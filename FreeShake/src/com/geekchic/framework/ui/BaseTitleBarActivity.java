/**
 * @Title: BaseTitleBarActivity.java
 * @Package com.geekchic.framework.ui.dialog
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.geekchic.wuyou.R;

/**
 * @ClassName: BaseTitleBarActivity
 * @Descritpion: [用一句话描述作用]
 * @author evil
 * @date Apr 30, 2014
 */
public abstract class BaseTitleBarActivity extends BaseFrameActivity implements
		CommonTitleBarInterface {
	/**
	 * Titlebar
	 */
	private TitleBar mTitleBar;
	/**
	 * 容器
	 */
	private LinearLayout mContainLayout;
	/**
	 * Titlebar接口
	 */
	private CommonTitleBarInterface mBasicTitleBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frame_titlebar_contain);
		initView();
	}

	private void initView() {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mTitleBar=(TitleBar) findViewById(R.id.title_bar_layout);
		mBasicTitleBar=new BaseTitleBar(mTitleBar);
		mContainLayout = (LinearLayout) findViewById(R.id.content_container);
		View contentView = inflater.inflate(getLayoutId(), null);
		mContainLayout.addView(contentView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		
		if(!initializeTitlBar()){
			mTitleBar.hideTitleBar();
			return;
		}
	}

	@Override
	public void setLeftButton(int id, OnClickListener listener) {
       mBasicTitleBar.setLeftButton(id, listener);
	}

	@Override
	public void setRightButton(int id, OnClickListener listener) {
       mBasicTitleBar.setRightButton(id, listener);
	}

	@Override
	public void setMiddleTitle(String title) {
       mBasicTitleBar.setMiddleTitle(title);
	}
     @Override
    public void setMiddleTitle(int titleId) {
    	 mBasicTitleBar.setMiddleTitle(titleId);
    }
	@Override
	public void setTitleBarBackground(int id) {
       mBasicTitleBar.setTitleBarBackground(id);
	}

	@Override
	public void setTitleVisible(boolean visible) {
       mBasicTitleBar.setTitleVisible(visible);
	}
	@Override
	public void setMiddleTitleDrawable(int id) {
		mBasicTitleBar.setMiddleTitleDrawable(id);
	}

	/**
	 * 获取布局
	 * 
	 * @return
	 */
	public abstract int getLayoutId();
	/**
	 * 初始化TitleBar
	 * @return
	 */
	public abstract boolean initializeTitlBar();
}
