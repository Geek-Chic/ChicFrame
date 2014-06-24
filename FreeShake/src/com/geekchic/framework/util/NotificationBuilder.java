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

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.geekchic.wuyou.R;
import com.geekchic.wuyou.ui.main.MainActivity;

/**
 * @ClassName: NotificationBuilder
 * @Descritpion: 通知管理类
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

	/**
	 * 默认接收通知
	 */
	private boolean isNotificationEnalbe = true;
	/**
	 * 是否要建新的Task栈
	 */
	private boolean isNeedStackBuilder = false;

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
    /**
     * 测试验证码通知
     * @param captcha
     */
	public void showCaptchaNotify(String captcha) {
		String notifyFormat = getResource(R.string.notify_captcha_content);
		String notifyContent = String.format(notifyFormat, captcha);
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.ic_launcher);
		PendingIntent intent=PendingIntent.getActivity(mContext, 0, new Intent(), 0);
		NotificationCompat.Builder builder=getCommonNotification(intent, getResource(R.string.notify_register),
				getResource(R.string.notify_captcha_title), notifyContent,
				bitmap, 0);
		int notifyId=mNotifyIdCache.size();
		mNotificationManager.cancel(notifyId);
		// 显示通知 break;
		mNotificationManager.notify(notifyId, builder.build());
	}

	/**
	 * 显示通知栏<BR>
	 * 
	 * @param notifyId
	 * @param intent
	 * @param tickerText
	 * @param title
	 * @param text
	 * @param icon
	 * @param when
	 */
	private void showNotification(int notifyId, Intent intent,
			String tickerText, String title, String text, Bitmap icon, long when) {

		NotificationCompat.Builder builder = getNotification(intent,
				tickerText, title, text, icon, when);
		mNotificationManager.cancel(notifyId);
		// 显示通知 break;
		mNotificationManager.notify(notifyId, builder.build());
	}

	/**
	 * 获取Notifycation Builder<BR>
	 * 
	 * @param intent
	 * @param tickerText
	 * @param title
	 * @param text
	 * @param icon
	 * @param when
	 * @return
	 */
	private NotificationCompat.Builder getNotification(Intent intent,
			String tickerText, String title, String text, Bitmap icon, long when) {

		PendingIntent pendingIntent;
		if (isNeedStackBuilder) {
			// 新建一个Tast堆栈,使订单详情返回至MainTabActivity
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
			stackBuilder.addParentStack(MainActivity.class);
			stackBuilder.addNextIntent(intent);
			pendingIntent = stackBuilder.getPendingIntent(0,
					PendingIntent.FLAG_UPDATE_CURRENT);
		} else {
			pendingIntent = PendingIntent.getActivity(mContext, 0, intent,
					PendingIntent.FLAG_UPDATE_CURRENT);
		}
		NotificationCompat.Builder builder = getCommonNotification(pendingIntent,
				tickerText, title, text, icon, when);
		return builder;
	}

	/**
	 * 构造通知
	 * 
	 * @param intent
	 * @param tickerText
	 * @param title
	 * @param text
	 * @param icon
	 * @param when
	 * @return
	 */
	private NotificationCompat.Builder getCommonNotification(PendingIntent intent,
			String tickerText, String title, String text, Bitmap icon, long when) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				mContext).setTicker(tickerText)
				.setSmallIcon(R.drawable.ic_launcher).setContentTitle(title)
				.setContentText(text).setLargeIcon(icon)
				.setWhen(System.currentTimeMillis()).setAutoCancel(true)
				.setDefaults(Notification.DEFAULT_LIGHTS)
				.setStyle(new NotificationCompat.BigTextStyle().bigText(text))
				.setContentIntent(intent);
		
		// 设置声音
		if (isNotificationSound()) {
			builder.setSound(Uri.parse("android.resource://"
					+ mContext.getPackageName() + "/"+R.raw.notice));
//			  builder.setDefaults(Notification.DEFAULT_SOUND);
		}
		// 设置震动
		if (isNotificationVibrate()) {
			builder.setDefaults(Notification.DEFAULT_VIBRATE);
		}
		return builder;
	}

	/**
	 * 清除所有订单<BR>
	 */
	public void clearAllNotify() {
		for (int i = 0; i < mNotifyIdCache.size(); i++) {
			mNotificationManager.cancel(i);
		}
		mNotifyIdCache.clear();
	}

	/**
	 * 清除通知Notification<BR>
	 * 
	 * @param notifyId
	 *            通知id
	 */
	public void clearNotify(String notifyId) {
		int id = removeCacheByID(notifyId);
		if (id != -1) {
			mNotificationManager.cancel(id);
		}
	}

	/**
	 * 是否需要新创建Task栈<BR>
	 * 
	 * @return 是否需要新创建Task栈
	 */
	public synchronized boolean isNeedTaskStrack() {
		return isNeedStackBuilder;
	}

	/**
	 * 是否开启震动<BR>
	 * 
	 * @return 是否开启震动
	 */
	private boolean isNotificationVibrate() {
		return true;
	}

	/**
	 * 是否开启声音<BR>
	 * 
	 * @return 是否开启声音
	 */
	private boolean isNotificationSound() {
		return true;
	}

	/**
	 * 根据ID添加Cache<BR>
	 * 
	 * @param notifyID
	 * @return 通知编号
	 */
	private int cacheNotifyID(String notifyID) {
		if (!mNotifyIdCache.contains(notifyID)) {
			mNotifyIdCache.add(notifyID);
		}
		return mNotifyIdCache.indexOf(notifyID);
	}

	/**
	 * 根据ID删除Cache<BR>
	 * 
	 * @param notifyID
	 * @return
	 */
	private int removeCacheByID(String notifyID) {
		int index = mNotifyIdCache.indexOf(notifyID);
		if (index != -1) {
			mNotifyIdCache.remove(index);
		}
		return index;
	}

	/**
	 * 当前是否允许通知
	 * 
	 * @return
	 */
	public boolean isNotificationEnalbe() {
		return isNotificationEnalbe;
	}

	/**
	 * 设置当前是否允许通知
	 * 
	 * @param isNotificationEnalbe
	 */
	public void setNotificationEnalbe(boolean isNotificationEnalbe) {
		this.isNotificationEnalbe = isNotificationEnalbe;
	}

	/**
	 * 获取字符串资源
	 * 
	 * @param id
	 * @return
	 */
	private String getResource(int id) {
		return mContext.getResources().getString(id);
	}

	/**
	 * @ClassName: Notifier
	 * @Descritpion:如果在自定义Service里实现通知，以广播形式发送至主线程实现，否则会存在两个通知实例。
	 * @author evil
	 * @date May 12, 2014
	 */
	public static class Notifier extends BroadcastReceiver {

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
	private class Worker extends HandlerThread {
		public Worker() {
			super("Notification Worker", NORM_PRIORITY);

		}

		public void init(Handler handler) {
			this.mHandler = handler;
		}

		public void notifyNewMessage(Message message) {
			if (mHandler != null) {
				mHandler.sendMessageDelayed(message, 100);
			}
		}

		private Handler mHandler;

	}
}
