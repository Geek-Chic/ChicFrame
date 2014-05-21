/**
 * @Title: ProjectDao.java
 * @Package com.geekchic.wuyou.model
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 17, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.model;

import android.content.Context;

import com.geekchic.base.db.BaseDaoImpl;
import com.geekchic.wuyou.bean.Project;

/**
 * @ClassName: ProjectDao
 * @Descritpion: 单例Project操作类
 * @author evil
 * @date May 17, 2014
 */
public class ProjectDao extends BaseDaoImpl<Project> implements ProjectDaoOps {
	/**
	 * 单例
	 */
	private static ProjectDao sInstance;

	/**
	 * ProjectDao构造函数
	 * 
	 * @param dbHelper
	 */
	private ProjectDao(Context context) {
		super(new WuyouDbHelper(context));
	}
   /**
    * 单例
    * @param context
    * @return
    */
	public static ProjectDao getInstance(Context context) {
		if (sInstance == null) {
			synchronized (ProjectDao.class) {
				if (sInstance == null) {
					sInstance = new ProjectDao(context);
				}
			}
		}
		return sInstance;
	}

}
