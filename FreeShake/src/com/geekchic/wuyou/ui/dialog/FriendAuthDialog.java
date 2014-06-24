/**
 * @Title: FriendAuthDialog.java
 * @Package com.geekchic.wuyou.ui.dialog
 * @Description: 好友验证请求
 * @author: evil
 * @date: May 23, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.geekchic.framework.ui.dialog.BasicDialog;
import com.geekchic.wuyou.R;

/**
 * @ClassName: FriendAuthDialog
 * @Descritpion: 好友验证请求
 * @author evil
 * @date May 23, 2014
 */
public class FriendAuthDialog implements OnClickListener {
	private BasicDialog mBasicDialog;
	private View mRootView;
	private Context mContext;
	private Button mConfirmButton;

	/**
	 * FriendAuthDialog构造函数
	 * 
	 * @param context
	 */
	public FriendAuthDialog(Context context) {
		this.mContext = context;
		initView();
	}

	private void initView() {
		mRootView = LayoutInflater.from(mContext).inflate(
				R.layout.friend_auth_dialog, null);
	}

	public FriendAuthDialog create() {
		mBasicDialog = new BasicDialog.Builder(mContext).setContentView(R.layout.friend_auth_dialog).setTitle("蒋鹏请求认证好友")
				.create();
		return this;
	}
    public void show(){
    	mBasicDialog.show();
    }
    public void dismiss(){
    	mBasicDialog.dismiss();
    }
	@Override
	public void onClick(View v) {

	}

}
