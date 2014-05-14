/**
 * @Title: BaseActivity.java
 * @Package com.geekchic.framework.ui
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 29, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.ui;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.geekchic.BaseApplication;
import com.geekchic.common.log.Logger;
import com.geekchic.common.utils.DisplayInfo;
import com.geekchic.common.utils.PreferencesUtil;
import com.geekchic.constant.AppConfig;
import com.geekchic.constant.AppConstants.Common;
import com.geekchic.constant.AppManager;
import com.geekchic.framework.logic.BaseLogicBuilder;
import com.geekchic.framework.logic.ILogic;
import com.geekchic.framework.logic.LogicBuilder;

/**
 * @ClassName: BaseActivity
 * @Descritpion: Activity 基类
 * @author evil
 * @date Apr 29, 2014
 */
public class BaseActivity extends FragmentActivity {
	/**
	 * TAG
	 */
	private static final String TAG = "BaseActivity";
	/**
	 * Logic管理类
	 */
	private static BaseLogicBuilder mBaseLogicBuilder;
	/**
	 * 缓存持有的logic对象的集合
	 */
	private final Set<ILogic> mLogicSet = new HashSet<ILogic>();
	/**
	 * 是否独自控制logic监听
	 */
	private boolean isPrivateHandler = false;
	/**
	 * 当前activity消息分发handle
	 */
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			BaseActivity.this.handleStateMessage(msg);
		}

	};
	/**
	 * 当前Activity是否Pause
	 */
	private boolean mPaused;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		init();
		initData();
	}

	private void init() {
		if (mBaseLogicBuilder == null) {
			mBaseLogicBuilder = LogicBuilder.getInstance(this);
		}
		if (!isHandlerManagerSelf()) {
			mBaseLogicBuilder.registerHandleToAllLogics(getHandler());
		}
		initLogics();
		// 设置在当前应用界面，调用音量键的时候控制的是多媒体音量
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

	}

	/**
	 * 读取常用设置
	 */
	private void initData() {
		AppConfig.getInstance().setSessionId(
				PreferencesUtil.getAttrString(Common.KEY_SESSION_ID));
		AppConfig.getInstance().setUid(
				PreferencesUtil.getAttrString(Common.KEY_USER_ID));
	}

	/**
	 * 判断是否已经登录
	 * 
	 * @return
	 */
	public boolean hasLogined() {
		return !TextUtils.isEmpty(AppConfig.getInstance().getSessionId());
	}

	/**
	 * 当前Activity是否处于暂停状态<BR>
	 * 
	 * @return 是否处于暂停状态
	 */
	protected boolean isActivityPause() {
		return mPaused;
	}

	/**
	 * 返回一个boolean表示展示该页面是否需要登录成功
	 * 
	 * @return boolean 是否是登录后的页面
	 */
	protected boolean needLogin() {
		return true;
	}

	/**
	 * 获取shared preferences<BR>
	 * 
	 * @return SharedPreferences
	 */
	public SharedPreferences getSharedPreferences() {
		return getSharedPreferences(Common.SHARED_PREFERENCE_NAME,
				Context.MODE_PRIVATE);
	}

	/**
	 * 设置已经成功登录<BR>
	 * 
	 * @param sessionid
	 *            登陆后纪录sessionid来判断是否登陆
	 */
	protected void setLogined(String sessionid) {
		PreferencesUtil.setAttr(Common.KEY_SESSION_ID, sessionid);
	}

	@Override
	protected void onDestroy() {
		removeHandler();
		AppManager.getAppManager().finishActivity(this);
		Logger.d(TAG, "BaseActivity:" + this + "onDestory");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		Logger.d(TAG, "BaseActivity:" + this + "onPause");
		mPaused = true;
		super.onPause();
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		if (null != imm && imm.isActive()) {
			if (null != this.getCurrentFocus()
					&& null != this.getCurrentFocus().getWindowToken()) {
				imm.hideSoftInputFromWindow(this.getCurrentFocus()
						.getApplicationWindowToken(), 0);
			}
		}
		  // 因为window的尺寸在显示之后才能确定，onCreate 与 onResume 时都无法取得，
        // 所以选择在启动页的oPause时初始化，便于其他页面使用
        DisplayInfo.init(this);
	}

	@Override
	protected void onResume() {
		mPaused = false;
		super.onResume();
		Logger.d(TAG, "BaseActivity:" + this + "onResume");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void finish() {
		removeHandler();
		Logger.d(TAG, "BaseActivity:" + this + "finish");
		super.finish();
	}

	/**
	 * 当前Activity出栈
	 */
	protected void finishActivity() {
		AppManager.getAppManager().finishActivity(this);
	}

	/**
	 * 清空Task栈
	 */
	protected void finishAllAcitivity() {
		AppManager.getAppManager().finishAllActivity();
	}

	/**
	 * 根据类名获取Logic
	 * 
	 * @param interfaceClass
	 * @return
	 */
	protected final ILogic getLogic(Class<?> interfaceClass) {
		ILogic logic = mBaseLogicBuilder.getLogic(interfaceClass);
		// 如果自己管理注册Handler则加缓存
		if (isHandlerManagerSelf() && null != logic
				&& !mLogicSet.contains(logic)) {
			logic.addHandler(getHandler());
			mLogicSet.add(logic);
		}
		if (null == logic) {
			Logger.e(TAG, "Not found Logic：" + interfaceClass);
		}
		return logic;
	}

	private void removeHandler() {
		if (mHandler != null) {

			if (mLogicSet.size() > 0 && isHandlerManagerSelf()) {
				for (ILogic logic : mLogicSet) {
					logic.removeHandler(getHandler());
				}
			} else {
				mBaseLogicBuilder.removeAllHandlerRegister(getHandler());
			}
			this.mHandler = null;
		}

	}

	/**
	 * 获取Handler
	 * 
	 * @return
	 */
	protected final Handler getHandler() {
		return mHandler;
	}

	/**
	 * 是否自己管理Handler
	 * 
	 * @return
	 */
	protected boolean isHandlerManagerSelf() {
		return isPrivateHandler;
	}

	/**
	 * 发送消息
	 * 
	 * @param what
	 *            消息标识
	 */
	protected final void sendEmptyMessage(int what) {
		if (mHandler != null) {
			mHandler.sendEmptyMessage(what);
		}
	}

	/**
	 * 延迟发送空消息
	 * 
	 * @param what
	 *            消息标识
	 * @param delayMillis
	 *            延迟时间
	 */
	protected final void sendEmptyMessageDelayed(int what, long delayMillis) {
		if (mHandler != null) {
			mHandler.sendEmptyMessageDelayed(what, delayMillis);
		}
	}

	/**
	 * 
	 * post一段操作到UI线程
	 * 
	 * @param runnable
	 *            Runnable
	 */
	protected final void postRunnable(Runnable runnable) {
		if (mHandler != null) {
			mHandler.post(runnable);
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param msg
	 *            消息对象
	 */
	protected final void sendMessage(Message msg) {
		if (mHandler != null) {
			mHandler.sendMessage(msg);
		}
	}

	/**
	 * 延迟发送消息
	 * 
	 * @param msg
	 *            消息对象
	 * @param delayMillis
	 *            延迟时间
	 */
	protected final void sendMessageDelayed(Message msg, long delayMillis) {
		if (mHandler != null) {
			mHandler.sendMessageDelayed(msg, delayMillis);
		}
	}

	/**
	 * 是否绑定推送
	 * 
	 * @param enableLasPush
	 *            是否打开地理位置的推送
	 */
	protected void bindPush(boolean enableLasPush) {
		boolean pushFlag = getSharedPreferences().getBoolean(
				Common.PUSH_BIND_FLAG, false);
		if (!pushFlag) {
			PushManager.startWork(getApplicationContext(),
					PushConstants.LOGIN_TYPE_API_KEY,
					PreferencesUtil.getMetaValue(this, "api_key"));
		}
	}
	/**
	 * 开始定位
	 * @return
	 */
	protected void  requestLocation(BDLocationListener mBdLocationListener){
		LocationClient mClient=((BaseApplication)getApplication()).mLocationClient;
		mClient.registerLocationListener(mBdLocationListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setPoiExtraInfo(true);	
		option.setPriority(LocationClientOption.NetWorkFirst); 
		option.setAddrType("all");
		option.setPoiNumber(5);
		option.disableCache(true);
		mClient.setLocOption(option);
		mClient.start();
		mClient.requestLocation();
	}

	/**
	 * Activity消息接收
	 * 
	 * @param msg
	 */
	protected void handleStateMessage(Message msg) {

	}

	/**
	 * 初始化Logic
	 */
	protected void initLogics() {

	}
}
