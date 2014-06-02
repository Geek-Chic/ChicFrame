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
import android.widget.Button;

import com.geekchic.common.utils.PreferencesUtils;
import com.geekchic.constant.AppAction;
import com.geekchic.constant.AppConfig;
import com.geekchic.constant.AppConstants.Common;
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
	 * 关于
	 */
	private View mAboutView;
	/**
	 * 反馈
	 */
	private View mFeedBackView;
	/**
	 * 登出
	 */
	private Button mLogout;
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
    	mLogout=(Button) findViewById(R.id.setting_logout);
    	mLogout.setOnClickListener(this);
    	mAboutView=findViewById(R.id.setting_about);
    	mAboutView.setOnClickListener(this);
    	mFeedBackView=findViewById(R.id.setting_feedback);
    	mFeedBackView.setOnClickListener(this);
    	
    }
	@Override
	public int getLayoutId() {
		return R.layout.setting;
	}

	@Override
	public boolean initializeTitlBar() {
		setMiddleTitle(R.string.setting);
		setTitleBarBackground(R.color.blue);
		setLeftButton(R.drawable.icon_tab_metra_back_selector, mBackClickListener);
		setTitleBarBackground(R.color.blue);
		return true;
	}
	/**
	 * 登出
	 */
	private void logOut(){
		//清除 userid和 sessionid
        PreferencesUtils.setAttr(Common.KEY_USER_ID, "");
        PreferencesUtils.setAttr(Common.KEY_SESSION_ID, "");
        //清除账户信息
        AppConfig.getInstance().setSessionId("");
        AppConfig.getInstance().setUid("");
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.setting_self_profile){
			Intent intent=new Intent(AppAction.ProfileSetting.ACTION);
			startActivity(intent);
		}else if(v.getId()==R.id.setting_logout){
           logOut();
           Intent intent=new Intent(AppAction.LoginAction.ACTION);
           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           startActivity(intent);
           finishAllAcitivity();
		}else if(v.getId()==R.id.setting_about){
			Intent intent=new Intent(AppAction.About.ACTION);
			startActivity(intent);
		}else if(v.getId()==R.id.setting_feedback){
			Intent intent=new Intent(AppAction.FeekBack.ACTION);
			startActivity(intent);
		}
	}

}
