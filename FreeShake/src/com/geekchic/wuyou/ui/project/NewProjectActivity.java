/**
 * @Title: NewTaskActivity.java
 * @Package com.geekchic.wuyou.ui.task
 * @Description: 创建项目
 * @author: evil
 * @date: May 10, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.project;

import android.view.View;
import android.view.View.OnClickListener;

import com.geekchic.framework.ui.titlebar.BaseTitleBarActivity;
import com.geekchic.wuyou.R;

/**
 * @ClassName: NewProjectActivity
 * @Descritpion: 创建项目
 * @author evil
 * @date May 10, 2014
 */
public class NewProjectActivity extends BaseTitleBarActivity{

	private OnClickListener mBackClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finishActivity();
		}
	};
	private OnClickListener mCreateClickLinstener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
		}
	};
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.project_new;
	}

	@Override
	public boolean initializeTitlBar() {
		setMiddleTitle(R.string.project_create_title);
		setLeftButton(R.drawable.icon_tab_metra_back_selector, mBackClickListener);
		setRightButton(R.drawable.icon_tab_metra_back_selector, mCreateClickLinstener);
		setTitleBarBackground(R.color.blue);
		return true;
	}

}
