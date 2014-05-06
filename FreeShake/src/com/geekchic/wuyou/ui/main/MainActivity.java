/**
 * @Title: MainActivity.java
 * @Package com.geekchic.wuyou.ui.main
 * @Description: 主界面
 * @author: evil
 * @date: May 3, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.geekchic.common.utils.DisplayInfo;
import com.geekchic.constant.AppAction;
import com.geekchic.framework.ui.BaseSlideActivity;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.ui.dialog.QuickAction;
import com.geekchic.wuyou.ui.dialog.ToolsActionDialog;
import com.geekchic.wuyou.ui.dialog.ToolsActionWidget;
import com.geekchic.wuyou.ui.dialog.ToolsActionWidget.OnQuickActionClickListener;
import com.widget.slidingmenu.SlidingMenu;

/**
 * @ClassName: MainActivity
 * @Descritpion: 主界面
 * @author evil
 * @date May 3, 2014
 */
public class MainActivity extends BaseSlideActivity {
	/**
	 * 侧滑菜单
	 */
	private SlidingMenu mSlidingMenu;
	/**
	 * 用户Fragment
	 */
	private ProfileFragment mProfileFragment;
	/**
	 * 联系人Framgment
	 */
	private ContactsFragment mContactsFragment;
	/**
	 * 联系人click
	 */
	private OnClickListener mLeftClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mSlidingMenu.showMenu(true);
		}
	};
	/**
	 * 信息click
	 */
	private OnClickListener mRightClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			ToolsActionDialog mGrid = new ToolsActionDialog(MainActivity.this);
			mGrid.addQuickAction(new QuickAction(MainActivity.this,
					R.drawable.ic_launcher, "二维码"));
			mGrid.addQuickAction(new QuickAction(MainActivity.this,
					R.drawable.ic_launcher, "二维码"));
			mGrid.addQuickAction(new QuickAction(MainActivity.this,
					R.drawable.ic_launcher, "二维码"));
			mGrid.addQuickAction(new QuickAction(MainActivity.this,
					R.drawable.ic_launcher, "二维码"));
			mGrid.addQuickAction(new QuickAction(MainActivity.this,
					R.drawable.ic_launcher, "二维码"));
			mGrid.addQuickAction(new QuickAction(MainActivity.this,
					R.drawable.ic_launcher, "二维码"));
			mGrid.addQuickAction(new QuickAction(MainActivity.this,
					R.drawable.ic_launcher, "二维码"));
			mGrid.addQuickAction(new QuickAction(MainActivity.this,
					R.drawable.ic_launcher, "二维码"));
			mGrid.addQuickAction(new QuickAction(MainActivity.this,
					R.drawable.ic_launcher, "二维码"));
			mGrid.addQuickAction(new QuickAction(MainActivity.this,
					R.drawable.ic_launcher, "二维码"));
			mGrid.addQuickAction(new QuickAction(MainActivity.this,
					R.drawable.ic_launcher, "二维码"));
			mGrid.show(getTitleBar());
			mGrid.setOnQuickActionClickListener(quickActionClickListener);
		}
	};
    private OnQuickActionClickListener quickActionClickListener=new OnQuickActionClickListener() {
		
		@Override
		public void onQuickActionClicked(ToolsActionWidget widget, int position) {
			if(position==0){
				Intent intent=new Intent(AppAction.ZXingAction.ACTION);
				startActivity(intent);
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initSlidingMenu();
	}

	private void initSlidingMenu() {
		int mScreenWidth = DisplayInfo.getInstance()
				.getWindowVisibleDisplayWidth();// 获取屏幕分辨率宽度
		setBehindContentView(R.layout.main_left_layout);// 设置左菜单
		FragmentTransaction mFragementTransaction = getSupportFragmentManager()
				.beginTransaction();
		Fragment mFrag = new ContactsFragment();
		mFragementTransaction.replace(R.id.main_left_fragment, mFrag);
		mFragementTransaction.commit();
		// 初始化SlidingMenu，
		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.LEFT);// 设置是左滑
		mSlidingMenu.setShadowWidth(mScreenWidth / 40);// 设置阴影宽度
		mSlidingMenu.setBehindOffset(mScreenWidth / 8);// 设置菜单宽度
		mSlidingMenu.setFadeDegree(0.35f);// 设置淡入淡出的比例
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		mSlidingMenu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
		mSlidingMenu.setBehindScrollScale(0.333f);// 设置滑动时拖拽效果
	}

	private void initView() {
		// 设置二级菜单
		mSlidingMenu.setSecondaryMenu(R.layout.main_right_layout);
		FragmentTransaction mRightTransaction = getSupportFragmentManager()
				.beginTransaction();
		mProfileFragment = new ProfileFragment();
		mRightTransaction.replace(R.id.main_right_fragment, mProfileFragment);
		mRightTransaction.commit();

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public boolean initializeTitlBar() {
		setMiddleTitle("主界面");
		setLeftButton(R.drawable.icon_slider_contact_selector,
				mLeftClickListener);
		setRightButton(R.drawable.icon_slider_profile_selector,
				mRightClickListener);
		return true;
	}

	@Override
	protected TabInfo[] getTabInfos() {
		TabInfo[] tabInfo = new TabInfo[2];
		tabInfo[0] = new TabInfo("ContactsFragment", R.string.action_settings,
				R.drawable.icon_slider_contact_selector,
				ContactsFragment.class, null);
		tabInfo[1] = new TabInfo("ProfileFragment", R.string.app_name,
				R.drawable.icon_slider_profile_selector, ProfileFragment.class,
				null);
		return tabInfo;
	}

}
