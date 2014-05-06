/**
 * @Title: BaseTitleBarFragment.java
 * @Package com.geekchic.framework.ui.titlebar
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 5, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.ui.titlebar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.geekchic.framework.ui.BaseFrameFragment;
import com.geekchic.wuyou.R;

/**
 * @ClassName: BaseTitleBarFragment
 * @Descritpion: [用一句话描述作用]
 * @author evil
 * @date May 5, 2014
 */
public abstract class BaseTitleBarFragment extends BaseFrameFragment implements
		CommonTitleBarInterface {
	/**
	 * TitleBar
	 */
	private TitleBar mTitleBar;

	/**
	 * 子视图的容器
	 */
	private LinearLayout mContainLayout;

	/**
	 * 根视图
	 */
	private View mRootView = null;

	/**
	 * 默认视图 在未登录的时候展示
	 */
	// private DefaultView mDefaultView;

	/**
	 * 登录后显示的真实数据视图
	 */
	private View mContentView;

	private CommonTitleBarInterface mTitlebarInterface;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	        initView(inflater);
	        return mRootView;
	}
     private void initView(LayoutInflater inflater){
    	 mRootView = inflater.inflate(R.layout.frame_titlebar_contain, null);
    	 mContainLayout = (LinearLayout) mRootView.findViewById(R.id.content_container);
    	 mTitlebarInterface=new BaseTitleBar(mTitleBar);
    		mContainLayout = (LinearLayout) mRootView.findViewById(R.id.content_container);
    		View contentView = inflater.inflate(getLayoutId(), null);
    		mContainLayout.addView(contentView, LayoutParams.MATCH_PARENT,
    				LayoutParams.MATCH_PARENT);
    		   if (!initializeTitlBar())
    	        {
    	            mTitleBar.hideTitleBar();
    	            return;
    	        }
     }
	@Override 
	public void setLeftButton(int id, OnClickListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRightButton(int id, OnClickListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMiddleTitle(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMiddleTitle(String title) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMiddleTitleDrawable(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTitleBarBackground(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTitleVisible(boolean visible) {
		// TODO Auto-generated method stub

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
