/**
 * @Title: ContactsLogic.java
 * @Package com.geekchic.wuyou.logic.contacts
 * @Description:  联系人Logic
 * @author: evil
 * @date: May 8, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.logic.contacts;

import java.util.ArrayList;

import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.geekchic.base.mutitask.TaskListener;
import com.geekchic.constant.AppActionCode;
import com.geekchic.constant.AppConstants.RequestCode;
import com.geekchic.constant.AppConstants.SERVICEWORK;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.logic.BaseLogic;
import com.geekchic.framework.network.RequestListener;
import com.geekchic.operation.ContactLocalSearchOperation;
import com.geekchic.wuyou.bean.Contact;
import com.geekchic.wuyou.bean.UserInfo;
import com.geekchic.wuyou.logic.RequestManager;
import com.geekchic.wuyou.model.ContactDao;

/**
 * @ClassName: ContactsLogic
 * @Descritpion: 联系人Logic
 * @author evil
 * @date May 8, 2014
 */
public class ContactsLogic extends BaseLogic implements IContactsLogic {
	/**
	 * TAG
	 */
	private static final String TAG = "ContactsLogic";
    /**
     * 上下文
     */
	private Context mContext;

	public ContactsLogic(Context context) {
		this.mContext = context;
	}
	@Override
	public void getContactsFromProvider() {
	   Request request=new Request(SERVICEWORK.WORKER_CONTACTS_FROM_PROVIDER);
	   RequestManager.getInstance(mContext).execute(request, new RequestListener() {
		
		@Override
		public void onRequestFinished(Request request, Bundle resultData) {
			ArrayList<Contact> contacts=resultData.getParcelableArrayList(RequestCode.REQUEST_RESULT);
			sendMessage(AppActionCode.ContactsCode.MESSAGE_CONSTACTS_PROVIDE_SUCCESS,contacts);
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
	@Override
	public void searchLocalContacts(String key,ArrayList<Contact> contacts) {
		  Request request=new Request(SERVICEWORK.WORKER_CONTACTS_LCOAL_SERACH);
		  request.put("key", key);
		  request.putList("contacts",contacts);
		  RequestManager.getInstance(mContext).execute(request, new RequestListener() {
			
			@Override
			public void onRequestFinished(Request request, Bundle resultData) {
				ArrayList<Contact> contacts=resultData.getParcelableArrayList(RequestCode.REQUEST_RESULT);
				sendMessage(AppActionCode.ContactsCode.MESSAGE_CONSTACTS_LOCAL_SEARCH_SUCCESS, contacts);
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
	@Override
	public void syncContacts(Contact contacts) {
		Request request=new Request(SERVICEWORK.WORKER_CONTACT_SYNC);
		request.put("contacts", contacts);
		JSON.toJSONString(contacts);
		RequestManager.getInstance(mContext).execute(request, new RequestListener() {
			
			@Override
			public void onRequestFinished(Request request, Bundle resultData) {
               sendMessage(AppActionCode.ContactsCode.MESSAGE_CONTACTS_SYNC_SUCCESS,0);			
			}
			
			@Override
			public void onRequestDataError(Request request) {
				sendEmptyMessage(AppActionCode.ContactsCode.MESSAGE_CONTACTS_SYNC_FAILED);
			}
			
			@Override
			public void onRequestCustomError(Request request, Bundle resultData) {
				sendEmptyMessage(AppActionCode.ContactsCode.MESSAGE_CONTACTS_SYNC_FAILED);
			}
			
			@Override
			public void onRequestConnectionError(Request request, int statusCode) {
				sendMessage(AppActionCode.BaseMessageCode.HTTP_ERROR, statusCode);
			}
		});
	}

}
