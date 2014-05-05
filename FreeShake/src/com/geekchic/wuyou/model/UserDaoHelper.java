/**
 * @Title: UserDaoHelper.java
 * @Package com.geekchic.wuyou.model
 * @Description: 数据库DDL帮助类
 * @author: evil
 * @date: May 5, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.geekchic.base.db.BaseDBHelper;
import com.geekchic.wuyou.bean.UserInfo;

/**
 * @ClassName: UserDaoHelper
 * @Descritpion:数据库DDL帮助类
 * @author evil
 * @date May 5, 2014
 */
public class UserDaoHelper extends BaseDBHelper {
	private static final String DBNAME = "wuyou.db";
	private static final int DBVERSION = 1;
	private static final Class<?>[] clazz = { UserInfo.class };

	public UserDaoHelper(Context context) {
		super(context, DBNAME, null, DBVERSION, clazz);
		// TODO Auto-generated constructor stub
	}

}
