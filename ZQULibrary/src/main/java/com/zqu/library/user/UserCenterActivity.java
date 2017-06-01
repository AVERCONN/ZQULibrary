package com.zqu.library.user;

import com.zqu.library.R;
import com.zqu.library.util.DataKeyBean;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 用户中心
 * 
 * @author Aboo
 * 
 */
public class UserCenterActivity extends Activity implements OnClickListener {

	private Context mContext;
	private String mUserAccountStr = "";
	private String mUserPwStr = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_center);
		mContext = this;

		Bundle mBundle = getIntent().getExtras();
		String mWelcomeStr = mBundle
				.getString(DataKeyBean.USER_WELCOME_STRING_KEY);
		mUserAccountStr = mBundle
				.getString(DataKeyBean.USER_ACCOUNT_KEY);
		mUserPwStr = mBundle.getString(DataKeyBean.USER_PW_KEY);

		View mBack = findViewById(R.id.userCenter_titlebar_title);
		TextView mWelcomeTV = (TextView) findViewById(R.id.userCenter_head_tv);
		View mUserInfoItem = findViewById(R.id.userCenter_item_userInfo);
		View mPwChangeItem = findViewById(R.id.userCenter_item_pwChange);
		View mBorrowBookConditionItem = findViewById(R.id.userCenter_item_borrowBookCondition);
		View mBookOverdueNotice = findViewById(R.id.userCenter_item_bookOverdueNotice);
		View mBorrowBookHistory = findViewById(R.id.userCenter_item_borrowBookHistory);

		mWelcomeTV.setText(mWelcomeStr);
		mBack.setOnClickListener(this);
		mUserInfoItem.setOnClickListener(this);
		mPwChangeItem.setOnClickListener(this);
		mBorrowBookConditionItem.setOnClickListener(this);
		mBookOverdueNotice.setOnClickListener(this);
		mBorrowBookHistory.setOnClickListener(this);
	}

	@Override
	public void finish() {
		super.finish();
		// 结束当前activity 的切换动画
		overridePendingTransition(R.anim.slide_in_up,
				R.anim.slide_out_down);
		Toast.makeText(UserCenterActivity.this, "退出登录成功！",
				Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)
				&& (event.getRepeatCount() == 0)) {
		}
		return true;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.userCenter_titlebar_title:
			// 提示退出登录
			((Activity) mContext).finish();
			break;
		case R.id.userCenter_item_userInfo:
			Intent userInfoIntent = new Intent(mContext,
					UserInfoActivity.class);
			userInfoIntent.putExtra(DataKeyBean.USER_ACCOUNT_KEY,
					mUserAccountStr);
			userInfoIntent.putExtra(DataKeyBean.USER_PW_KEY,
					mUserPwStr);
			startActivity(userInfoIntent);
			break;

		case R.id.userCenter_item_pwChange:
			//
			break;

		case R.id.userCenter_item_borrowBookCondition:
			Intent borrowBookConditionIntent = new Intent(mContext,
					UserBorrowBookConditionActivity.class);
			borrowBookConditionIntent.putExtra(
					DataKeyBean.USER_ACCOUNT_KEY,
					mUserAccountStr);
			borrowBookConditionIntent.putExtra(
					DataKeyBean.USER_PW_KEY, mUserPwStr);
			startActivity(borrowBookConditionIntent);
			break;

		case R.id.userCenter_item_bookOverdueNotice:
			Intent bookOverdueNoticeIntent = new Intent(mContext,
					UserBookOverdueNoticeActivity.class);
			bookOverdueNoticeIntent.putExtra(
					DataKeyBean.USER_ACCOUNT_KEY,
					mUserAccountStr);
			bookOverdueNoticeIntent.putExtra(
					DataKeyBean.USER_PW_KEY, mUserPwStr);
			startActivity(bookOverdueNoticeIntent);
			break;

		case R.id.userCenter_item_borrowBookHistory:
			Intent borrowBookHistoryIntent = new Intent(mContext,
					UserBorrowBookHistoryActivity.class);
			borrowBookHistoryIntent.putExtra(
					DataKeyBean.USER_ACCOUNT_KEY,
					mUserAccountStr);
			borrowBookHistoryIntent.putExtra(
					DataKeyBean.USER_PW_KEY, mUserPwStr);
			startActivity(borrowBookHistoryIntent);
			break;

		default:
			break;
		}
	}

}
