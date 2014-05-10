/**
 * @Title: MessageItem.java
 * @Package com.geekchic.wuyou.bean
 * @Description: 聊天消息Bean
 * @author: evil
 * @date: May 9, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.bean;

/**
 * @ClassName: MessageItem
 * @Descritpion: 聊天消息Bean
 * @author evil
 * @date May 9, 2014
 */
public class MessageItem {
	/**
	 * 消息类型
	 */
	private int msgType;
	/**
	 * 发送者姓名
	 */
	private String name;
	/**
	 * 时间
	 */
	private long time;
	/**
	 * 内容
	 */
	private String message;
	/**
	 * 头像ID
	 */
	private int headImg;
	/**
	 * 是否为收到的消息
	 */
	private boolean isComMeg = true;
	/**
	 * 是否新消息
	 */
	private int isNew;

	/**
	 * MessageItem构造函数
	 */
	public MessageItem() {
	}

	/**
	 * MessageItem构造函数
	 * 
	 * @param msgType
	 * @param name
	 * @param date
	 * @param message
	 * @param headImg
	 * @param isComMeg
	 * @param isNew
	 */
	public MessageItem(int msgType, String name, long date, String message,
			int headImg, boolean isComMeg, int isNew) {
		super();
		this.msgType = msgType;
		this.name = name;
		this.time = date;
		this.message = message;
		this.headImg = headImg;
		this.isComMeg = isComMeg;
		this.isNew = isNew;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDate() {
		return time;
	}

	public void setDate(long date) {
		this.time = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getHeadImg() {
		return headImg;
	}

	public void setHeadImg(int headImg) {
		this.headImg = headImg;
	}

	public boolean isComMeg() {
		return isComMeg;
	}

	public void setComMeg(boolean isComMeg) {
		this.isComMeg = isComMeg;
	}

	public int getIsNew() {
		return isNew;
	}

	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}
}
