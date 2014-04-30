/**
 * @Title: PushMessageRerceiver.java
 * @Package com.geekchic.framework.service
 * @Description: 推送IM
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.service;

import com.baidu.android.pushservice.PushConstants;
import com.geekchic.common.log.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @ClassName: PushMessageRerceiver
 * @Descritpion: 推送IM
 * @author evil
 * @date Apr 30, 2014
 */
public class PushMessageRerceiver extends BroadcastReceiver {
	/**
	 * TAG
	 */
	private static final String TAG="PushMessageReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
			if(intent.getExtras()==null){
				return;
			}
			String message=intent.getExtras().getString(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
			Logger.i(TAG, "onMessage:"+message);
		}
	}

}
