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
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.geekchic.common.utils.DisplayInfo;
import com.geekchic.constant.AppAction;
import com.geekchic.constant.AppConstants.QUICKACTION;
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
	private GoingFragment mProfileFragment;
	/**
	 * 联系人Framgment
	 */
	private ContactsFragment mContactsFragment;
	/**
	 * ACTION BAR
	 */
	private ToolsActionDialog mToolsActionDialog;
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
			initActionBar();
			mToolsActionDialog.show(getTitleBar());
		}
	};
    private OnQuickActionClickListener quickActionClickListener=new OnQuickActionClickListener() {
		
		@Override
		public void onQuickActionClicked(ToolsActionWidget widget, int position) {
			switch (position) {
			case QUICKACTION.ACTION_CREATEPROJECT:
				Intent projectIntent=new Intent(AppAction.ProjectCreateAction.ACTION);
				startActivity(projectIntent);

				break;
			case QUICKACTION.ACTION_QR:
				Intent qrIntent=new Intent(AppAction.ZXingAction.ACTION);
				startActivity(qrIntent);
				break;
			default:
				break;
			}
		}
	};
	/**
	 * 定位监听
	 */
	private BDLocationListener mBdLocationListener=new BDLocationListener() {
		
		@Override
		public void onReceivePoi(BDLocation paramBDLocation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onReceiveLocation(BDLocation paramBDLocation) {
			//如果Fragment被添加至Activity中则设置地址
			if(null!=paramBDLocation&&mContactsFragment.isAdded()){
				mContactsFragment.setLoc(paramBDLocation.getCity());
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initSlidingMenu();
		
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		try {
			super.onRestoreInstanceState(savedInstanceState);
		} catch (Exception e) {
			savedInstanceState=null;
		}
	}

	private void initSlidingMenu() {
		int mScreenWidth = DisplayInfo.getScreenWidth(this);// 获取屏幕分辨率宽度
		setBehindContentView(R.layout.main_left_layout);// 设置左菜单
		FragmentTransaction mFragementTransaction = getSupportFragmentManager()
				.beginTransaction();
		mContactsFragment  = ContactsFragment.newInstance();
		mFragementTransaction.replace(R.id.main_left_frame_container, mContactsFragment);
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
    private void initActionBar(){
    	if(null==mToolsActionDialog){
    		mToolsActionDialog = new ToolsActionDialog(MainActivity.this);
    		mToolsActionDialog.addQuickAction(new QuickAction(MainActivity.this,
					R.drawable.icon_project_add, "创建目标"));
    		mToolsActionDialog.addQuickAction(new QuickAction(MainActivity.this,
					R.drawable.icon_qr, "二维码"));
    		mToolsActionDialog.setOnQuickActionClickListener(quickActionClickListener);
    	}
    }
//	private void initView() {
//		// 设置二级菜单
//		mSlidingMenu.setSecondaryMenu(R.layout.main_right_layout);
//		FragmentTransaction mRightTransaction = getSupportFragmentManager()
//				.beginTransaction();
//		mProfileFragment = new ProfileFragment();
//		mRightTransaction.replace(R.id.main_right_fragment, mProfileFragment);
//		mRightTransaction.commit();
//
//	}
	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		requestLocation(mBdLocationListener);
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
		setTitleBarBackground(R.color.blue);
		return true;
	}

	@Override
	protected TabInfo[] getTabInfos() {
		TabInfo[] tabInfo = new TabInfo[4];
		tabInfo[0] = new TabInfo("MessageFragment", R.string.main_tab_message,
				R.drawable.icon_tabbar_history_message_selector,
				MessageFragment.class, null,false);
		tabInfo[1] = new TabInfo("GoingFragment", R.string.app_name,
				R.drawable.icon_tabbar_going_selector, GoingFragment.class,
				null,false);
		tabInfo[2] = new TabInfo("CompleteFragment", R.string.main_tab_complete,
				R.drawable.icon_tabbar_complete_selector, CompleteFragment.class,
				null,false);
		tabInfo[3] = new TabInfo("StatisticFragment", R.string.main_tab_statictic,
				R.drawable.icon_tabbar_statistic_selector, StatisticFragment.class,
				null,false);
		return tabInfo;
	}

}
