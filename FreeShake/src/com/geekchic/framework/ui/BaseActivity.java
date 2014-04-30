/**
 * @Title: BaseActivity.java
 * @Package com.geekchic.framework.ui
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 29, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

import com.geekchic.common.log.Logger;
import com.geekchic.common.utils.PreferencesUtil;
import com.geekchic.constant.AppConfig;
import com.geekchic.constant.AppConstants.Common;
import com.geekchic.constant.AppManager;

/**
 * @ClassName: BaseActivity
 * @Descritpion: [用一句话描述作用]
 * @author evil
 * @date Apr 29, 2014
 */
public class BaseActivity extends FragmentActivity {
	/**
	 * TAG
	 */
	private static final String TAG = "BaseActivity";
	/**
	 * 当前Activity是否Pause
	 */
	private boolean mPaused;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		init();
	}

	private void init() {
		AppConfig.getInstance().setSessionId(
				PreferencesUtil.getAttrString(Common.KEY_SESSION_ID));
		AppConfig.getInstance().setUid(
				PreferencesUtil.getAttrString(Common.KEY_USER_ID));

	}
    /**
     * 判断是否已经登录
     * @return
     */
	public boolean hasLogined() {
		return !TextUtils.isEmpty(AppConfig.getInstance().getSessionId());
	}
	 /**
     * 当前Activity是否处于暂停状态<BR>
     * @return 是否处于暂停状态
     */
    protected boolean isActivityPause()
    {
        return mPaused;
    }
    /**
     * 返回一个boolean表示展示该页面是否需要登录成功
     * @return boolean 是否是登录后的页面
     */
    protected boolean needLogin()
    {
        return true;
    }
    /**
     * 获取shared preferences<BR>
     * @return SharedPreferences
     */
    public SharedPreferences getSharedPreferences()
    {
        return getSharedPreferences(Common.SHARED_PREFERENCE_NAME,
                Context.MODE_PRIVATE);
    }
    /**
     * 设置已经成功登录<BR>
     * @param sessionid 登陆后纪录sessionid来判断是否登陆
     */
    protected void setLogined(String sessionid)
    {
        PreferencesUtil.setAttr(Common.KEY_SESSION_ID, sessionid);
    }
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		Logger.d(TAG, "BaseActivity:" + this + "onPause");
		mPaused = true;
		super.onPause();
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		if (null != imm && imm.isActive()) {
			if (null != this.getCurrentFocus()
					&& null != this.getCurrentFocus().getWindowToken()) {
				imm.hideSoftInputFromWindow(this.getCurrentFocus()
						.getApplicationWindowToken(), 0);
			}
		}
	}

	@Override
	protected void onResume() {
		mPaused = false;
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void finish() {
		AppManager.getAppManager().finishActivity(this);
		super.finish();
	}
}
