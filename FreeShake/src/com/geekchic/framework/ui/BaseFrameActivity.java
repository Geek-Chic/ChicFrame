/**
 * @Title: BaseNetActivity.java
 * @Package com.geekchic.framework.ui
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.ui;

import com.geekchic.common.log.Logger;

import android.os.Bundle;

/**
 * @ClassName: BaseNetActivity
 * @Descritpion: 扩展通用UI
 * @author evil
 * @date Apr 30, 2014
 */
public class BaseFrameActivity extends BaseActivity implements CommonDialogInterface {
	/**
	 * TAG
	 */
	private static final String TAG="BaseFrameActivity";
	/**
	 * 通用UI操作
	 */
	private CommonDialogInterface mCommonDialogInterface;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if(needLogin() && !hasLogined()){
			finish();
			Logger.e(TAG,"this activity need login");
			return;
		}
		super.onCreate(savedInstanceState);
		mCommonDialogInterface = new BasicCommDialog(this);
	}
	@Override
	public void showProgressDialog(String message, boolean cancelable) {
		if (mCommonDialogInterface != null) {
			mCommonDialogInterface.showProgressDialog(message, cancelable);
		}
	}
 
}
