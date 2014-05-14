/**
 * @Title: Operation.java
 * @Package com.geekchic.framework.service.core
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.service.core;

import android.content.Context;
import android.os.Bundle;

import com.geekchic.framework.bean.Request;
import com.geekchic.framework.network.exception.ConnectionException;
import com.geekchic.framework.network.exception.CustomRequestException;
import com.geekchic.framework.network.exception.DataException;

/**
 * @ClassName: Operation
 * @Descritpion: 操作类接口
 * @author evil
 * @date May 2, 2014
 */
public interface Operation {
	/**
	 * 执行请求
	 * @param context
	 * @param request
	 * @return
	 * @throws ConnectionException
	 * @throws DataException
	 * @throws CustomRequestException
	 */
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException;
}
