/**
 * @Title: NavigateView.java
 * @Package com.geekchic.wuyou.ui.view
 * @Description: 使用导航
 * @author: evil
 * @date: May 4, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.geekchic.common.utils.PreferencesUtil;
import com.geekchic.constant.AppConstants;
import com.geekchic.framework.ui.BaseFrameActivity;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.ui.view.PagerSlidingTabStrip.IconTabProvider;

/**
 * @ClassName: NavigateView
 * @Descritpion: 使用导航
 * @author evil
 * @date May 4, 2014
 */
public class NavigateActivity extends BaseFrameActivity {
	private ViewPager mContentPager;
	private PagerSlidingTabStrip mBgPagerSlidingTabStrip;
	private MyPagerAdapter myPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigate_layout);
		initView();
		//标记为已显示
		PreferencesUtil.setAttr(AppConstants.Common.NAV_HAS_SHOW, true);
	}

	private void initView() {
		mContentPager = (ViewPager) findViewById(R.id.nav_content);
		mBgPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.nav_bg);
		myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
		mContentPager.setAdapter(myPagerAdapter);
		mBgPagerSlidingTabStrip.setViewPager(mContentPager);
	}

	@Override
	protected boolean needLogin() {
		// TODO Auto-generated method stub
		return false;
	}
    @Override
    public void finishActivity() {
    	// TODO Auto-generated method stub
    	super.finishActivity();
    }
	public class MyPagerAdapter extends FragmentPagerAdapter implements
			IconTabProvider {
		private final int[] navBelowIds = { R.drawable.nav_one, R.drawable.nav_two,
				R.drawable.nav_three, R.drawable.nav_four };
     
		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getPageIconResId(int position) {
			// TODO Auto-generated method stub
			return navBelowIds[position];
		}

		@Override
		public Fragment getItem(int paramInt) {
			return NavDesFragment.newInstance(paramInt);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return navBelowIds.length;
		}

	}
}
