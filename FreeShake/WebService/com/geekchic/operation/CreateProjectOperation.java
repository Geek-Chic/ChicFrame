/**
 * @Title: CreateProjectOperation.java
 * @Package com.geekchic.operation
 * @Description: 创建项目
 * @author: evil
 * @date: Jun 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.operation;

import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geekchic.constant.AppConstants.ReturnCode;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.bean.HttpRequestBean.Method;
import com.geekchic.framework.network.HttpConnector.ConnectionResult;
import com.geekchic.framework.network.exception.ConnectionException;
import com.geekchic.framework.network.exception.CustomRequestException;
import com.geekchic.framework.network.exception.DataException;
import com.geekchic.framework.service.core.BaseOperation;
import com.geekchic.wuyou.bean.Project;
import com.geekchic.wuyou.bean.URLs;

/**
 * @ClassName: CreateProjectOperation
 * @Descritpion: 创建项目
 * @author evil
 * @date Jun 2, 2014
 */
public class CreateProjectOperation extends BaseOperation {

	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		Project project=(Project) request.getParcelable("project");
		HashMap<String,String> params=getParamMap();
		params.put("project",JSON.toJSONString(project));
	    ConnectionResult result=handleHttp(context, Method.POST, URLs.PROJECT_CREATE, params);
	    JSONObject res=JSON.parseObject(result.body);
	    int code=res.getIntValue("code");
	    if(code==ReturnCode.CODE_SUCCESS){
	    	
	    }
		return null;
	}

}
