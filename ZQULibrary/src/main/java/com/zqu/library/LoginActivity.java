package com.zqu.library;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.http.client.RequestParams;
import com.zqu.library.R;
import com.zqu.library.user.UserCenterActivity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 用户登录
 * 
 * @author Aboo
 * 
 */
public class LoginActivity extends Activity implements OnClickListener {

	private Context mContext;
	private View mBack;
	private EditText mUserAccountEdt;
	private EditText mUserPwEdt;
	private Button mLoginBtn;
	private Button mResetBtn;

	private AlertDialog mLoginLoadingDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext = LoginActivity.this;

		mBack = findViewById(R.id.login_titlebar_title);
		mBack.setOnClickListener(this);

		mUserAccountEdt = (EditText) findViewById(R.id.login_account_edt);
		mUserPwEdt = (EditText) findViewById(R.id.login_pw_edt);
		mLoginBtn = (Button) findViewById(R.id.login_login_btn);
		mResetBtn = (Button) findViewById(R.id.login_reset_btn);

		mLoginBtn.setOnClickListener(this);
		mResetBtn.setOnClickListener(this);
	}

	@Override
	public void finish() {
		super.finish();
		// 结束当前activity 的切换动画
		overridePendingTransition(R.anim.slide_in_up,
				R.anim.slide_out_down);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_titlebar_title:
			((Activity) mContext).finish();
			break;

		case R.id.login_login_btn:
			String accountStr = mUserAccountEdt.getText()
					.toString().trim();
			String pwStr = mUserPwEdt.getText().toString().trim();

			if (!accountStr.equals("") || !pwStr.equals("")) {
				executeUserLogin(accountStr, pwStr);
			} else {
				Toast.makeText(mContext, "请输入账号或者密码",
						Toast.LENGTH_SHORT).show();
			}

			break;

		case R.id.login_reset_btn:
			mUserAccountEdt.setText("");
			mUserPwEdt.setText("");
			break;

		default:
			break;
		}
	}

	private void executeUserLogin(final String account, final String pw) {

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

		String urlStr = WebUrlHelper.getUserLoginUrl(account, pw);

		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET, urlStr, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(
							HttpException arg0,
							String arg1) {
						mLoginLoadingDialog.dismiss();
						Toast.makeText(mContext,
								"登录失败,请检查网络",
								Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void onStart() {
						mLoginLoadingDialog = (new LoadingDialog(
								mContext))
								.createLoadingDialog("正在登录...");
					}

					@Override
					public void onSuccess(
							ResponseInfo<String> arg0) {

						if (arg0.statusCode == 200) {
							mLoginLoadingDialog
									.dismiss();

							if (isLoginSuccess(arg0.result)) {

								Intent userCenterIntent = new Intent(
										mContext,
										UserCenterActivity.class);
								userCenterIntent.putExtra(
										DataKeyBean.USER_WELCOME_STRING_KEY,
										getWelcomeStr(arg0.result));
								userCenterIntent.putExtra(
										DataKeyBean.USER_ACCOUNT_KEY,
										account);
								userCenterIntent.putExtra(
										DataKeyBean.USER_PW_KEY,
										pw);
								startActivity(userCenterIntent);
							} else {
								Toast.makeText(mContext,
										"密码错误，登录失败",
										Toast.LENGTH_SHORT)
										.show();
							}
						}
					}
				});
	}

	private Boolean isLoginSuccess(String resultWebPage) {
		Document doc = Jsoup.parse(resultWebPage);
		Element tempElement = doc
				.getElementById("ctl00_ContentPlaceHolder1_lblErr_Lib");
		if (tempElement == null) {
			return true;
		} else {
			return false;
		}
	}

	private String getWelcomeStr(String resultWebPage) {
		Document doc = Jsoup.parse(resultWebPage);
		String welcomeStr = doc.getElementsByClass("userinfo").text()
				.trim();
		return welcomeStr;
	}
}
