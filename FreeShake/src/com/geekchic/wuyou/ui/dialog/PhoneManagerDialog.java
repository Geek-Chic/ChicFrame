/**
 * @Title: PhoneManagerDialog.java
 * @Package com.geekchic.wuyou.ui.dialog
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 21, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.geekchic.common.utils.DisplayInfo;
import com.geekchic.framework.ui.dialog.BasicDialog;
import com.geekchic.wuyou.R;

/**
 * @ClassName: PhoneManagerDialog
 * @Descritpion: 换号标记对话框
 * @author evil
 * @date May 21, 2014
 */
public class PhoneManagerDialog implements OnClickListener{
	/**
	 * 基类对话框
	 */
  private BasicDialog mBasicDialog;
  /**
   * 根View
   */
  private View rootView;
  /**
   * 上下文
   */
  private Context mContext;
  /**
   * 号码管理对话框
   * PhoneManagerDialog构造函数
   * @param context
   */
  public PhoneManagerDialog(Context context){
	  this.mContext=context;
	  initView();
  }
  private void initView(){
	  LayoutInflater inflater=LayoutInflater.from(mContext);
	  rootView=inflater.inflate(R.layout.profile_dialog_phone_manager,null);
  }
  public PhoneManagerDialog create(){
	  mBasicDialog=new BasicDialog(mContext, R.style.AppDialog);
	  mBasicDialog.setContentView(rootView);
	    Window window = mBasicDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width =DisplayInfo.getScreenWidth(mContext);
        lp.height=DisplayInfo.getScreenHeight(mContext)/2;
        window.setAttributes(lp);
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
