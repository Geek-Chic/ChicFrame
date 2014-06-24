/**
 * @Title: PushReceiver.java
 * @Package com.geekchic.wuyou.service
 * @Description: 推送接收处理
 * @author: evil
 * @date: May 3, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic;

import java.util.ArrayList;

import android.content.Context;
import android.os.Message;

import com.geekchic.common.utils.JsonUtils;
import com.geekchic.framework.service.BasePushMessageReceiver;
import com.geekchic.framework.util.NotificationBuilder;
import com.geekchic.wuyou.bean.Notify;

/**
 * @ClassName: PushReceiver
 * @Descritpion: 推送接收处理
 * @author evil
 * @date May 3, 2014
 */
public class PushReceiver extends BasePushMessageReceiver {
	/**
	 * 注册码事件
	 */
	public static final int CAPTCHA = 0;
	private static ArrayList<PushHandle> ehList = new ArrayList<PushHandle>();
	@Override
	public void onMessage(Context context, String message,
			String customContentString) {
		super.onMessage(context, message, customContentString);
		Notify notify = (Notify) JsonUtils.parserJsonToBean(message,
				Notify.class);
		handlePushIntent(context, notify);
	}

	/**
	 * 处理操作
	 * 
	 * @param context
	 * @param notify
	 */
	private void handlePushIntent(Context context, Notify notify) {
		switch (notify.id) {
		case CAPTCHA:
			NotificationBuilder.getInstance(context).showCaptchaNotify(
					notify.content);
			break;
		default:
			break;
		}
	}

	/**
	 * 注册Push监听器
	 * 
	 * @param pushHandle
	 */
	public static void AddPushHandle(PushHandle pushHandle) {
		ehList.add(pushHandle);
	}

	/**
	 * 移除Push监听器
	 * 
	 * @param pushHandle
	 */
	public static void removePushHandle(PushHandle pushHandle) {
		if (ehList.contains(pushHandle)) {
			ehList.remove(pushHandle);
		}
	}
    
    /**
     * @ClassName: PushHandle
     * @Descritpion: 推送事件监听
     * @author evil
     * @date May 16, 2014
     */
	public static abstract interface PushHandle {
		/**
		 * 消息回调
		 * @param message
		 */
		public abstract void onMessage(Message message);
        
//		public abstract void onBind(String method, int errorCode, String content);
      
		public abstract void onNetChange(boolean isNetConnected);

	}
}
