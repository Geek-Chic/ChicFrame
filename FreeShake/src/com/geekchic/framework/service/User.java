/**
 * @Title: User.java
 * @Package com.geekchic.wuyou.bean
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.service;

import java.io.Serializable;

/**
 * @ClassName: User
 * @Descritpion: 用户
 * @author evil
 * @date Apr 30, 2014
 */
public class User implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private String UserId;
	/**
	 * 频道ID
	 */
	private String channelId;
	/**
	 * 用户名
	 */
	private String nick;
	/**
	 * 头像
	 */
	private int headIcon;
	/**
	 * 用户组
	 */
	private int group;

	public User(String UserId, String channelId, String nick, int headIcon,
			int group) {
		this.UserId = UserId;
		this.channelId = channelId;
		this.nick = nick;
		this.headIcon = headIcon;
		this.group = group;
	}

	public User() {

	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getHeadIcon() {
		return headIcon;
	}

	public void setHeadIcon(int headIcon) {
		this.headIcon = headIcon;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "User [UserId=" + UserId + ", channelId=" + channelId
				+ ", nick=" + nick + ", headIcon=" + headIcon + ", group="
				+ group + "]";
	}


}
