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

import com.geekchic.framework.service.BasePushMessageReceiver;

/**
 * @ClassName: PushReceiver
 * @Descritpion: 推送接收处理
 * @author evil
 * @date May 3, 2014
 */
public class PushReceiver extends BasePushMessageReceiver {

    @Override
    public void onMessage(Context context, String message,
    		String customContentString) {
    	// TODO Auto-generated method stub
    	super.onMessage(context, message, customContentString);
    }   
}
