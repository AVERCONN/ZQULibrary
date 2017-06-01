package com.zqu.library;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 标题栏返回按钮处理类
 * @author Aboo
 *
 */
public class TitleBarBackEvent extends Activity{

	/**
	 * 处理点击标题栏中的 “返回” 按钮事件
	 * @param backButton “返回”按钮
	 * @param context 传入的上下文
	 */
	public void back(final Button backButton ,final Context context){
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//关闭当前 activity
				((Activity)context).finish();
			}
		});
	}
}
