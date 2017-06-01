package com.zqu.library.user;

import com.zqu.library.R;
import com.zqu.library.TitleBarBackEvent;
import com.zqu.library.util.LibraryDBManager;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserChangePwActivity extends Activity {

	/** 旧密码输入框 */
	private EditText oldPwEditText;
	/** 输入的旧密码 */
	private String oldPw;
	/** 新密码输入框 */
	private EditText newPwEditText;
	/** 输入的新密码 */
	private String newPw;
	/** 重新输入的密码输入框 */
	private EditText reNewPwEditText;
	/** 重新输入的旧密码 */
	private String reNewPw;
	private int display = 0;
	/** “确认”按钮 */
	private Button confirmButton;
	/** “重置”按钮 */
	private Button resetButton;
	/** 数据库管理类 */
	private LibraryDBManager dbManager;
	/** 用户数据库 */
	private SQLiteDatabase userDatabase;
	/** 当前账号的借阅证账号 */
	private String userAccount;
	/** 当前账号的借阅证密码 */
	private String userPw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_center_change_pw);

		// // 获得用户数据库
		// dbManager = new LibraryDBManager(UserChangePwActivity.this,
		// "users.db");
		// userDatabase = dbManager.getDatabase();
		// userAccount = User.getACCOUNT();
		// // 获得 “旧密码” 输入框
		// oldPwEditText = (EditText) findViewById(R.id.userChangePw_oldPw_edt);
		// // 获得 “新密码” 输入框
		// newPwEditText = (EditText) findViewById(R.id.userChangePw_newPw_edt);
		// // 获得 “重复密码” 输入框
		// reNewPwEditText = (EditText)
		// findViewById(R.id.userChangePw_re_newPw_edt);
		// // 获得 “显示密码” 单选框
		// ImageView pwDisplayImageView = (ImageView)
		// findViewById(R.id.userChangePw_pwDisplay_img);
		// pwDisplayControl(pwDisplayImageView);
		// // 获得 “确认” 按钮
		// confirmButton = (Button) findViewById(R.id.userChangePw_confirm_btn);
		// confirm();
		// // 获得 “重置” 按钮
		// resetButton = (Button) findViewById(R.id.userChangePw_reset_btn);
		// reset();
	}

	@Override
	public void finish() {
		super.finish();
		userDatabase.close();
		// 结束当前activity 的切换动画
		overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
	}

	/**
	 * 获得输入的旧密码、新密码、重新输入的新密码
	 */
	private void getInputMessage() {
		oldPw = oldPwEditText.getText().toString();
		newPw = newPwEditText.getText().toString();
		reNewPw = reNewPwEditText.getText().toString();
	}

	/**
	 * 控制是否显示密码
	 * 
	 * @param pwDispalyImageView
	 *            控制按钮图片
	 */
	private void pwDisplayControl(final ImageView pwDispalyImageView) {
		// 事件监听
		pwDispalyImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 按钮图片显示控制
				if (display == 0) {
					pwDispalyImageView.setImageResource(R.drawable.pw_display);
					display = 1;
				} else {
					pwDispalyImageView.setImageResource(R.drawable.pw_hind);
					display = 0;
				}

				// 显示密码
				if (display == 1) {
					oldPwEditText
							.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					newPwEditText
							.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					reNewPwEditText
							.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				}
				// 隐藏密码
				else {
					oldPwEditText.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_PASSWORD);
					newPwEditText.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_PASSWORD);
					reNewPwEditText.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}
			}
		});

	}

	/**
	 * 处理点击 “确认” 按钮事件
	 * 
	 * @param confirmButton
	 *            “确认”按钮
	 */
	private void confirm() {
		confirmButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * 将密码加密 发送密码相关信息。 然后判断密码有没有修改成功。 根据成功与否给出Toast提示
				 */
				// 修改成功
				userPw = User.getPW();
				getInputMessage();
				if (userPw.equals(oldPw)) {
					if (newPw.length() != 0) {
						if (newPw.equals(reNewPw)) {
							String sql = "UPDATE user SET userPw='" + newPw
									+ "' WHERE userAccount='" + userAccount
									+ "';";
							userDatabase.execSQL(sql);
							Toast.makeText(UserChangePwActivity.this,
									"修改密码成功!", Toast.LENGTH_SHORT).show();
							User.setPW(newPw);
							// 将输入框置空
							oldPwEditText.setText("");
							newPwEditText.setText("");
							reNewPwEditText.setText("");
						} else {
							Toast.makeText(UserChangePwActivity.this,
									"两次输入的新密码不相同!", Toast.LENGTH_LONG).show();
						}
					} else {
						Toast.makeText(UserChangePwActivity.this, "新密码不能为空!",
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(UserChangePwActivity.this, "输入的密码不正确!",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	/**
	 * 处理点击 “重置” 按钮事件
	 * 
	 * @param resetButton
	 *            “重置”按钮
	 */
	private void reset() {
		resetButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 将输入框置空
				oldPwEditText.setText("");
				newPwEditText.setText("");
				reNewPwEditText.setText("");
			}
		});
	}
}
