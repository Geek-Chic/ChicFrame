/**
 * @Title: RegisterActivity.java
 * @Package com.geekchic.wuyou.ui.login
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 1, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.geekchic.constant.AppAction.MainAction;
import com.geekchic.framework.ui.titlebar.BaseTitleBarActivity;
import com.geekchic.wuyou.R;

/**
 * @ClassName: RegisterActivity
 * @Descritpion: 注册
 * @author evil
 * @date May 1, 2014
 */
public class RegisterActivity extends BaseTitleBarActivity implements
		OnClickListener {
	/**
	 * TAG
	 */
	@SuppressWarnings("unused")
	private static final String TAG = "RegisterActivity";
	/**
	 * 步骤一
	 */
	private RadioButton mStepFirstRadioButton;
	/**
	 * 步骤二
	 */
	private RadioButton mStepSecondRadioButton;
	/**
	 * 步骤三
	 */
	private RadioButton mStepThirdRadioButton;
	/**
	 * 步骤组
	 */
	private RadioGroup mStepRadioGroup;

	/**
	 * fragment管理类
	 */
	private FragmentManager mFragmentManager;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 后退
	 */
	private OnClickListener mBackClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			onBackPressed();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();

	}

	private void initView() {
		mFragmentManager = getSupportFragmentManager();
		RegisterStepOneFragment registerStepOneFragment = new RegisterStepOneFragment();
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		transaction.replace(R.id.register_container_fragment,
				registerStepOneFragment);
		transaction.commit();

		mStepRadioGroup = (RadioGroup) findViewById(R.id.register_tag_radiogroup);
		mStepFirstRadioButton = (RadioButton) findViewById(R.id.register_tag_first);
		mStepSecondRadioButton = (RadioButton) findViewById(R.id.register_tag_second);
		mStepThirdRadioButton = (RadioButton) findViewById(R.id.register_tag_third);

		mStepFirstRadioButton.setChecked(true);
	}

	@Override
	public int getLayoutId() {
		return R.layout.register_container;
	}

	@Override
	public boolean initializeTitlBar() {
		setMiddleTitle(R.string.registration);
		setLeftButton(R.drawable.icon_tab_metra_back_selector,
				mBackClickListener);
		setTitleBarBackground(R.color.blue);
		return true;
	}

	@Override
	protected boolean needLogin() {
		return false;
	}

	@Override
	public void onClick(View v) {
	}

	/**
	 * 缓存电话号码
	 * 
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 跳转至第二步
	 */
	public void stepToSecondFragment() {
		RegisterStepTwoFragment registerStepTwoFragment = new RegisterStepTwoFragment();
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		transaction.replace(R.id.register_container_fragment,
				registerStepTwoFragment);
		transaction.commit();

		mStepFirstRadioButton.setChecked(false);
		mStepSecondRadioButton.setChecked(true);
	}

	/**
	 * 跳转至第三步
	 */
	public void stepToThirdFragment() {
		RegisterStepThirdFragment registerStepThirdFragment = RegisterStepThirdFragment
				.newInstance(phone);
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		transaction.replace(R.id.register_container_fragment,
				registerStepThirdFragment);
		transaction.commit();

		mStepSecondRadioButton.setChecked(false);
		mStepThirdRadioButton.setChecked(true);
	}

	/**
	 * 跳转到主页面
	 */
	public void stepToMain() {
		Intent intent = new Intent(MainAction.ACTION);
		startActivity(intent);
		finishAllAcitivity();
	}
}
