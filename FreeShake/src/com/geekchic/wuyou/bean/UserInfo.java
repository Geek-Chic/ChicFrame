/**
 * @Title: UserInfo.java
 * @Package com.geekchic.wuyou.bean
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 3, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.bean;

import java.util.ArrayList;

import com.geekchic.base.db.annotation.Column;
import com.geekchic.base.db.annotation.Id;
import com.geekchic.base.db.annotation.Table;

/**
 * @ClassName: UserInfo
 * @Descritpion: [用一句话描述作用]
 * @author evil
 * @date May 3, 2014
 */
@Table(name="user")
public class UserInfo {
	 public static class UserField{
		 public static final String TYPE_UUID="uuid";
		 public static final String TYPE_PHONE = "phone";
		 public static final String TYPE_NICKNAME = "nickname";
		 public static final String TYPE_EMAIL = "email";
		 public static final String TYPE_TIME = "time";
		 public static final String TYPE_STATUS = "status";
		 public static final String TYPE_AVATOR="avator";
		 public static final String TYPE_GROUP="groupid";
	   }
	/**
	 * 用户ID
	 */
	@Id
	@Column(name=UserField.TYPE_UUID)
	private String uuid;
	/**
	 * 电话号码
	 */
	@Column(name=UserField.TYPE_PHONE)
	private String phone;
	/**
	 * 用户名
	 */
	@Column(name=UserField.TYPE_NICKNAME)
	private String nickName;
	/**
	 * 用户邮箱
	 */
	@Column(name=UserField.TYPE_EMAIL)
	private String email;
	/**
	 * 头像ID
	 */
	@Column(name=UserField.TYPE_AVATOR)
	private String headicon;
	/**
	 * 组别
	 */
	@Column(name=UserField.TYPE_GROUP)
	private int groupid;
	/**
	 * 注册时间
	 */
	@Column(name=UserField.TYPE_TIME)
	private String time;
	@Column(name="test")
	private ArrayList<String > test;
	/**
	 * 验证状态
	 */
	@Column(name=UserField.TYPE_STATUS)
	private int status;
	
	public UserInfo(){
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getHeadicon() {
		return headicon;
	}
	public void setHeadicon(String headicon) {
		this.headicon = headicon;
	}
	public int getGroup() {
		return groupid;
	}
	public void setGroup(int group) {
		this.groupid = group;
	}
  
}
