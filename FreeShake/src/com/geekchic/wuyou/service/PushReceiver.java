/**
 * @Title: PushReceiver.java
 * @Package com.geekchic.wuyou.service
 * @Description: 推送接收处理
 * @author: evil
 * @date: May 3, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.service;

import android.content.Context;

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
	public static final int CAPTCHA=0;
    @Override
    public void onMessage(Context context, String message,
    		String customContentString) {
    	super.onMessage(context, message, customContentString);
    	Notify notify=(Notify) JsonUtils.parserJsonToBean(message, Notify.class);
    	handlePushIntent(context, notify);
    }
    /**
     * 处理操作
     * @param context
     * @param notify
     */
   private void handlePushIntent(Context context,Notify notify){
	   switch (notify.id) {
	case CAPTCHA:
		NotificationBuilder.getInstance(context).showCaptchaNotify(notify.content);
		break;
	default:
		break;
	}
   }
}
