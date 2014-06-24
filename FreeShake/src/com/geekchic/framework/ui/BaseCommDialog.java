/**
 * @Title: BasicCommDialog.java
 * @Package com.geekchic.framework.ui
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.ui;

import android.content.Context;

import com.geekchic.framework.ui.dialog.ProgressDialog;

/**
 * @ClassName: BasicCommDialog
 * @Descritpion: [用一句话描述作用]
 * @author evil
 * @date Apr 30, 2014
 */
public class BaseCommDialog implements CommonDialogInterface {
	/**
	 * 等待对话框
	 */
	private ProgressDialog mProgressDialog;
	private Context mContext;

	public BaseCommDialog(Context context) {
		this.mContext = context;
	}

	@Override
	public void showProgressDialog(String message, boolean cancelable) {
		mProgressDialog = ProgressDialog.createProgressDialog(mContext);
		mProgressDialog.setMessage(message);
		mProgressDialog.setCancelable(cancelable);
		mProgressDialog.show();
	}

	@Override
	public void showProgressDialog(int messageId, boolean cancelable) {
		String message = mContext.getResources().getString(messageId);
		showProgressDialog(message, cancelable);
	}

	@Override
	public void closeProgressDialog() {
		if (null != mProgressDialog && mProgressDialog.isShowing()) {
			this.mProgressDialog.dismiss();
		}
	}

}
