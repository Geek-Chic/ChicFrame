/**
 * @Title: RequestManager.java
 * @Package com.geekchic.wuyou.logic
 * @Description:  Request管理类
 * @author: evil
 * @date: May 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.logic;

import android.content.Context;

import com.geekchic.RequestService;
import com.geekchic.framework.service.BaseRequestManager;

/**
 * @ClassName: RequestManager
 * @Descritpion: Request管理类
 * @author evil
 * @date May 2, 2014
 */
public class RequestManager extends BaseRequestManager {
	/**
	 * 单例
	 */
	private static RequestManager sInstance;
	public static RequestManager getInstance(Context context){
		if(null==sInstance){
			sInstance=new RequestManager(context);
		}
		return sInstance;
	}
	private RequestManager(Context context) {
		super(context,RequestService.class);
	}

}
