/**
 * @Title: ContactLocalSearchOperation.java
 * @Package com.geekchic.wuyou.service.operation
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 11, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.service.operation;

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
import com.geekchic.wuyou.bean.Person;

/**
 * @ClassName: ContactLocalSearchOperation
 * @Descritpion: [用一句话描述作用] 
 * @author evil
 * @date May 11, 2014
 */
public class ContactLocalSearchOperation extends BaseOperation {

	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		ArrayList<Person> contacts=(ArrayList<Person>) request.getArrayList("contacts");
		String key=request.getString("key");
		ArrayList<Person> filterpersons=new ArrayList<Person>();
        //遍历所有联系人数组,筛选出包含关键字的联系人
        for (Person person:contacts) {  
            //过滤的条件
              if (StringUtil.isStrInString(person.phone,key)
            		||StringUtil.isStrInString(person.pY,key)
            		||person.name.contains(key)
            		||StringUtil.isStrInString(person.fisrtSpell,key)){
                //将筛选出来的联系人重新添加到filterpersons数组中
            	Person filterperson = new Person();
            	filterperson.id = person.id;
            	filterperson.name = person.name;
            	filterperson.pY = person.pY;
            	filterperson.phone = person.phone;
            	filterperson.fisrtSpell =person.fisrtSpell;
            	filterpersons.add(filterperson);
            }  
        }  
        Bundle bundle=new Bundle();
        bundle.putParcelableArrayList(RequestCode.REQUEST_RESULT, filterpersons);
		return bundle;
	}

}
