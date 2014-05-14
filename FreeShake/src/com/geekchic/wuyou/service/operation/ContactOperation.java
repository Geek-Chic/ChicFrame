/**
 * @Title: ContactOperation.java
 * @Package com.geekchic.wuyou.service.operation
 * @Description: 联系人Operation
 * @author: evil
 * @date: May 8, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.service.operation;

import java.util.ArrayList;
import java.util.Collections;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.RawContacts.Data;

import com.geekchic.common.utils.PinyinUtils;
import com.geekchic.constant.AppConstants;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.network.exception.ConnectionException;
import com.geekchic.framework.network.exception.CustomRequestException;
import com.geekchic.framework.network.exception.DataException;
import com.geekchic.framework.service.core.BaseOperation;
import com.geekchic.wuyou.bean.Person;
import com.geekchic.wuyou.bean.Person.ComparatorPY;

/**
 * @ClassName: ContactOperation
 * @Descritpion: 联系人Operation
 * @author evil
 * @date May 8, 2014
 */
public class ContactOperation extends BaseOperation {

	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		Bundle bundle = new Bundle();

		ArrayList<Person> contacts = new ArrayList<Person>();
        getPhones(contacts, context);
        
		Collections.sort(contacts, new ComparatorPY());
		bundle.putParcelableArrayList(AppConstants.RequestCode.REQUEST_RESULT,
				contacts);
		return bundle;
	}
  /**
   * 获取电话信息
   * @param contacts
   * @param context
   */
	private void getPhones(ArrayList<Person> contacts, Context context) {
		ContentResolver contentResolver = context.getContentResolver();
		// 获得所有联系人数据集的游标
		Cursor cursor = contentResolver.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		// 循环遍历
		if (cursor.moveToFirst()) {
			int idColumn = cursor.getColumnIndex(Data.RAW_CONTACT_ID);
			int displayNameColumn = cursor
					.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
			int phoneColumn = cursor
					.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

			while (cursor.moveToNext()) {
				Person person = new Person();
				person.id = cursor.getString(idColumn);
				person.name = cursor.getString(displayNameColumn);
				person.pY = PinyinUtils.getPingYin(person.name);
				person.fisrtSpell = PinyinUtils.getFirstSpell(person.name);
				person.phone = cursor.getString(phoneColumn);
				contacts.add(person);
			}
			cursor.close();
		}
	}
	/**
	 * 获取联系人
	 */
	private void getEmail(ArrayList<Person> contacts, Context context){
		Cursor cursor=context.getContentResolver().query(Email.CONTENT_URI,   new String[]{Email.DATA, Email.CONTACT_ID}, null, null, Email.CONTACT_ID + " asc");
		
	}
}
