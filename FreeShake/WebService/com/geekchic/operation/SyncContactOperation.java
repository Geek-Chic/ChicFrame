/**
 * @Title: SyncContactOperation.java
 * @Package com.geekchic.wuyou.service.operation
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 17, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.operation;

import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geekchic.constant.AppConfig;
import com.geekchic.constant.AppConstants.RequestCode;
import com.geekchic.constant.AppConstants.ReturnCode;
import com.geekchic.framework.bean.HttpRequestBean.Method;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.network.HttpConnector.ConnectionResult;
import com.geekchic.framework.network.exception.ConnectionException;
import com.geekchic.framework.network.exception.CustomRequestException;
import com.geekchic.framework.network.exception.DataException;
import com.geekchic.framework.service.core.BaseOperation;
import com.geekchic.wuyou.bean.Contact;
import com.geekchic.wuyou.bean.URLs;
import com.geekchic.wuyou.model.ContactDao;

/**
 * @ClassName: SyncContactOperation
 * @Descritpion: 同步人联系人
 * @author evil
 * @date May 17, 2014
 */
public class SyncContactOperation extends BaseOperation{

	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		Contact contacts=(Contact) request.getParcelable("contacts");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("contacts",JSON.toJSONString(contacts));
		params.put("uuid", AppConfig.getInstance().getUid());
		ConnectionResult result = handleHttp(context, Method.POST, URLs.CONTACT_SYNC, params);
		Bundle bundle = new Bundle();
		JSONObject json = JSON.parseObject(result.body);
		int code = json.getIntValue("code");
		if (ReturnCode.CODE_SUCCESS == code) {
			bundle.putInt(RequestCode.REQUEST_CODE, 0);
			Contact res=new Contact();
			res.uuid=json.getString("uuid");
			res.phone.add(json.getString("phone"));
			ContactDao.getInstance(context).insert(res);
		} else {
			throw new DataException();
		}
		return bundle;
	}

}
