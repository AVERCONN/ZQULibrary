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
import com.zqu.library.R;
import com.zqu.library.util.DataKeyBean;
import com.zqu.library.util.LoadingDialog;
import com.zqu.library.util.WebUrlHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
 * 用于显示图书检索结果
 * 
 * @author Aboo
 * 
 */
public class SearchBookResultActivity extends Activity implements
		OnClickListener {

	private Context mContext;
	private String mBookLocationStr;
	private String mSearchKeyWordStr;
	private View mBack;
	private ListView mBookLv;
	/** 上一页 按钮 */
	private View mBackPage;
	/** 当前页显示 */
	private TextView mPageTv;
	/** 下一页 按钮 */
	private View mForwardPage;
	/** 搜索结果页数 */
	private int mPageCount = 0;
	/** 当前页数 */
	private int mCurrentPage = 1;

	private ArrayList<Map<String, String>> mBookList = null;
	private final String BOOK_CTRL_NO_KEY = "bookCtrlNo";
	private final String BOOK_NAME_KEY = "bookName";
	private final String BOOK_WRITER_KEY = "bookWriter";
	private final String BOOK_COUNT_KEY = "bookCount";
	private final String BOOK_INDEX_KEY = "bookIndexNo";
	private final String BOOK_PUBLICATION_DATE_KEY = "bookPublicationDate";
	private final String BOOK_PRESS_KEY = "bookPress";
	private AlertDialog.Builder builder = null;
	private AlertDialog alertDialog = null;
	private AlertDialog bookIntroLoadingDialog = null;
	private AlertDialog bookIntroDialog = null;
	private Document doc = null;
	private String ctrlNoTempStr = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_book_result);
		mContext = SearchBookResultActivity.this;

		builder = new AlertDialog.Builder(mContext);

		Bundle mBundle = getIntent().getExtras();
		mBookLocationStr = mBundle
				.getString(DataKeyBean.SEARCH_BOOK_LOCATION_KEY);
		mSearchKeyWordStr = mBundle
				.getString(DataKeyBean.SEARCH_BOOK_KEYWORD_KEY);

		//主页传送的数据
		Intent intent = getIntent();
		String data = intent.getStringExtra("maindata");
		if(!data.equals("")) {
			mSearchKeyWordStr=data;
			mBookLocationStr = "全部";
		}
		try {
			mSearchKeyWordStr = URLEncoder.encode(
					mSearchKeyWordStr, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
		}

		mBack = findViewById(R.id.searchBookResult_titlebar_title);
		mBookLv = (ListView) findViewById(R.id.searchBookResult_bookList_lv);
		mBackPage = findViewById(R.id.searchBookResult_menubar_back);
		mForwardPage = findViewById(R.id.searchBookResult_menubar_forward);
		mPageTv = (TextView) findViewById(R.id.searchBookResult_menubar_page);

		mBack.setOnClickListener(this);
		mBackPage.setOnClickListener(this);
		mForwardPage.setOnClickListener(this);

		executeSearch(mSearchKeyWordStr, mBookLocationStr, mCurrentPage
				+ "");

		mBookLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				ctrlNoTempStr = ((TextView) arg1
						.findViewById(R.id.bookListviewItemStyle_ctrlNo_tv))
						.getText().toString();
				getBookIntro(ctrlNoTempStr);
			}
		});
	}

	@Override
	public void finish() {
		super.finish();
		// 结束当前activity 的切换动画
		overridePendingTransition(R.anim.slide_in_up,
				R.anim.slide_out_down);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.searchBookResult_titlebar_title:
			((Activity) mContext).finish();
			break;

		case R.id.searchBookResult_menubar_back:
			if (mCurrentPage > 1) {
				mCurrentPage = mCurrentPage - 1;
				executeSearch(mSearchKeyWordStr,
						mBookLocationStr, mCurrentPage
								+ "");
			} else {
				Toast.makeText(mContext, "已是第一页",
						Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.searchBookResult_menubar_forward:
			if (mCurrentPage < mPageCount) {
				mCurrentPage = mCurrentPage + 1;
				executeSearch(mSearchKeyWordStr,
						mBookLocationStr, mCurrentPage
								+ "");
			} else {
				Toast.makeText(mContext, "已是最后一页",
						Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 执行搜索
	 * 
	 * @param keyWord
	 *                关键词
	 * @param location
	 *                馆藏点
	 * @param page
	 *                页数
	 */
	private void executeSearch(String keyWord, String location, String page) {

		RequestParams params = new RequestParams();
		params.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		params.addHeader("Accept-Encoding", "gzip, deflate");
		params.addHeader("Accept-Language",
				"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		params.addHeader("Connection", "keep-alive");
		params.addHeader("Host", WebUrlHelper.HOST_IP);

		HttpUtils httpUtils = new HttpUtils();
		int dp = 10;
		String url = WebUrlHelper.BOOK_SEARCH_RESULT_URL + "?"
				+ "anywords=" + keyWord + "&dp=" + dp
				+ "&page=" + page + "&dept=" + location;

		httpUtils.send(HttpMethod.GET, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(
							HttpException arg0,
							String arg1) {
						// TODO Auto-generated method
						// stub
						alertDialog.dismiss();
						Toast.makeText(mContext,
								"获取数据失败,请检查网络",
								Toast.LENGTH_SHORT)
								.show();
						((Activity) mContext).finish();
					}

					@Override
					public void onStart() {
						alertDialog = (new LoadingDialog(
								mContext))
								.createLoadingDialog("正在搜索...");
					}

					@Override
					public void onSuccess(
							ResponseInfo<String> arg0) {
						alertDialog.dismiss();
						try {
							getBookListData(arg0.result);
							mPageTv.setText(mCurrentPage
									+ "/"
									+ mPageCount);
							setBookListViewData();
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
	 * 从网络请求返回的结果中获取数据
	 * 
	 * @param resultHtml
	 *                网络请求返回的结果
	 */
	private void getBookListData(String resultHtml) {
		mBookList = new ArrayList<Map<String, String>>();
		String aStr = "";
		String bStr = "";
		String cStr = "";
		String dStr = "";
		String eStr = "";
		String fStr = "";
		String gStr = "";

		Map<String, String> mBookMap = new HashMap<String, String>();

		doc = Jsoup.parse(resultHtml);

		int searchResultCount = Integer
				.parseInt(doc.getElementById(
						"ctl00_ContentPlaceHolder1_countlbl")
						.text().toString());

		mPageCount = Integer.parseInt(doc.getElementById(
				"ctl00_ContentPlaceHolder1_gplblfl1").text());

		if (searchResultCount == 0) {
			return;
		}
		Elements es = doc.getElementsByClass("tb").get(0)
				.getElementsByTag("tr");

		for (int i = 1; i < es.size(); i++) {
			Elements tdes = es.get(i).getElementsByTag("td");
			for (int j = 0; j < tdes.size(); j++) {
				switch (j) {
				case 0:
					// 系统控制号
					aStr = tdes.get(j).select("input")
							.attr("value").trim()
							.toString();
					mBookMap.put(BOOK_CTRL_NO_KEY, aStr);
					break;

				case 1:
					// 书名
					bStr = tdes.get(j).text().trim();
					mBookMap.put(BOOK_NAME_KEY, bStr);
					break;
				case 2:
					// 作者
					cStr = tdes.get(j).text().trim();
					mBookMap.put(BOOK_WRITER_KEY, "作者："
							+ cStr);
					break;
				case 3:
					// 出版社
					dStr = tdes.get(j).text().trim();
					mBookMap.put(BOOK_PRESS_KEY, "出版社："
							+ dStr);
					break;
				case 4:
					// 出版年
					eStr = tdes.get(j).text().trim();
					mBookMap.put(BOOK_PUBLICATION_DATE_KEY,
							"出版年：" + eStr);
					break;
				case 5:
					// 索取号
					fStr = tdes.get(j).text().trim();
					mBookMap.put(BOOK_INDEX_KEY, "索引号："
							+ fStr);
					break;
				case 6:
					break;
				case 7:
					// 可借
					gStr = tdes.get(j).text().trim();
					mBookMap.put(BOOK_COUNT_KEY, "可借："
							+ gStr);
					break;
				default:
					break;
				}
			}
			mBookList.add(mBookMap);
			mBookMap = new HashMap<String, String>();
		}
	}

	private void setBookListViewData() {
		if (mBookList != null && mBookList.size() != 0) {

			SimpleAdapter bookSimpleAdapter = new SimpleAdapter(
					mContext,
					mBookList,
					R.layout.searh_book_listview_item_style,
					new String[] { BOOK_CTRL_NO_KEY,
							BOOK_NAME_KEY,
							BOOK_WRITER_KEY,
							BOOK_COUNT_KEY,
							BOOK_INDEX_KEY,
							BOOK_PRESS_KEY,
							BOOK_PUBLICATION_DATE_KEY },
					new int[] {
							R.id.bookListviewItemStyle_ctrlNo_tv,
							R.id.bookListviewItemStyle_bookName_tv,
							R.id.bookListviewItemStyle_writer_tv,
							R.id.bookListviewItemStyle_canGetCount_tv,
							R.id.bookListviewItemStyle_indexNo_tv,
							R.id.bookListviewItemStyle_press_tv,
							R.id.bookListviewItemStyle_publicationDate_tv });
			mBookLv.setAdapter(bookSimpleAdapter);

		} else {
			Toast.makeText(mContext, "没有找到相关书籍", Toast.LENGTH_SHORT)
					.show();
			((Activity) mContext).finish();

		}

	}

	/**
	 * 显示图书简介Dialog
	 * 
	 * @param bookIntro
	 *                图书简介
	 */
	private void createBookIntroDialog(String bookIntro) {
		bookIntroDialog = builder.show();
		bookIntroDialog.setContentView(R.layout.dialog_book_intro);
		bookIntroDialog.setCanceledOnTouchOutside(false);

		doc = Jsoup.parse(bookIntro);
		String introStr = doc
				.getElementById("ctl00_ContentPlaceHolder1_bookcardinfolbl")
				.text().toString();

		String bookConditionStr = "";
		Elements elements = doc.getElementsByClass("tb").get(0)
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

		TextView bookIntroTv = (TextView) bookIntroDialog
				.findViewById(R.id.dialogBookIntro_content_tv);
		View closeBtn = bookIntroDialog
				.findViewById(R.id.dialogBookIntro_close_tv);

		bookIntroTv.setText(bookConditionStr + introStr);
		closeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bookIntroDialog.dismiss();
			}
		});
	}

	/**
	 * 从网络中获得图书简介
	 * 
	 * @param ctrlNo
	 *                系统控制号
	 */
	private void getBookIntro(String ctrlNo) {
		RequestParams params = new RequestParams();
		params.addHeader("Connection", "keep-alive");
		params.addHeader("Host", WebUrlHelper.HOST_IP);
		params.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		params.addHeader("Accept-Encoding", "gzip, deflate");
		params.addHeader("Accept-Language",
				"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		HttpUtils httpUtils = new HttpUtils();
		String urlStr = WebUrlHelper.BOOK_INFO_URL + "?" + "ctrlno="
				+ ctrlNo;

		httpUtils.send(HttpMethod.GET, urlStr, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(
							HttpException arg0,
							String arg1) {
						// TODO Auto-generated method
						// stub
						bookIntroLoadingDialog
								.dismiss();
						Toast.makeText(mContext,
								"获取数据失败,请检查网络",
								Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void onStart() {
						bookIntroLoadingDialog = (new LoadingDialog(
								mContext))
								.createLoadingDialog("加载中...");
					}

					@Override
					public void onSuccess(
							ResponseInfo<String> arg0) {
						bookIntroLoadingDialog
								.dismiss();
						createBookIntroDialog(arg0.result);
					}
				});
	}
}
