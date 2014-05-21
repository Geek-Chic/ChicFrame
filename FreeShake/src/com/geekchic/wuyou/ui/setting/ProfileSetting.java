/**
 * @Title: ProfileSetting.java
 * @Package com.geekchic.wuyou.ui.setting
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 11, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.geekchic.framework.ui.titlebar.BaseTitleBarActivity;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.ui.dialog.PermissionDialog;
import com.geekchic.wuyou.ui.dialog.PhoneManagerDialog;

/**
 * @ClassName: ProfileSetting
 * @Descritpion: 个人资料设置
 * @author evil
 * @date May 11, 2014
 */
public class ProfileSetting extends BaseTitleBarActivity implements
		OnClickListener {
	private View mPhoneManagerView;
	private View mPermissionView;
	/**
	 * 后退
	 */
	private OnClickListener mBackClickListener = new OnClickListener() {

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
		setLeftButton(R.drawable.icon_tab_metra_back_selector,
				mBackClickListener);
		setTitleBarBackground(R.color.blue);
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		mPhoneManagerView = findViewById(R.id.set_bind_old_phone);
		mPermissionView=findViewById(R.id.set_contact_permission);
		mPermissionView.setOnClickListener(this);
		mPhoneManagerView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.set_bind_old_phone:
			PhoneManagerDialog phoneManagerDialog = new PhoneManagerDialog(this);
			phoneManagerDialog = phoneManagerDialog.create();
			phoneManagerDialog.show();
			break;
		case R.id.set_contact_permission:
			PermissionDialog permissionDialog=new PermissionDialog(this);
			permissionDialog=permissionDialog.create();
			permissionDialog.show();
			break;
		default:
			break;
		}
	}
}
