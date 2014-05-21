/**
 * @Title: WuyouDbHelper.java
 * @Package com.geekchic.wuyou.model
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 19, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.model;

import android.content.Context;

import com.geekchic.base.db.BaseDBHelper;
import com.geekchic.wuyou.bean.Contact;
import com.geekchic.wuyou.bean.MessageItem;
import com.geekchic.wuyou.bean.Project;
import com.geekchic.wuyou.bean.UserInfo;

/**
 * @ClassName: WuyouDbHelper
 * @Descritpion: [用一句话描述作用] 
 * @author evil
 * @date May 19, 2014
 */
public class WuyouDbHelper extends BaseDBHelper{
	private static final String DBNAME="wuyou.db";
	   private static final int DBVERSION=1;
	   private static final Class<?>[] clazz={Project.class,MessageItem.class,UserInfo.class,Contact.class};
	public WuyouDbHelper(Context context) {
		super(context, DBNAME, null, DBVERSION, clazz);
	}

}
