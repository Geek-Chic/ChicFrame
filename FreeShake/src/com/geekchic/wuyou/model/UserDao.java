/**
 * @Title: UserDao.java
 * @Package com.geekchic.wuyou.model
 * @Description: User表DML
 * @author: evil
 * @date: May 5, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.model;

import android.content.Context;

import com.geekchic.base.db.BaseDaoImpl;
import com.geekchic.base.http.CommHttpInfoDBImpl;
import com.geekchic.wuyou.bean.UserInfo;

/**
 * @ClassName: UserDao
 * @Descritpion: User表DML
 * @author evil
 * @date May 5, 2014
 */
public class UserDao extends BaseDaoImpl<UserInfo>{
	   private static final String TAG=CommHttpInfoDBImpl.class.getName();
	    private static UserDao sInstance;
	    /**
	     * 单例
	     * @param context
	     * @return
	     */
	    public static UserDao getINSTANCE(Context context){
	        if(sInstance==null){
	        	sInstance=new UserDao(context);
	        }
	        return sInstance;
	    }
	private  UserDao(Context context) {
		super(new WuyouDbHelper(context), UserInfo.class);
	}

}
