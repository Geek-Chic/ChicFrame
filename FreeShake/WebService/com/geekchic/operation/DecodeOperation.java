/**
 * @Title: DecodeOperation.java
 * @Package com.geekchic.wuyou.service.operation
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 7, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.operation;

import java.util.Hashtable;
import java.util.concurrent.CountDownLatch;

import android.content.Context;
import android.os.Bundle;

import com.geekchic.common.log.Logger;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.network.exception.ConnectionException;
import com.geekchic.framework.network.exception.CustomRequestException;
import com.geekchic.framework.network.exception.DataException;
import com.geekchic.framework.service.core.Operation;
import com.google.zxing.DecodeHintType;

/**
 * @ClassName: DecodeOperation
 * @Descritpion: 解码
 * @author evil
 * @date May 7, 2014
 */
public class DecodeOperation implements Operation{
	/**
	 * TAG
	 */
      private static final String TAG="DecodeOperation";
	 public static final String BARCODE_BITMAP = "barcode_bitmap";
	  private final Hashtable<DecodeHintType, Object> hints;
	  private final CountDownLatch handlerInitLatch;
	  public DecodeOperation(){
		  handlerInitLatch = new CountDownLatch(1);

		    hints = new Hashtable<DecodeHintType, Object>(3);
	  }
	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		  Logger.d(TAG, "暂停");
		  try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  Logger.d(TAG, "暂停2");
		return null;
	}
 
}
