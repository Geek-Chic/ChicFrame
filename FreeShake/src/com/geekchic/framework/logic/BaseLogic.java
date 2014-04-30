/**
 * @Title: BaseLogic.java
 * @Package com.geekchic.framework.logic
 * @Description: Logic基类
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.logic;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * @ClassName: BaseLogic
 * @Descritpion: Logic基类
 * @author evil
 * @date Apr 30, 2014
 */
public class BaseLogic implements ILogic {
	private final List<Handler> mHandlersList = new Vector<Handler>();
	private Context mContext;
	private Handler mHandler;

	@Override
	public void init(Context context) {
		this.mContext = context;
		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				synchronized (mHandlersList) {
					for (Handler handler : mHandlersList) {
						if (msg.obj == null) {
							handler.sendEmptyMessage(msg.what);
						} else {
							Message message = new Message();
							message.what = msg.what;
							message.obj = msg.obj;
							handler.sendMessage(message);
						}
					}
				}
			}

		};
	}

	@Override
	public void addHandler(Handler handler) {
		// TODO Auto-generated method stub
		mHandlersList.add(handler);
	}

	@Override
	public void removeHandler(Handler handler) {
		mHandlersList.remove(handler);
	}

	/**
	 * 发送消息
	 * 
	 * @param what
	 * @param obj
	 */
	public void sendMessage(int what, Object obj) {
		synchronized (mHandlersList) {
			for (Handler handler : mHandlersList) {
				if (null == obj) {
					handler.sendEmptyMessage(what);
				} else {
					Message message = handler.obtainMessage(what, obj);
					handler.sendMessage(message);
				}
			}
		}
	}

	/**
	 * 发送空消息
	 * 
	 * @param what
	 */
	public void sendEmptyMessage(int what) {
		synchronized (mHandlersList) {
			for (Handler handler : mHandlersList) {
				handler.sendEmptyMessage(what);
			}
		}
	}

}
