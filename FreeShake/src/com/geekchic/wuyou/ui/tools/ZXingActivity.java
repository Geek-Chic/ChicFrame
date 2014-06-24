/**
 * @Title: ZXingActivity.java
 * @Package com.geekchic.wuyou.ui.tools
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 6, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.tools;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.geekchic.framework.ui.BaseTabHostActivity;
import com.geekchic.wuyou.R;

/**
 * @ClassName: ZXingActivity
 * @Descritpion: 扫描二维码
 * @author evil
 * @date May 6, 2014
 */
public class ZXingActivity extends BaseTabHostActivity {
	/**
	 * TAG
	 */
	private static final String TAG = "ZXingActivity";
    private OnClickListener mBackClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finishActivity();
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean initializeTitlBar() {
		setMiddleTitle(R.string.zxing_title);
		setLeftButton(R.drawable.icon_tab_back_selector, mBackClickListener);
		return true;
	}
   @Override
protected boolean needLogin() {
	return false;
}


@Override
protected TabInfo[] getTabInfos() {
	TabInfo[] tabInfos=new TabInfo[1];
	tabInfos[0]=new TabInfo("QrScanFragment", R.string.app_name, R.drawable.ic_launcher, QrScanFragment.class, null,false);
	return tabInfos;
}
}
