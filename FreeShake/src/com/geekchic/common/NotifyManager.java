/**
 * @Title: NotifyManager.java
 * @Package com.geekchic.common
 * @Description: [用一句话描述做什么]
 * @author: Administrator
 * @date: 2014-4-14
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.common;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;

/**
 * @ClassName: NotifyManager
 * @Descritpion: 通用Notify管理类 
 * @author Administrator
 * @date 2014-4-14
 */
public class NotifyManager
{
    /**
     * 单例
     */
    private static NotifyManager mInstance;
    private class Worker extends HandlerThread implements Callback{
     private Handler mHandler;
        public Worker()
        {
            super("Notification Worker",NORM_PRIORITY);
            mHandler=new Handler(getLooper(),this);
        }

        @Override
        public boolean handleMessage(Message msg)
        {
            // TODO Auto-generated method stub
            return false;
        }
        
    }
}
