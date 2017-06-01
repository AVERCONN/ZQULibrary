package com.zqu.library;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.zqu.library.util.DataKeyBean;
import com.zqu.library.util.LoadingDialog;
import com.zqu.library.util.WebUrlHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 用于显示 “分类导航” 中的list项点击结果
 * 
 * @author Aboo
 * 
 */
public class BookshelfItemShowActivity extends Activity implements
		OnClickListener {

	private Context mContext;
	private String mBookCategoryStr;
	private View mBack;
	private ListView mBookLv;
	/** 上一页 按钮 */
	private View mBackPage;
	/** 当前页显示 */
	private TextView mCurrentPageIndexTv;
	/** 下一页 按钮 */
	private View mForwardPage;
	/** 结果页数 */
	private int mPageCount = 0;
	/** 当前页数 */
	private int mCurrentPage = 1;
	private ArrayList<Map<String, String>> mBookshlefList = null;
	private AlertDialog.Builder mBuilder = null;
	private AlertDialog mLoadingDialog = null;
	private AlertDialog mBookIntroDialog = null;
	private Document mDoc = null;
	private String mBookCategoryIdStr = "";
	private String mBookCategoryNameStr = "";

	private final String BOOK_CTRL_NO_KEY = "bookCtrlNo";
	private final String BOOK_NAME_KEY = "bookName";
	private final String BOOK_INDEX_KEY = "bookIndexNo";
	private final String BOOK_PUBLICATION_DATE_KEY = "bookPublicationDate";
	private final String BOOK_PRESS_KEY = "bookPress";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bookshelf_itemshow);
		mContext = this;
		mBuilder = new AlertDialog.Builder(mContext);

		mBookCategoryStr = getIntent().getExtras().getString(
				DataKeyBean.BOOKSHELF_KEY);

		mBack = findViewById(R.id.bookshelfItemShow_titlebar_title);
		mBookLv = (ListView) findViewById(R.id.bookshelfItemShow_bookList_lv);
		mBackPage = findViewById(R.id.bookshelfItemShow_menubar_back);
		mCurrentPageIndexTv = (TextView) findViewById(R.id.bookshelfItemShow_menubar_page);
		mForwardPage = findViewById(R.id.bookshelfItemShow_menubar_forward);

		mBack.setOnClickListener(this);
		mBackPage.setOnClickListener(this);
		mForwardPage.setOnClickListener(this);

		String[] mBookCategoryStrings = mBookCategoryStr.split(" ");
		mBookCategoryIdStr = mBookCategoryStrings[0].trim();
		try {
			mBookCategoryNameStr = URLEncoder.encode(
					mBookCategoryStrings[1].trim(), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		getBookshelfList(mBookCategoryIdStr, mBookCategoryNameStr,
				mCurrentPage + "");

		mBookLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				TextView bookInfoLinkTv = (TextView) arg1
						.findViewById(R.id.bookshelfChildListviewItemStyle_ctrlNo_tv);
				getBookIntroFromNet(bookInfoLinkTv.getText()
						.toString());
			}
		});

	}

	@Override
	public void finish() {
		super.finish();
		// 跳转activity时的切换动画
		overridePendingTransition(R.anim.slide_in_up,
				R.anim.slide_out_down);
	}

	/**
	 * 从网络中获取书的列表
	 * 
	 * @param ID
	 *                分类ID
	 * @param name
	 *                分类名
	 * @param page
	 *                页
	 */
	private void getBookshelfList(String ID, String name, String page) {
		RequestParams params = new RequestParams();
		params.addHeader(
				"Accept",
				"image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, */*");
		params.addHeader("Accept-Encoding", "gzip, deflate");
		params.addHeader("Accept-Language", "zh-CN");
		params.addHeader("Connection", "keep-alive");
		params.addHeader("Host", WebUrlHelper.HOST_IP);

		HttpUtils httpUtils = new HttpUtils();
		String url = WebUrlHelper.BOOKSHELF_BROWSE_URL + "?"
				+ "clcode=" + ID + "&clname=" + name + "&page="
				+ page;

		httpUtils.send(HttpMethod.GET, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
						mLoadingDialog = (new LoadingDialog(
								mContext))
								.createLoadingDialog("正在加载...");
					}

					@Override
					public void onFailure(
							HttpException arg0,
							String arg1) {
						mLoadingDialog.dismiss();
						Toast.makeText(mContext,
								"获取数据失败",
								Toast.LENGTH_SHORT)
								.show();
						((Activity) mContext).finish();
					}

					@Override
					public void onSuccess(
							ResponseInfo<String> arg0) {
						try {
							mLoadingDialog.dismiss();
							parseXmlToBookshelfListDate(arg0.result);
							mCurrentPageIndexTv
									.setText(mCurrentPage
											+ "/"
											+ mPageCount);
							setBookShelfListViewData();
						} catch (Exception e) {
							Toast.makeText(mContext,
									"没有找到相关书籍",
									Toast.LENGTH_SHORT)
									.show();
							((Activity) mContext)
									.finish();
						}
					}
				});
	}

	/**
	 * 解析数据
	 * 
	 * @param resultXml
	 *                GET请求结果返回
	 */
	private void parseXmlToBookshelfListDate(String resultXml) {
		mBookshlefList = new ArrayList<Map<String, String>>();
		Map<String, String> mBookshelfMap = new HashMap<String, String>();
		String aStr = "";
		String bStr = "";
		String cStr = "";
		String dStr = "";
		String linkStr = "";

		mDoc = Jsoup.parse(resultXml);

		int resultCount = Integer.parseInt(mDoc
				.getElementById("lblrcount").text().toString());
		if (resultCount == 0) {
			return;
		}

		mPageCount = Integer.parseInt(mDoc
				.getElementById("Pagination2_gplbl2").text()
				.toString());

		Elements es = mDoc.getElementsByClass("tb").get(0)
				.getElementsByTag("tr");
		for (int i = 1; i < es.size(); i++) {
			Elements tdes = es.get(i).getElementsByTag("td");
			for (int j = 0; j < tdes.size(); j++) {
				switch (j) {
				case 1:
					// 书名
					aStr = tdes.get(j).text().trim();
					mBookshelfMap.put(BOOK_NAME_KEY, aStr);

					linkStr = tdes.get(j)
							.getElementsByTag("a")
							.attr("href");
					mBookshelfMap.put(BOOK_CTRL_NO_KEY,
							linkStr);
					break;
				case 2:
					// 出版社
					bStr = tdes.get(j).text().trim();
					mBookshelfMap.put(BOOK_PRESS_KEY,
							"出版社：" + bStr);
					break;
				case 3:
					// 出版日期
					cStr = tdes.get(j).text().trim();
					mBookshelfMap.put(
							BOOK_PUBLICATION_DATE_KEY,
							"出版日期：" + cStr);
					break;
				case 4:
					// 索取号
					dStr = tdes.get(j).text().trim();
					mBookshelfMap.put(BOOK_INDEX_KEY,
							"索取号：" + dStr);
					break;

				default:
					break;
				}
			}
			mBookshlefList.add(mBookshelfMap);
			mBookshelfMap = new HashMap<String, String>();
		}
	}

	private void setBookShelfListViewData() {
		if (mBookshlefList != null && mBookshlefList.size() != 0) {
			SimpleAdapter bookSimpleAdapter = new SimpleAdapter(
					mContext,
					mBookshlefList,
					R.layout.bookshelf_child_listview_item_style,
					new String[] {
							BOOK_NAME_KEY,
							BOOK_INDEX_KEY,
							BOOK_PRESS_KEY,
							BOOK_PUBLICATION_DATE_KEY,
							BOOK_CTRL_NO_KEY },
					new int[] {
							R.id.bookshelfChildListviewItemStyle_bookName_tv,
							R.id.bookshelfChildListviewItemStyle_indexNo_tv,
							R.id.bookshelfChildListviewItemStyle_press_tv,
							R.id.bookshelfChildListviewItemStyle_publicationDate_tv,
							R.id.bookshelfChildListviewItemStyle_ctrlNo_tv });
			mBookLv.setAdapter(bookSimpleAdapter);

		} else {
			Toast.makeText(mContext, "没有找到相关书籍", Toast.LENGTH_SHORT)
					.show();
			((Activity) mContext).finish();
		}
	}

	/**
	 * 从网络中获取图书简介
	 * 
	 * @param bookIntroLink
	 *                图书简介页链接
	 * 
	 */
	private void getBookIntroFromNet(String bookIntroLink) {
		RequestParams params = new RequestParams();
		params.addHeader("Connection", "keep-alive");
		params.addHeader("Host", WebUrlHelper.HOST_IP);
		params.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		params.addHeader("Accept-Encoding", "gzip, deflate");
		params.addHeader("Accept-Language",
				"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");

		HttpUtils httpUtils = new HttpUtils();
		String urlStr = WebUrlHelper.HOST_URL + "/" + bookIntroLink;

		httpUtils.send(HttpMethod.GET, urlStr, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(
							HttpException arg0,
							String arg1) {
						mLoadingDialog.dismiss();
						Toast.makeText(mContext,
								"获取数据失败,请检查网络",
								Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void onStart() {
						mLoadingDialog = (new LoadingDialog(
								mContext))
								.createLoadingDialog("正在加载...");
					}

					@Override
					public void onSuccess(
							ResponseInfo<String> arg0) {
						mLoadingDialog.dismiss();
						showBookIntroDialog(arg0.result);
					}
				});
	}

	/**
	 * 显示图书简介Dialog
	 * 
	 * @param bookIntroHtml
	 *                图书简介网页
	 */
	private void showBookIntroDialog(String bookIntroHtml) {
		mBookIntroDialog = mBuilder.show();
		mBookIntroDialog.setContentView(R.layout.dialog_book_intro);
		mBookIntroDialog.setCanceledOnTouchOutside(false);

		mDoc = Jsoup.parse(bookIntroHtml);
		String introStr = mDoc
				.getElementById("ctl00_ContentPlaceHolder1_bookcardinfolbl")
				.text().toString();

		String bookConditionStr = "";
		Elements elements = mDoc.getElementsByClass("tb").get(0)
				.getElementsByTag("tr");
		for (int i = 2; i < elements.size(); i++) {
			Elements tdes = elements.get(i).getElementsByTag("td");
			for (int j = 0; j < tdes.size(); j++) {
				switch (j) {
				case 0:
					bookConditionStr = bookConditionStr
							+ "馆藏地："
							+ tdes.get(j).text()
									.trim()
							+ "\n";
					break;
				case 1:
					bookConditionStr = bookConditionStr
							+ "索取号："
							+ tdes.get(j).text()
									.trim()
							+ "\n";
					break;
				case 2:
					bookConditionStr = bookConditionStr
							+ "登录号："
							+ tdes.get(j).text()
									.trim()
							+ "\n";
					break;
				case 5:
					bookConditionStr = bookConditionStr
							+ "状态："
							+ tdes.get(j).text()
									.trim()
							+ "\n";
					break;
				case 6:
					bookConditionStr = bookConditionStr
							+ "借阅类型："
							+ tdes.get(j).text()
									.trim()
							+ "\n";
					break;
				default:
					break;
				}
			}
			bookConditionStr = bookConditionStr + "\n";
		}

		TextView bookIntroTv = (TextView) mBookIntroDialog
				.findViewById(R.id.dialogBookIntro_content_tv);
		View closeBtn = mBookIntroDialog
				.findViewById(R.id.dialogBookIntro_close_tv);

		bookIntroTv.setText(bookConditionStr + introStr);
		closeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mBookIntroDialog.dismiss();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bookshelfItemShow_titlebar_title:
			((Activity) mContext).finish();
			break;

		case R.id.bookshelfItemShow_menubar_back:
			if (mCurrentPage > 1) {
				mCurrentPage = mCurrentPage - 1;
				getBookshelfList(mBookCategoryIdStr,
						mBookCategoryNameStr,
						mCurrentPage + "");
			} else {
				Toast.makeText(mContext, "已是第一页",
						Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.bookshelfItemShow_menubar_forward:
			if (mCurrentPage < mPageCount) {
				mCurrentPage = mCurrentPage + 1;
				getBookshelfList(mBookCategoryIdStr,
						mBookCategoryNameStr,
						mCurrentPage + "");
			} else {
				Toast.makeText(mContext, "已是最后一页",
						Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}

}
