/**
 * @Title: BaseRequestService.java
 * @Package com.geekchic.framework.service.core
 * @Description: 网络请求Request Service
 * @author: evil
 * @date: May 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.service.core;

import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.geekchic.common.log.Logger;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.network.exception.ConnectionException;
import com.geekchic.framework.network.exception.CustomRequestException;
import com.geekchic.framework.network.exception.DataException;
import com.geekchic.framework.service.BaseRequestManager;

/**
 * @ClassName: BaseRequestService
 * @Descritpion: 网络请求Request Service
 * @author evil
 * @date May 2, 2014
 */
public abstract class BaseRequestService extends WorkerService {
	/**
	 * TAG
	 */
	private static final String TAG = "BaseRequestService";
	/**
	 * Intetn请求码
	 */
	public static final String INTENT_EXTRA_REQUEST = "com.geekchic.extra.request";
	/**
	 * Intent请求回调码
	 */
	public static final String INTENT_EXTRA_RECEIVER = "com.geekchic.extra.receiver";
	/**
	 * 请求成功
	 */
	public static final int CODE_TYPE_SUCCESS = 0;
	/**
	 * 连接错误
	 */
	public static final int CODE_ERROR_CONNEXION = 1;
	/**
	 * 数据错误
	 */
	public static final int CODE_ERROR_DATA = 2;
	/**
	 * 自定义错误
	 */
	public static final int CODE_ERROR_CUSTOM = 3;

	@Override
	protected void onHandleIntent(Intent intent) {
		Request request = intent.getParcelableExtra(INTENT_EXTRA_REQUEST);
		ResultReceiver receiver = intent
				.getParcelableExtra(INTENT_EXTRA_RECEIVER);
		Operation operation = getOperationForType(request.getRequestType());
		try {
			sendResult(receiver, operation.execute(this, request), CODE_TYPE_SUCCESS);
		} catch (ConnectionException e) {
			e.printStackTrace();
			Bundle bundle=new Bundle();
			bundle.putInt(BaseRequestManager.RECEIVER_EXTRA_CONNECTION_ERROR_STATUS_CODE, e.getStatusCode());
			sendResult(receiver,null,CODE_ERROR_CONNEXION);
			Logger.e(TAG, "ConnectionException", e);
		} catch (DataException e) {
			e.printStackTrace();
			sendResult(receiver,null,CODE_ERROR_DATA);
			Logger.e(TAG, "DataException", e);
		} catch (CustomRequestException e) {
			e.printStackTrace();
			sendResult(receiver,null,CODE_ERROR_CUSTOM);
			Logger.e(TAG, "CustomRequestException");

		}
	}

	/**
	 * 通过ResultReceiver返回结果
	 * 
	 * @param receiver
	 * @param data
	 * @param code
	 */
	private void sendResult(ResultReceiver receiver, Bundle data, int code) {
		if (null != receiver) {
			if (data == null) {
				data = new Bundle();
			}
		}
		receiver.send(code, data);
	}

	public abstract Operation getOperationForType(int requestType);
}
