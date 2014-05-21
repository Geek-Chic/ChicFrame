/**
 * @Title: ContactLocalSearchOperation.java
 * @Package com.geekchic.wuyou.service.operation
 * @Description: 本地查找联系人信息
 * @author: evil
 * @date: May 11, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.operation;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;

import com.geekchic.common.utils.StringUtil;
import com.geekchic.constant.AppConstants.RequestCode;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.network.exception.ConnectionException;
import com.geekchic.framework.network.exception.CustomRequestException;
import com.geekchic.framework.network.exception.DataException;
import com.geekchic.framework.service.core.BaseOperation;
import com.geekchic.wuyou.bean.Contact;

/**
 * @ClassName: ContactLocalSearchOperation
 * @Descritpion: 本地查找联系人信息
 * @author evil
 * @date May 11, 2014
 */
public class ContactLocalSearchOperation extends BaseOperation {
//	@Override
//	protected ArrayList<Contact> doInBackground(Request... params) {
//		String key=params[0].getString("key");
//		ArrayList<Contact> mSearchContacts=params[0].getArrayList("contacts");
//		ArrayList<Contact> filterpersons=new ArrayList<Contact>();
//		   for (Contact person:mSearchContacts) {  
//	            //过滤的条件
//	              if (  StringUtil.isStrInString(person.searchPhone, key)
//	            		||StringUtil.isStrInString(person.pY,key)
//	            		||person.name.contains(key)
//	            		||StringUtil.isStrInString(person.fisrtSpell,key)){
//	                //将筛选出来的联系人重新添加到filterpersons数组中
//	            	  Contact filterperson = new Contact();
//	            	filterperson.name = person.name;
//	            	filterperson.pY = person.pY;
//	            	filterperson.searchPhone = person.searchPhone;
//	            	filterperson.fisrtSpell =person.fisrtSpell;
//	            	filterpersons.add(filterperson);
//	            }  
//	        }  
//		return filterpersons;
//	}

	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
			ArrayList<Contact> contacts= request.getArrayList("contacts");
		String key=request.getString("key");
		ArrayList<Contact> filterpersons=new ArrayList<Contact>();
        //遍历所有联系人数组,筛选出包含关键字的联系人
        for (Contact person:contacts) {  
            //过滤的条件
              if (  StringUtil.isStrInString(person.searchPhone, key)
            		||StringUtil.isStrInString(person.pY,key)
            		||person.name.contains(key)
            		||StringUtil.isStrInString(person.fisrtSpell,key)){
                //将筛选出来的联系人重新添加到filterpersons数组中
            	  Contact filterperson = new Contact();
            	filterperson.name = person.name;
            	filterperson.pY = person.pY;
            	filterperson.searchPhone = person.searchPhone;
            	filterperson.fisrtSpell =person.fisrtSpell;
            	filterpersons.add(filterperson);
            }  
        }  
        Bundle bundle=new Bundle();
        bundle.putParcelableArrayList(RequestCode.REQUEST_RESULT, filterpersons);
		return bundle;
	}

}
