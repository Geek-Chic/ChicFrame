/**
 * @Title: MessageItemDao.java
 * @Package com.geekchic.wuyou.model
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 19, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.model;

import android.content.Context;

import com.geekchic.base.db.BaseDaoImpl;
import com.geekchic.wuyou.bean.MessageItem;

/**
 * @ClassName: MessageItemDao
 * @Descritpion: MessageItem数据库DML
 * @author evil
 * @date May 19, 2014
 */
public class MessageItemDao extends BaseDaoImpl<MessageItem> {
	private   static MessageItemDao sInstance;
     /**
      * MessageItemDao构造函数
      * @param context
      */
	public MessageItemDao(Context context) {
		super(new WuyouDbHelper(context));
	}
	public static MessageItemDao getInstance(Context context){
		if(sInstance==null)
			synchronized (MessageItemDao.class) {
				if(sInstance==null){
					sInstance=new MessageItemDao(context);
				}
			}
		return sInstance;
	}

}
