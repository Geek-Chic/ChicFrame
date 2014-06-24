/**
 * @Title: ContactDao.java
 * @Package com.geekchic.wuyou.model
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 20, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.model;

import android.content.Context;

import com.geekchic.base.db.BaseDaoImpl;
import com.geekchic.wuyou.bean.Contact;

/**
 * @ClassName: ContactDao
 * @Descritpion: 联系人DML
 * @author evil
 * @date May 20, 2014
 */
public class ContactDao extends BaseDaoImpl<Contact> {

	private static ContactDao sInstance=null;
	public ContactDao(Context context) {
		super(new WuyouDbHelper(context));
	}
	/**
	 * 单例
	 * @param context
	 * @return
	 */
	public static ContactDao getInstance(Context context){
		if(sInstance==null){
			synchronized (ContactDao.class) {
				if(sInstance==null){
					sInstance=new ContactDao(context);
				}
			}
		}
		return sInstance;
	}

}
