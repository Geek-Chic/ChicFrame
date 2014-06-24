/**
 * @Title: FeedbackLogic.java
 * @Package com.geekchic.wuyou.logic.feedback
 * @Description: 反馈Logic
 * @author: evil
 * @date: May 17, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.logic.feedback;

import android.content.Context;
import android.os.Bundle;

import com.geekchic.constant.AppActionCode;
import com.geekchic.constant.AppConstants.SERVICEWORK;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.logic.ILogic;
import com.geekchic.framework.logic.BaseLogic;
import com.geekchic.framework.network.RequestListener;
import com.geekchic.wuyou.logic.RequestManager;

/**
 * @ClassName: FeedbackLogic
 * @Descritpion: 反馈Logic
 * @author evil
 * @date May 17, 2014
 */
public class FeedbackLogic extends BaseLogic implements IFeedbackLogic{
	/**
	 * 上下文 
	 */
  private Context mContext;
  /**
   * FeedbackLogic构造函数
   * @param context
   */
  public FeedbackLogic(Context context){
	  this.mContext=context;
  }
	@Override
	public void setFeedBack(String uuid, String message) {
		Request request=new Request(SERVICEWORK.WORKER_FEEDBACK);
		request.put("uuid", uuid);
		request.put("message", message);
		RequestManager.getInstance(mContext).execute(request, new RequestListener() {
			
			@Override
			public void onRequestFinished(Request request, Bundle resultData) {
				sendEmptyMessage(AppActionCode.FeedBackCode.FEED_BACK_SUCCESS);
			}
			
			@Override
			public void onRequestDataError(Request request) {
				sendEmptyMessage(AppActionCode.FeedBackCode.FEED_BACK_FAILED);
			}
			
			@Override
			public void onRequestCustomError(Request request, Bundle resultData) {
				
			}
			
			@Override
			public void onRequestConnectionError(Request request, int statusCode) {
				sendEmptyMessage(AppActionCode.BaseMessageCode.HTTP_ERROR);
			}
		});
	}

}
