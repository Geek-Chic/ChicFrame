/**
 * @Title: TaskDao.java
 * @Package com.geekchic.wuyou.model
 * @Description: Task DML操作
 * @author: evil
 * @date: May 19, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.model;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.geekchic.base.db.BaseDaoImpl;
import com.geekchic.wuyou.bean.Task;

/**
 * @ClassName: TaskDao
 * @Descritpion: Task DML操作
 * @author evil
 * @date May 19, 2014
 */
public class TaskDao extends BaseDaoImpl<Task>{
    private static TaskDao sInstance;
    /**
     * TaskDao构造函数
     * @param context
     */
	private TaskDao(Context context) {
		super(new WuyouDbHelper(context));
	}
	/**
	 * 单例
	 * @param context
	 * @return
	 */
    public static TaskDao getInstance(Context context){
    	if(null==sInstance){
    		synchronized (TaskDao.class) {
				if(null==sInstance){
					sInstance=new TaskDao(context);
				}
			}
    	}
		return sInstance;
    }
}
