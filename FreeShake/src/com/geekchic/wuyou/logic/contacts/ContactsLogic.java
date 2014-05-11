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
import java.util.List;

import android.content.Context;
import android.os.Bundle;

import com.geekchic.common.log.Logger;
import com.geekchic.constant.AppAction;
import com.geekchic.constant.AppActionCode;
import com.geekchic.constant.AppConstants;
import com.geekchic.constant.AppConstants.REQUESTCODE;
import com.geekchic.constant.AppConstants.SERVICEWORK;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.logic.BaseLogic;
import com.geekchic.framework.network.RequestListener;
import com.geekchic.wuyou.bean.Person;
import com.geekchic.wuyou.logic.RequestManager;

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
			ArrayList<CharSequence> contacts=resultData.getCharSequenceArrayList(REQUESTCODE.REQUEST_RESULT);
			sendMessage(AppActionCode.ContactsCode.MESSAGE_CONSTACTS_PROVIDE_SUCCESS,contacts);
			 Logger.d(TAG, contacts.toString());
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
	public void searchLocalContacts(String key,ArrayList<Person> contacts) {
		  Request request=new Request(SERVICEWORK.WORKER_CONTACTS_LCOAL_SERACH);
		  request.put(REQUESTCODE.REQUEST_KEY, key);
		  request.putList(REQUESTCODE.REQUEST_LIST_DATA,contacts);
		  RequestManager.getInstance(mContext).execute(request, new RequestListener() {
			
			@Override
			public void onRequestFinished(Request request, Bundle resultData) {
				ArrayList<CharSequence> contacts=resultData.getCharSequenceArrayList(REQUESTCODE.REQUEST_RESULT);
				sendMessage(AppActionCode.ContactsCode.MESSAGE_CONSTACTS_LOCAL_SEARCH_SUCCESS,contacts);
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
