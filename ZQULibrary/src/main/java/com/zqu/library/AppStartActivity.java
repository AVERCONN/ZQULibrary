package com.zqu.library;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 应用开场界面
 * 
 * @author Aboo
 * 
 */
public class AppStartActivity extends Activity {
	/** Splash的背景资源 */
	int[] splashBg;
	/** 名言字符串资源 */
	int[] splashSentence;
	/** Splash的布局 */
	RelativeLayout splashRl;
	/** 要显示名言句子的TextView */
	TextView splashSentenceTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		splashBg = new int[] { R.drawable.splash_bg1, R.drawable.splash_bg2,
				R.drawable.splash_bg3, R.drawable.splash_bg4 };
		splashSentence = new int[] { R.string.Splash_sentence_1,
				R.string.Splash_sentence_2, R.string.Splash_sentence_3 };

		splashRl = (RelativeLayout) findViewById(R.id.Splash_rl);
		splashSentenceTextView = (TextView) findViewById(R.id.Splash_sentence_tv);

		Random random = new Random();
		int i = random.nextInt(3) + 1;
		int j = random.nextInt(2) + 1;

		splashRl.setBackgroundResource(splashBg[i]);
		splashSentenceTextView.setText(splashSentence[j]);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent mainIntent = new Intent(AppStartActivity.this,
						MainActivity.class);
				startActivity(mainIntent);
				overridePendingTransition(R.anim.start_left_in,
						R.anim.start_right_out);
				finish();
			}
		}, 2500);
	}

//	@Override
//	public void finish() {
//		super.finish();
//		overridePendingTransition(R.anim.end_left_in, R.anim.end_right_out);
//	}

}
