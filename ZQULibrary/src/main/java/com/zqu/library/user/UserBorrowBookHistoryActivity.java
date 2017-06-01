package com.zqu.library.user;

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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 显示”借书历史“界面
 * 
 * @author Aboo
 * 
 */
public class UserBorrowBookHistoryActivity extends Activity implements
		OnClickListener {

	private Context mContext;
	private ListView mBookLV;
	private ArrayList<Map<String, String>> mBookArrayList;
	private SimpleAdapter mBookSimpleAdapter;
	private TextView mBookPageTV;
	private int mPageCount = 0;
	private int mCurrentPageIndex = 0;
	private AlertDialog mLoadingDialog = null;

	private String mAccountStr = "";
	private String mPwStr = "";

	private final String BOOK_BORROW_DATE_KEY = "borrowDate";
	private final String BOOK_RETURN_DATE_KEY = "returnDate";
	private final String BOOK_NAME_KEY = "bookName";
	private final String BOOK_TYPE_KEY = "bookType";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_center_borrow_book_history);
		mContext = this;

		Bundle mBundle = getIntent().getExtras();
		mAccountStr = mBundle.getString(DataKeyBean.USER_ACCOUNT_KEY);
		mPwStr = mBundle.getString(DataKeyBean.USER_PW_KEY);

		View mBack = findViewById(R.id.ucBorrowBookHistory_titlebar_title);
		mBack.setOnClickListener(this);

		View mBackPage = findViewById(R.id.ucBorrowBookHistory_menubar_back);
		View mForwardPage = findViewById(R.id.ucBorrowBookHistory_menubar_forward);
		mBackPage.setOnClickListener(this);
		mForwardPage.setOnClickListener(this);

		mBookPageTV = (TextView) findViewById(R.id.ucBorrowBookHistory_menubar_page);

		sendRequestToGetData(1);
	}

	private void sendRequestToGetData(int page) {
		RequestParams params = new RequestParams();
		params.addHeader(
				"Accept",
				"image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, */*");
		params.addHeader("Accept-Encoding", "gzip, deflate");
		params.addHeader("Accept-Language", "zh-CN");
		params.addHeader("Content-Type",
				"application/x-www-form-urlencoded");
		params.addHeader("Connection", "Keep-Alive");
		params.addHeader("Host", WebUrlHelper.HOST_IP);

		String urlStr = WebUrlHelper.getUserBorrowBookHistoryUrl(page,
				mAccountStr, mPwStr);

		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET, urlStr, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(
							HttpException arg0,
							String arg1) {
						mLoadingDialog.dismiss();
						Toast.makeText(mContext,
								"获取数据失败!",
								Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void onStart() {
						mLoadingDialog = (new LoadingDialog(
								mContext))
								.createLoadingDialog("加载中");
					}

					@Override
					public void onSuccess(
							ResponseInfo<String> arg0) {
						if (arg0.statusCode == 200) {
							mLoadingDialog.dismiss();
							try {
								parseDataFromWebPage(arg0.result);
							} catch (Exception e) {
								Toast.makeText(mContext,
										"借书记录为空",
										Toast.LENGTH_SHORT)
										.show();
							}
						}
					}
				});
	}

	private void parseDataFromWebPage(String webPage) {
		Document doc = Jsoup.parse(webPage);

		mCurrentPageIndex = Integer.parseInt(doc.getElementById(
				"ctl00_cpRight_Pagination1_dplbl2").text());
		mPageCount = Integer.parseInt(doc.getElementById(
				"ctl00_cpRight_Pagination1_gplbl2").text());
		setPage(mCurrentPageIndex, mPageCount);

		Elements trs = doc.getElementsByTag("table").get(0)
				.getElementsByTag("tr");
		Elements tds = null;

		mBookArrayList = new ArrayList<Map<String, String>>();
		Map<String, String> mBookMap = new HashMap<String, String>();

		for (int i = 1; i < trs.size(); i++) {
			tds = trs.get(i).getElementsByTag("td");
			for (int j = 0; j < tds.size(); j++) {
				switch (j) {
				case 0:
					mBookMap.put(BOOK_BORROW_DATE_KEY,
							"借期："
									+ tds.get(j)
											.text());
					break;

				case 1:
					mBookMap.put(BOOK_RETURN_DATE_KEY,
							"还期："
									+ tds.get(j)
											.text());
					break;

				case 2:
					mBookMap.put(BOOK_NAME_KEY, tds.get(j)
							.text());
					break;

				case 3:
					mBookMap.put(BOOK_TYPE_KEY, "图书类型："
							+ tds.get(j).text());
					break;

				default:
					break;
				}
			}
			mBookArrayList.add(mBookMap);
			mBookMap = new HashMap<String, String>();
		}

		setupBookListData();

	}

	private void setPage(int currentPageIndex, int pageCount) {
		mBookPageTV.setText(currentPageIndex + "/" + pageCount);
	}

	private void setupBookListData() {
		mBookLV = (ListView) findViewById(R.id.ucBorrowBookHistory_book_lv);
		mBookSimpleAdapter = new SimpleAdapter(
				mContext,
				mBookArrayList,
				R.layout.ucbbh_listview_item_style,
				new String[] { BOOK_NAME_KEY,
						BOOK_BORROW_DATE_KEY,
						BOOK_RETURN_DATE_KEY,
						BOOK_TYPE_KEY },
				new int[] {
						R.id.ucbbhListviewItemStyle_bookName_tv,
						R.id.ucbbhListviewItemStyle_borrowDate_tv,
						R.id.ucbbhListviewItemStyle_returnDate_tv,
						R.id.ucbbhListviewItemStyle_bookType_tv });
		mBookLV.setAdapter(mBookSimpleAdapter);
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
		switch (v.getId()) {
		case R.id.ucBorrowBookHistory_titlebar_title:
			((Activity) mContext).finish();
			break;

		case R.id.ucBorrowBookHistory_menubar_back:
			// 上一页
			if (mCurrentPageIndex > 1) {
//				mCurrentPageIndex = mCurrentPageIndex - 1;
				sendRequestToGetData(mCurrentPageIndex);
				
			} else {
				Toast.makeText(mContext, "已是第一页",
						Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.ucBorrowBookHistory_menubar_forward:
			// 下一页
			if (mCurrentPageIndex < mPageCount) {
//				mCurrentPageIndex = mCurrentPageIndex + 1;
				sendRequestToGetData(mCurrentPageIndex);
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
