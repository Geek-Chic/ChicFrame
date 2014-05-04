/**
 * @Title: ProgressDialog.java
 * @Package com.geekchic.framework.ui.dialog
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 29, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.TextView;

import com.geekchic.wuyou.R;

/**
 * @ClassName: ProgressDialog
 * @Descritpion: 加载对话框
 * @author evil
 * @date Apr 29, 2014
 */
public class ProgressDialog extends Dialog {
	/**
	 * 基本对话框
	 */
	private static ProgressDialog mBasicDialog = null;
	/**
	 * 加载动画
	 */
	private AnimationDrawable mLoadingAnimationDrawable;
	/**
	 * 加载视图
	 */
	private View mLoadingView;
	/**
	 * context
	 */
	private Context mContext;

	public ProgressDialog(Context context) {
		super(context);
	}

	public ProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public static ProgressDialog createProgressDialog(Context context) {
		mBasicDialog = new ProgressDialog(context, R.style.AppDialog);
		mBasicDialog.setContentView(R.layout.dialog_progress);
		return mBasicDialog;
	}
	/**
	 * 设置需要显示的消息<BR>
	 * 
	 * @param strMessage
	 *            需要显示的提示消息
	 * @return progressdialog实例
	 */
	public ProgressDialog setMessage(String message) {
		TextView contextTextView = (TextView) mBasicDialog
				.findViewById(R.id.progress_msg);

		if (null != contextTextView) {
			contextTextView.setText(message);
		}
		return this;
	}


	public void setOnCancelListener(OnCancelListener onCancelListener) {
		mBasicDialog.setOnCancelListener(onCancelListener);
	}

	public void setOnKeyListener(OnKeyListener onKeyListener) {
		mBasicDialog.setOnKeyListener(onKeyListener);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		if (mBasicDialog == null) {
			return;
		}
		mLoadingView = mBasicDialog.findViewById(R.id.dialog_loading);
		mLoadingAnimationDrawable = (AnimationDrawable) mLoadingView
				.getBackground();
		mLoadingAnimationDrawable.start();

	}

}
