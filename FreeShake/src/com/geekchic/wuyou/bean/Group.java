/**
 * @Title: Group.java
 * @Package com.geekchic.wuyou.bean
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 8, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.bean;

import com.geekchic.base.db.annotation.Column;
import com.geekchic.base.db.annotation.Id;
import com.geekchic.base.db.annotation.Table;

/**
 * @ClassName: Group
 * @Descritpion: 用户组
 * @author evil
 * @date May 8, 2014
 */
@Table(name = "group")
public class Group {
	public static class GroupField {
		public static final String GROUP_ID = "id";
		public static final String GROUP_GUID = "guid";
		public static final String GROUP_GNAME = "gname";
		public static final String GROUP_SEQID = "seq";
	}

	/**
	 * 组id
	 */
	@Id
	@Column(name = GroupField.GROUP_ID)
	private int id;
	/**
	 * 组uuid
	 */
	@Column(name = GroupField.GROUP_GUID)
	private String guid;
	/**
	 * 组名
	 */
	@Column(name = GroupField.GROUP_GNAME)
	private String gname;
	/**
	 * 顺序
	 */
	@Column(name = GroupField.GROUP_SEQID)
	private int seq;
}
