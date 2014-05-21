/**
 * @Title: FuzzySearchDialog.java
 * @Package com.geekchic.wuyou.ui.dialog
 * @Description: 模糊查找Dialog
 * @author: evil
 * @date: May 21, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.geekchic.framework.ui.dialog.BasicDialog;
import com.geekchic.wuyou.R;

/**
 * @ClassName: FuzzySearchDialog
 * @Descritpion: 模糊查找Dialog
 * @author evil
 * @date May 21, 2014
 */
public class FuzzySearchDialog {
	/**
	 * 上下文
	 */
  private Context mContext;
  /**
   * RootView
   */
  private View mRootView;
  /**
   * 模糊查找Dialog
   */
  private BasicDialog mFuzzySearchBasicDialog;
  /**
   * FuzzySearchDialog构造函数
   * @param context
   */
  public FuzzySearchDialog(Context context){
	  this.mContext=context;
	  initView();
  }
  private void initView(){
	  LayoutInflater inflater=LayoutInflater.from(mContext);
	  mRootView=inflater.inflate(R.layout.fuzzy_search_dialog, null);
  }
  public FuzzySearchDialog create(){
	  mFuzzySearchBasicDialog=new BasicDialog(mContext, R.style.AppDialog);
	  mFuzzySearchBasicDialog.setContentView(mRootView);
	  return this;
  }
  /**
   * 显示对话框 
   */
  public void show(){
	  mFuzzySearchBasicDialog.show();
  }
  /**
   * 隐藏对话框
   */
  public void dismiss(){
	  mFuzzySearchBasicDialog.dismiss();
  }
  
}
