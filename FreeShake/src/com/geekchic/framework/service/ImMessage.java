/**
 * @Title: ImMessage.java
 * @Package com.geekchic.framework.service
 * @Description: 推送消息Bean
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.service;

import java.io.Serializable;

/**
 * @ClassName: ImMessage
 * @Descritpion: 推送消息Bean
 * @author evil
 * @date Apr 30, 2014
 */
public class ImMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private String user_id;
	/**
	 * 频道ID
	 */
	private String channel_id;
	/**
	 * 用户名
	 */
	private String nick;
	/**
	 * 头像ID
	 */
	private int head_id;
	/**
	 * 时间戳
	 */
	private long time_samp;
	/**
	 * 消息内容
	 */
	private String message;
	/**
	 * 分组tag
	 */
	private String tag;

	public ImMessage(long time_samp, String message, String tag) {
		super();
		// SharePreferenceUtil util = PushApplication.getInstance().getSpUtil();
		// this.user_id = util.getUserId();
		// this.channel_id = util.getChannelId();
		// this.nick = util.getNick();
		// this.head_id = util.getHeadIcon();
		// this.time_samp = time_samp;
		// this.message = message;
		// this.tag = tag;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getHead_id() {
		return head_id;
	}

	public void setHead_id(int head_id) {
		this.head_id = head_id;
	}

	public long getTime_samp() {
		return time_samp;
	}

	public void setTime_samp(long time_samp) {
		this.time_samp = time_samp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "Message [user_id=" + user_id + ", channel_id=" + channel_id
				+ ", nick=" + nick + ", head_id=" + head_id + ", time_samp="
				+ time_samp + ", message=" + message + ", tag=" + tag + "]";
	}

}
