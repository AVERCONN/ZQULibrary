package com.zqu.library;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 关于界面的显示
 * 
 * @author Aboo
 * 
 */
public class AboutActivity extends Activity {

	View mBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		mBack = findViewById(R.id.about_titlebar_title);
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AboutActivity.this.finish();
			}
		});
	}

	@Override
	public void finish() {
		super.finish();
		// 结束当前activity 的切换动画
		overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
	}

}
