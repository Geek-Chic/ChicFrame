/**
 * @Title: BaseSmsManager.java
 * @Package com.geekchic.framework.service
 * @Description: 短信广播监听
 * @author: evil
 * @date: Jun 1, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * @ClassName: BaseSmsManager
 * @Descritpion: 短信广播监听
 * @author evil
 * @date Jun 1, 2014
 */
public class BaseSmsManager extends BroadcastReceiver{
	/**
	 * 短信ACTION
	 */
	private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
	@Override
	public void onReceive(Context context, Intent intent) {

        if(intent!=null && intent.getAction()!=null &&
ACTION.compareToIgnoreCase(intent.getAction())==0)
        {
            Object[]pduArray= (Object[]) intent.getExtras().get("pdus");
            SmsMessage[] messages = new SmsMessage[pduArray.length];
            for (int i = 0; i<pduArray.length; i++) {
                    messages[i] = SmsMessage.createFromPdu ((byte[])pduArray [i]);
            }
            StringBuilder sb = new StringBuilder();
            for (SmsMessage cur:messages)
            {
             sb.append("from：");
             sb.append(cur.getDisplayOriginatingAddress());
             sb.append(" body：");
             sb.append(cur.getDisplayMessageBody());
            }
            Log.d("MySMSMonitor","SMS Message Received."+sb.toString());
        }
	}

}
