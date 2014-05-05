/**
 * @Title: UserInfo.java
 * @Package com.geekchic.wuyou.bean
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 3, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.bean;

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
		 public static final String TYPE_PASSWORD = "password";
		 public static final String TYPE_NICKNAME = "nickname";
		 public static final String TYPE_EMAIL = "email";
		 public static final String TYPE_TIME = "time";
		 public static final String TYPE_STATUS = "status";
		 public static final String TYPE_SESSIONID = "sessionid";
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
	 * 注册时间
	 */
	@Column(name=UserField.TYPE_TIME)
	private String time;
	/**
	 * 验证状态
	 */
	@Column(name=UserField.TYPE_STATUS)
	private int status;
	/**
	 * sessionId
	 */
	@Column(name=UserField.TYPE_SESSIONID)
	private String sessionId;
	
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
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
  
}
