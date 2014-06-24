/**
 * @Title: ContactOperation.java
 * @Package com.geekchic.wuyou.service.operation
 * @Description: 联系人Operation
 * @author: evil
 * @date: May 8, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.GroupMembership;
import android.provider.ContactsContract.Groups;
import android.provider.ContactsContract.RawContacts.Data;

import com.geekchic.common.utils.PinyinUtils;
import com.geekchic.common.utils.StringUtils;
import com.geekchic.constant.AppConstants;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.network.exception.ConnectionException;
import com.geekchic.framework.network.exception.CustomRequestException;
import com.geekchic.framework.network.exception.DataException;
import com.geekchic.framework.service.core.BaseOperation;
import com.geekchic.wuyou.bean.Contact;
import com.geekchic.wuyou.bean.Contact.ComparatorPY;
import com.geekchic.wuyou.model.ContactDao;

/**
 * @ClassName: ContactOperation
 * @Descritpion: 联系人Operation
 * @author evil
 * @date May 8, 2014
 */
public class ContactOperation extends BaseOperation {
   List<Contact> regLists;
	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		regLists=ContactDao.getInstance(context).find();
		HashMap<String,Contact> contactMaps=new HashMap<String, Contact>(100);
        getPhones(contactMaps, context);
        getEmail(contactMaps, context);
        getGroups(contactMaps, context);
        ArrayList<Contact> contacts =HashMapToArrayList(contactMaps);
        List<Contact> reg=ContactDao.getInstance(context).find();
        Collections.sort(contacts, new ComparatorPY());
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList(AppConstants.RequestCode.REQUEST_RESULT,
				contacts);
		return bundle;
	}
  /**
   * 获取电话信息
   * @param contacts
   * @param context
   */
	private void getPhones(HashMap<String, Contact> contactMaps, Context context) {
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
				String id=cursor.getString(idColumn);
				Contact person=contactMaps.get(id);
				if(null==person){
					 person = new Contact();
					person.id=id;
					contactMaps.put(id, person);
				}
				person.name = cursor.getString(displayNameColumn).trim();
				person.pY = PinyinUtils.getPingYin(person.name).trim();
				person.fisrtSpell = PinyinUtils.getFirstSpell(person.name).trim();
				String tPhone=cursor.getString(phoneColumn).trim();
				person.phone.add(tPhone);
				if(!StringUtils.isNullOrEmpty(person.uuid)){
					continue;
				}
				for(Contact reg:regLists){
					if(reg.equals(person)){
						person.uuid=reg.uuid;
					}
				}
			}
			cursor.close();
		}
	}
	/**
	 * 获取联系人
	 */
	private void getEmail(HashMap<String, Contact> contactMaps, Context context){
		Cursor cursor=context.getContentResolver().query(Email.CONTENT_URI,   new String[]{Email.DATA, Email.CONTACT_ID}, null, null, Email.CONTACT_ID + " asc");
		if(cursor.moveToFirst()){
			int idColumn=cursor.getColumnIndex(Email.CONTACT_ID);
			int emailColumn=cursor.getColumnIndex(Email.DATA);
		    do {
				String cid=cursor.getString(idColumn);
				String email=cursor.getString(emailColumn);
				Contact person=contactMaps.get(cid);
				if(null==person){
					person=new Contact();
					person.id=cid;
					contactMaps.put(cid, person);
				}
				person.email=email;
			} while (cursor.moveToNext());
		}
	}
	public void getGroups(HashMap<String, Contact> contactMaps, Context context){
		HashMap<String,String> groupHashMap=new HashMap<String, String>();
		Cursor cursor=context.getContentResolver().query(ContactsContract.Groups.CONTENT_URI,new String[]{ContactsContract.Groups.TITLE, ContactsContract.Groups._ID}, null, null, null);
		if(cursor.moveToFirst()){
			int gidColumn=cursor.getColumnIndex(Groups._ID);
			int titleColumn=cursor.getColumnIndex(Groups.TITLE);
			do {
				String gid=cursor.getString(gidColumn);
				String title=cursor.getString(titleColumn);
				groupHashMap.put(gid, title);
			} while (cursor.moveToNext());
			
		}
		cursor.close();
		cursor=context.getContentResolver().query(android.provider.ContactsContract.Data.CONTENT_URI, new String[]{GroupMembership.CONTACT_ID, GroupMembership.GROUP_ROW_ID},
                Data.MIMETYPE + "=?",
                new String[]{GroupMembership.CONTENT_ITEM_TYPE}, null);
		if(cursor.moveToFirst()){
			int idColumn=cursor.getColumnIndex(GroupMembership.CONTACT_ID);
			int idGroupColumn=cursor.getColumnIndex(GroupMembership.GROUP_ROW_ID);
			do {
				String id=cursor.getString(idColumn);
				String groupID=cursor.getString(idGroupColumn);
				Contact person=contactMaps.get(id);
				if(null==person){
				    continue;
				}
				person.groups.add(groupHashMap.get(groupID));
			} while (cursor.moveToNext());
		}
		cursor.close();
		groupHashMap.clear();
	}
	private ArrayList<Contact> HashMapToArrayList(HashMap<String,Contact> contactMaps){
		ArrayList<Contact> persons=new ArrayList<Contact>();
		for(String key:contactMaps.keySet()){
			persons.add(contactMaps.get(key));
		}
		return persons;
	}
}
