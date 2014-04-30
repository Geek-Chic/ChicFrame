/**
 * @Title: NotificationBuilder.java
 * @Package com.geekchic.framework.ui.util
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 27, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.util;

import java.util.ArrayList;
import java.util.List;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

/**
 * @ClassName: NotificationBuilder
 * @Descritpion: [用一句话描述作用]
 * @author evil
 * @date Apr 27, 2014
 */
public class NotificationBuilder {
	/**
	 * TAG
	 */
	private static final String TAG = "NotificationBuilder";
	/**
	 * Singleton
	 */
	private static NotificationBuilder sInstance;
	/**
	 * Context
	 */
	private Context mContext;
	/**
	 * 通知管理器
	 */
	private NotificationManager mNotificationManager;
	/**
	 * 缓存通知ID
	 */
	private List<String> mNotifyIdCache = new ArrayList<String>();

	private NotificationBuilder(Context context) {
		mContext = context.getApplicationContext();
		mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

	}

	/**
	 * 获取NotificationBuilder实例
	 * 
	 * @param context
	 * @return
	 */
	public static synchronized NotificationBuilder getInstance(Context context) {
		if (null == sInstance) {
			sInstance = new NotificationBuilder(context);
		}
		return sInstance;
	}
	public static class Notifier extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
		}
		
	}
	/**
	 * @ClassName: Worker
	 * @Descritpion: 工作线程
	 * @author evil
	 * @date Apr 27, 2014
	 */
	private class Worker extends HandlerThread{
        public Worker() {
			super("Notification Worker",NORM_PRIORITY);
			
		}
        public void init(Handler handler){
        	this.mHandler=handler;
        }
        public void notifyNewMessage(Message message){
        	if(mHandler!=null){
        		mHandler.sendMessageDelayed(message, 100);
        	}
        }
		private Handler mHandler;
		
	}
}
