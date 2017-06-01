package com.zqu.library.user;

import com.zqu.library.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * 显示”催还图书“界面
 * 
 * @author Aboo
 * 
 */
public class UserBookOverdueNoticeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_center_book_overdue_notice);

	}

	@Override
	public void finish() {
		super.finish();
		// 结束当前activity 的切换动画
		overridePendingTransition(R.anim.slide_in_up,
				R.anim.slide_out_down);
	}

}
