/**
 * @Title: ProgressDialog.java
 * @Package com.geekchic.framework.ui.dialog
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 29, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.ui.dialog;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.geekchic.wuyou.R;

/**
 * @ClassName: ProgressDialog
 * @Descritpion: 加载对话框
 * @author evil
 * @date Apr 29, 2014
 */
public class ProgressDialog {
	private BasicDialog mBasicDialog;

	public ProgressDialog(Context context) {
		this(context, 0);
	}

	public ProgressDialog(Context context, int theme) {
		this(context, theme, true);
	}
	

	public ProgressDialog(Context context, int theme, boolean isCancleable) {
		mBasicDialog = new BasicDialog(context, theme, isCancleable);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dialog_progress, null);
		mBasicDialog.setContentView(view);
	}
	/**
     * 设置需要显示的消息<BR>
     * @param strMessage 需要显示的提示消息
     * @return progressdialog实例
     */
    public ProgressDialog setMessage(String message)
    {
        TextView contextTextView = (TextView) mBasicDialog.getContentView()
                .findViewById(R.id.progress_msg);
        
        if (null != contextTextView)
        {
        	contextTextView.setText(message);
        }
        return this;
    }
	/**
	 * showdialog<BR>
	 */
	public void show() {
		mBasicDialog.show();
	}

	/**
	 * 关闭dialog<BR>
	 */
	public void dismiss() {
		mBasicDialog.dismiss();
	}

	/**
	 * dialog是否在显示<BR>
	 * 
	 * @return true 在显示 ；false 不在显示
	 */
	public boolean isShowing() {
		return mBasicDialog.isShowing();
	}

	public void setOnCancelListener(OnCancelListener onCancelListener) {
		mBasicDialog.setOnCancelListener(onCancelListener);
	}

	public void setOnKeyListener(OnKeyListener onKeyListener) {
		mBasicDialog.setOnKeyListener(onKeyListener);
	}

}
