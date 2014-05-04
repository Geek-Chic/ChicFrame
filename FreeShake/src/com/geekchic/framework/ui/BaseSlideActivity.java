/**
 * @Title: BaseSlideActivity.java
 * @Package com.geekchic.framework.ui
 * @Description: 侧滑Activity 
 * @author: evil
 * @date: May 3, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.widget.slidingmenu.SlidingActivityHelper;
import com.widget.slidingmenu.SlidingMenu;

/**
 * @ClassName: BaseSlideActivity
 * @Descritpion: 侧滑Activity
 * @author evil
 * @date May 3, 2014
 */
public abstract class BaseSlideActivity extends BaseTitleBarActivity {
	/**
	 * 侧滑操作Helper
	 */
	private SlidingActivityHelper mHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mHelper = new SlidingActivityHelper(this);
		mHelper.onCreate(savedInstanceState);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mHelper.onPostCreate(savedInstanceState);
	}

	@Override
	public View findViewById(int id) {
		View v = super.findViewById(id);
		if (v != null)
			return v;
		return mHelper.findViewById(id);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mHelper.onSaveInstanceState(outState);
	}

	@Override
	public void setContentView(int layoutResID) {
		setContentView(getLayoutInflater().inflate(layoutResID, null));

	}

	@Override
	public void setContentView(View v) {
		setContentView(v, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
	}

	@Override
	public void setContentView(View v, LayoutParams params) {
		super.setContentView(v, params);
		mHelper.registerAboveContentView(v, params);
	}

	public void setBehindContentView(int id) {
		setBehindContentView(getLayoutInflater().inflate(id, null));
	}

	public void setBehindContentView(View v) {
		setBehindContentView(v, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
	}

	public void setBehindContentView(View v, LayoutParams params) {
		mHelper.setBehindContentView(v, params);
	}

	public SlidingMenu getSlidingMenu() {
		return mHelper.getSlidingMenu();
	}

	public void toggle() {
		mHelper.toggle();
	}

	public void showContent() {
		mHelper.showContent();
	}

	public void showMenu() {
		mHelper.showMenu();
	}

	public void showSecondaryMenu() {
		mHelper.showSecondaryMenu();
	}

	public void setSlidingActionBarEnabled(boolean b) {
		mHelper.setSlidingActionBarEnabled(b);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean b = mHelper.onKeyUp(keyCode, event);
		if (b)
			return b;
		return super.onKeyUp(keyCode, event);
	}

	@Override
	protected boolean needLogin() {
		return false;
	}

	@Override
	public boolean initializeTitlBar() {
		return false;
	}

}
