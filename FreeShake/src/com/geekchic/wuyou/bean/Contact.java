/**
 * @Title: Contact.java
 * @Package com.geekchic.wuyou.bean
 * @Description:  联系人
 * @author: evil
 * @date: May 8, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.bean;

import java.util.ArrayList;
import java.util.Comparator;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @ClassName: Contact
 * @Descritpion: 联系人
 * @author evil
 * @date May 8, 2014
 */
public class Contact implements Parcelable {
	/**
	 * 数据库中的标识ID
	 */
	public String id; 
	/**
	 * 姓名
	 */
	public String name;
	/**
	 * 拼音
	 */
	public String pY;
	/**
	 * 电话号码
	 */
	public ArrayList<String> phone;
	/**
	 * 组群
	 */
	public ArrayList<String> groups;
	/**
	 * 查找缓存的电话号码
	 */
	public String searchPhone;
	/**
	 * 中文首字母
	 */
	public String fisrtSpell;
	/**
	 * 电子邮件
	 */
	public String email;
	/**
	 * Person构造函数
	 */
	public Contact(){
		phone=new ArrayList<String>();
		groups=new ArrayList<String>();
	}

	/**
	 * Person构造函数
	 * 
	 * @param in
	 */
	public Contact(Parcel in) {
		this.id = in.readString();
		this.name = in.readString();
		this.pY = in.readString();
		this.fisrtSpell = in.readString();
		this.searchPhone=in.readString();
		this.email=in.readString();
		phone=new ArrayList<String>();
		groups=new ArrayList<String>();
		in.readStringList(phone);
		in.readStringList(groups);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(pY);
		dest.writeString(fisrtSpell);
		dest.writeString(searchPhone);
		dest.writeString(email);
		dest.writeStringList(phone);
		dest.writeStringList(groups);
	}

	public static final Creator<Contact> CREATOR = new Creator<Contact>() {
		public Contact createFromParcel(final Parcel in) {
			return new Contact(in);
		}

		public Contact[] newArray(final int size) {
			return new Contact[size];
		}
	};
	//根据姓名首字母的排序
	public static class ComparatorPY implements Comparator<Contact>{

		@Override
		public int compare(Contact lhs, Contact rhs) {
			String str1 = lhs.pY;
			String str2 = rhs.pY;
			return str1.compareToIgnoreCase(str2);
		}
	}

}
