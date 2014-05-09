/**
 * @Title: SettingActivity.java
 * @Package com.geekchic.wuyou.ui.setting
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 9, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.setting;

import android.view.View;
import android.view.View.OnClickListener;

import com.geekchic.framework.ui.titlebar.BaseTitleBarActivity;
import com.geekchic.wuyou.R;

/**
 * @ClassName: SettingActivity
 * @Descritpion: 设置界面
 * @author evil
 * @date May 9, 2014
 */
public class SettingActivity extends BaseTitleBarActivity{
    /**
     * 后退
     */
	private OnClickListener mBackClickListener=new OnClickListener(){

		@Override
		public void onClick(View v) {
			finishActivity();
		}
		
	};
	@Override
	public int getLayoutId() {
		return R.layout.setting;
	}

	@Override
	public boolean initializeTitlBar() {
		setMiddleTitle("设置");
		setLeftButton(R.drawable.icon_tab_back_selector, mBackClickListener);
		return true;
	}

}
