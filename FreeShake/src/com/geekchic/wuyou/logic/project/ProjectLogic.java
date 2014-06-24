/**
 * @Title: ProjectLogic.java
 * @Package com.geekchic.wuyou.logic.project
 * @Description: 项目逻辑Logic
 * @author: evil
 * @date: Jun 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.logic.project;

import android.content.Context;
import android.os.Bundle;

import com.geekchic.constant.AppConstants.SERVICEWORK;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.logic.BaseLogic;
import com.geekchic.framework.network.RequestListener;
import com.geekchic.wuyou.bean.Project;
import com.geekchic.wuyou.logic.RequestManager;

/**
 * @ClassName: ProjectLogic
 * @Descritpion: 项目逻辑Logic
 * @author evil
 * @date Jun 2, 2014
 */
public class ProjectLogic extends BaseLogic implements IProjectLogic{
	/**
	 * 上下文 
	 */
   private Context mContext;
   /**
    * ProjectLogic构造函数
    * @param context
    */
   public ProjectLogic(Context context){
	   this.mContext=context;
   }
	@Override
	public void createProject(Project project) {
		Request request=new Request(SERVICEWORK.PROJECT_CREATE);
		request.put("project",project);
		RequestManager.getInstance(mContext).execute(request, new RequestListener() {
			
			@Override
			public void onRequestFinished(Request request, Bundle resultData) {
				
			}
			
			@Override
			public void onRequestDataError(Request request) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onRequestCustomError(Request request, Bundle resultData) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onRequestConnectionError(Request request, int statusCode) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
