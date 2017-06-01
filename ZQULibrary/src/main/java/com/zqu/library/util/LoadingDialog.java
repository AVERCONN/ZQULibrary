package com.zqu.library.util;

import com.zqu.library.R;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.TextView;

public class LoadingDialog {

	private AlertDialog.Builder mBuilder = null;

	public LoadingDialog(Context mContext) {
		mBuilder = new AlertDialog.Builder(mContext);
	}

	public AlertDialog createLoadingDialog(String tips) {
		AlertDialog mLoginLoadingDialog = mBuilder.show();
		mLoginLoadingDialog.setCanceledOnTouchOutside(false);
		mLoginLoadingDialog.setContentView(R.layout.dialog_loading);
		TextView tipTV = (TextView) mLoginLoadingDialog
				.findViewById(R.id.dialogLoading_tip);
		tipTV.setText(tips);

		return mLoginLoadingDialog;
	}

}
