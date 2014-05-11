/**
 * @Title: ProfileSetting.java
 * @Package com.geekchic.wuyou.ui.setting
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 11, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.setting;

import android.view.View;
import android.view.View.OnClickListener;

import com.geekchic.framework.ui.titlebar.BaseTitleBarActivity;
import com.geekchic.wuyou.R;

/**
 * @ClassName: ProfileSetting
 * @Descritpion: 个人资料设置
 * @author evil
 * @date May 11, 2014
 */
public class ProfileSetting extends BaseTitleBarActivity {

	/**
	 * 后退
	 */
	private OnClickListener mBackClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
           finishActivity();			
		}
	};
	@Override
	public int getLayoutId() {
		return R.layout.profile_setting_layout;
	}

	@Override
	public boolean initializeTitlBar() {
		setMiddleTitle(R.string.setting);
		setLeftButton(R.drawable.icon_tab_metra_back_selector, mBackClickListener);
		setTitleBarBackground(R.color.blue);
		return true;
	}

}
