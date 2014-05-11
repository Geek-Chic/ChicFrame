/**
 * @Title: SettingActivity.java
 * @Package com.geekchic.wuyou.ui.setting
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 9, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.geekchic.constant.AppAction;
import com.geekchic.framework.ui.titlebar.BaseTitleBarActivity;
import com.geekchic.wuyou.R;

/**
 * @ClassName: SettingActivity
 * @Descritpion: 设置界面
 * @author evil
 * @date May 9, 2014
 */
public class SettingActivity extends BaseTitleBarActivity implements OnClickListener{
	/**
	 * 个人资料设置
	 */
	private View mAccountSettingView;
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}
    private void initView(){
    	mAccountSettingView=findViewById(R.id.setting_self_profile);
    	mAccountSettingView.setOnClickListener(this);
    	
    }
	@Override
	public int getLayoutId() {
		return R.layout.setting;
	}

	@Override
	public boolean initializeTitlBar() {
		setMiddleTitle(R.string.setting);
		setLeftButton(R.drawable.icon_tab_metra_back_selector, mBackClickListener);
		setTitleBarBackground(R.color.blue);
		return true;
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.setting_self_profile){
			Intent intent=new Intent(AppAction.ProfileSetting.ACTION);
			startActivity(intent);
		}
	}

}
