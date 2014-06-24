/**
 * @Title: PermissionDialog.java
 * @Package com.geekchic.wuyou.ui.dialog
 * @Description: 用户权限管理
 * @author: evil
 * @date: May 21, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.dialog;

import com.geekchic.framework.ui.dialog.BasicDialog;
import com.geekchic.wuyou.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @ClassName: PermissionDialog
 * @Descritpion: 用户权限管理
 * @author evil
 * @date May 21, 2014
 */
public class PermissionDialog implements OnClickListener {
	/**
	 * 权限管理对话框
	 */
   private BasicDialog mBasicDialog;
   /**
    * 根View
    */
   private View mRootView;
   /**
    * 上下文
    */
   private Context mContext;
   /**
    * PermissionDialog构造函数
    * @param context
    */
   public PermissionDialog(Context context){
	   this.mContext=context;
	   initView();
   }
	private void initView() {
	mRootView=LayoutInflater.from(mContext).inflate(R.layout.contact_permission_dialog, null);
}
	public PermissionDialog create(){
		mBasicDialog=new BasicDialog(mContext, R.style.AppDialog);
		mBasicDialog.setContentView(mRootView);
		return this;
	}
	/**
	 * 显示
	 */
	public void show(){
		mBasicDialog.show();
	}
	/**
	 * 消失
	 */
	public void dismiss(){
		mBasicDialog.dismiss();
	}
	@Override
	public void onClick(View v) {
		
	}

}
