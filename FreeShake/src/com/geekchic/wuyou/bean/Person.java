/**
 * @Title: People.java
 * @Package com.geekchic.wuyou.bean
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 8, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.bean;

import java.util.Comparator;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @ClassName: People
 * @Descritpion: 联系人
 * @author evil
 * @date May 8, 2014
 */
public class Person implements Parcelable {
	/**
	 * 数据库中的标识ID
	 */
	public String id; // 数据库中的标识ID
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
	public String phone;
	/**
	 * 中文首字母
	 */
	public String fisrtSpell;
	/**
	 * Person构造函数
	 */
	public Person(){}

	/**
	 * Person构造函数
	 * 
	 * @param in
	 */
	public Person(Parcel in) {
		this.id = in.readString();
		this.name = in.readString();
		this.pY = in.readString();
		this.phone = in.readString();
		this.fisrtSpell = in.readString();
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
		dest.writeString(phone);
		dest.writeString(fisrtSpell);
	}

	public static final Creator<Person> CREATOR = new Creator<Person>() {
		public Person createFromParcel(final Parcel in) {
			return new Person(in);
		}

		public Person[] newArray(final int size) {
			return new Person[size];
		}
	};
	//根据姓名首字母的排序
	public static class ComparatorPY implements Comparator<Person>{

		@Override
		public int compare(Person lhs, Person rhs) {
			String str1 = lhs.pY;
			String str2 = rhs.pY;
			return str1.compareToIgnoreCase(str2);
		}
	}

}
