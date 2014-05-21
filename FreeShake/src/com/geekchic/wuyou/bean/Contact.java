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

import com.geekchic.base.db.annotation.Column;
import com.geekchic.base.db.annotation.Id;
import com.geekchic.base.db.annotation.Table;

/**
 * @ClassName: Contact
 * @Descritpion: 联系人
 * @author evil
 * @date May 8, 2014
 */
@Table(name="contact")
public class Contact implements Parcelable {
	/**
	 * 数据库中的标识ID
	 */
	@Id
	@Column(name="_id")
	public String id; 
	/**
	 * 用户网络标识
	 */
	@Column(name="uuid")
	public String uuid;
	/**
	 * 姓名
	 */
	public String name;
	/**
	 * 是否匹配确定身份
	 */
	public int status;
	/**
	 * 拼音
	 */
	public String pY;
	/**
	 * 电话号码
	 */
	@Column(name="phone")
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
	 * 缓存组名
	 */
	public String listGroup;
	/**
	 * 中文首字母
	 */
	public String fisrtSpell;
	/**
	 * 电子邮件
	 */
	public String email;
	/**
	 * 同步标记
	 */
	public int sync;
	/**
	 * 同步时间戳
	 */
	public long  timestamp;
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
		this.uuid=in.readString();
		this.name = in.readString();
		this.status=in.readInt();
		this.pY = in.readString();
		this.fisrtSpell = in.readString();
		this.searchPhone=in.readString();
		this.email=in.readString();
		this.sync=in.readInt();
		this.timestamp=in.readLong();
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
		dest.writeString(uuid);
		dest.writeString(name);
		dest.writeInt(status);
		dest.writeString(pY);
		dest.writeString(fisrtSpell);
		dest.writeString(searchPhone);
		dest.writeString(email);
		dest.writeInt(sync);
		dest.writeLong(timestamp);
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
@Override
public boolean equals(Object o) {
	if(o instanceof Contact){
		Contact dest=(Contact) o;
		for(String p:phone){
			if(dest.phone.contains(p)){
				return true;
			}
		}
	}
	return false;
}
}
