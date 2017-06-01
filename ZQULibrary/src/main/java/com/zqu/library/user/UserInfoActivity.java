package com.zqu.library.user;

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
import android.widget.TextView;
import android.widget.Toast;

/**
 * 显示当前用户的信息
 * 
 * @author Aboo
 * 
 */
public class UserInfoActivity extends Activity implements OnClickListener {

	private Context mContext;
	private View mBack;
	private TextView mUserNameTV;
	private TextView mUserStatusTV;
	private TextView mUserTypeTV;
	private TextView mUserDeptTV;
	private TextView mUserBalanceTV;

	private String mUserAccountStr = "";
	private String mUserPwStr = "";
	private AlertDialog mLoadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_center_user_info);
		mContext = this;

		Bundle mBundle = getIntent().getExtras();
		mUserAccountStr = mBundle
				.getString(DataKeyBean.USER_ACCOUNT_KEY);
		mUserPwStr = mBundle.getString(DataKeyBean.USER_PW_KEY);

		mBack = findViewById(R.id.ucUserInfo_titlebar_title);
		mUserNameTV = (TextView) findViewById(R.id.ucUserInfo_user_name);
		mUserStatusTV = (TextView) findViewById(R.id.ucUserInfo_user_status);
		mUserTypeTV = (TextView) findViewById(R.id.ucUserInfo_user_type);
		mUserDeptTV = (TextView) findViewById(R.id.ucUserInfo_user_dept);
		mUserBalanceTV = (TextView) findViewById(R.id.ucUserInfo_user_balance);

		mBack.setOnClickListener(this);

		sendRequestToGetUserInfo();
	}

	private void sendRequestToGetUserInfo() {
		RequestParams params = new RequestParams();
		params.addHeader("Pragma", "no-cache");
		params.addHeader(
				"Accept",
				"image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, */*");
		params.addHeader("Accept-Encoding", "gzip, deflate");
		params.addHeader("Accept-Language", "zh-CN");
		params.addHeader("Content-Type",
				"application/x-www-form-urlencoded");
		params.addHeader("Connection", "Keep-Alive");
		params.addHeader("Host", "10.0.1.102:81");

		String urlStr = WebUrlHelper.getUserInfoUrl(mUserAccountStr,
				mUserPwStr);

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
							parseUserInfoFromWebPage(arg0.result);
						}
					}
				});
	}

	private void parseUserInfoFromWebPage(String webPageHtml) {
		Document document = Jsoup.parse(webPageHtml);

		Elements elements = document.getElementById("userInfoContent")
				.getElementsByClass("infoline");
		for (int i = 0; i < elements.size(); i++) {
			switch (i) {
			case 1:
				// 姓名
				mUserNameTV.setText(elements
						.get(i)
						.getElementsByClass("inforight")
						.text());
				break;

			case 2:
				// 类型
				mUserTypeTV.setText(elements
						.get(i)
						.getElementsByClass("inforight")
						.text());

			case 3:
				// 学院
				mUserDeptTV.setText(elements
						.get(i)
						.getElementsByClass("inforight")
						.text());

			case 4:
				// 状态
				mUserStatusTV.setText(elements
						.get(i)
						.getElementsByClass("inforight")
						.text());

			case 10:
				// 余额
				mUserBalanceTV.setText(elements
						.get(i)
						.getElementsByClass("inforight")
						.text());

			default:
				break;
			}
		}
	}

	@Override
	public void finish() {
		// 实现父类方法必须放在首句，否则动画无效
		super.finish();
		// 结束当前activity 的切换动画
		overridePendingTransition(R.anim.slide_in_up,
				R.anim.slide_out_down);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ucUserInfo_titlebar_title:
			((Activity) mContext).finish();
			break;

		default:
			break;
		}
	}

}
