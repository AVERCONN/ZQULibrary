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
import android.widget.Toast;

/**
 * 显示”借阅情况“界面
 * 
 * @author Aboo
 * 
 */
public class UserBorrowBookConditionActivity extends Activity implements
		OnClickListener {

	private Context mContext;
	private ListView mBookLV;
	private SimpleAdapter mBookSimpleAdapter;
	private String mAccountStr = "";
	private String mPwStr = "";
	private AlertDialog mLoadingDialog;

	private ArrayList<Map<String, String>> mBookLvArrayList;
	private final String BOOK_NAME_KEY = "bookName";
	private final String BOOK_BORROW_DATE_KEY = "borrowDate";
	private final String BOOK_RETURN_DATE_KEY = "returnDate";
	private final String BOOK_TYPE_KEY = "bookType";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_center_borrow_book_condition);
		mContext = UserBorrowBookConditionActivity.this;

		Bundle mBundle = getIntent().getExtras();
		mAccountStr = mBundle.getString(DataKeyBean.USER_ACCOUNT_KEY);
		mPwStr = mBundle.getString(DataKeyBean.USER_PW_KEY);

		View mBack = findViewById(R.id.ucBorrowBookCondition_titlebar_title);
		mBack.setOnClickListener(this);

		sendRequestToGetData();

	}

	private void sendRequestToGetData() {
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

		String urlStr = WebUrlHelper.getUserBorrowBookConditionUrl(
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
							parseBookListFromWebPage(arg0.result);
						}
					}
				});
	}

	private void parseBookListFromWebPage(String webPage) {

		Document doc = Jsoup.parse(webPage);
		Elements trs = doc.getElementById("borrowedcontent")
				.getElementsByTag("tr");
		Elements tds = null;

		mBookLvArrayList = new ArrayList<Map<String, String>>();
		Map<String, String> bookMap = new HashMap<String, String>();

		for (int i = 1; i < trs.size(); i++) {
			tds = trs.get(i).getElementsByTag("td");
			for (int j = 0; j < tds.size(); j++) {
				switch (j) {
				case 1:

					bookMap.put(BOOK_RETURN_DATE_KEY,
							"应还期："
									+ tds.get(j)
											.text());
					break;

				case 2:
					bookMap.put(BOOK_NAME_KEY, tds.get(j)
							.text());
					break;

				case 4:
					bookMap.put(BOOK_TYPE_KEY, "图书类型："
							+ tds.get(j).text());
					break;

				case 6:
					bookMap.put(BOOK_BORROW_DATE_KEY, "借期："
							+ tds.get(j).text());
					break;

				default:
					break;
				}
			}
			mBookLvArrayList.add(bookMap);
			bookMap = new HashMap<String, String>();
		}

		if (mBookLvArrayList.size() != 0) {
			setupBookList();
		} else {
			Toast.makeText(mContext, "你当前没有借书", Toast.LENGTH_SHORT)
					.show();
			((Activity) mContext).finish();
		}

	}

	private void setupBookList() {
		mBookLV = (ListView) findViewById(R.id.ucBorrowBookCondition_book_lv);
		mBookSimpleAdapter = new SimpleAdapter(
				mContext,
				mBookLvArrayList,
				R.layout.ucbbc_listview_item_style,
				new String[] { BOOK_NAME_KEY,
						BOOK_BORROW_DATE_KEY,
						BOOK_RETURN_DATE_KEY,
						BOOK_TYPE_KEY },
				new int[] {
						R.id.ucbbcListviewItemStyle_bookName_tv,
						R.id.ucbbcListviewItemStyle_borrowDate_tv,
						R.id.ucbbcListviewItemStyle_returnDate_tv,
						R.id.ucbbcListviewItemStyle_bookType_tv });
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
		case R.id.ucBorrowBookCondition_titlebar_title:
			((Activity) mContext).finish();
			break;

		default:
			break;
		}
	}

}
