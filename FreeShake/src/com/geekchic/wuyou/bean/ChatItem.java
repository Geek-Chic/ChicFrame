/**
 * @Title: ChatItem.java
 * @Package com.geekchic.wuyou.bean
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 16, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.bean;

import java.util.Comparator;

import android.os.Parcel;
import android.os.Parcelable;

import com.geekchic.base.db.annotation.Column;
import com.geekchic.base.db.annotation.Id;

/**
 * @ClassName: ChatItem
 * @Descritpion: 聊天Bean
 * @author evil
 * @date May 16, 2014
 */
public class ChatItem implements Parcelable {
	public static class ChatField {
		public static final String CHAT_UUID = "uuid";
		public static final String CHAT_SENDERID = "senderuid";
		public static final String CHAT_SELFUID = "selfuid";
		public static final String CHAT_MESSAGEID = "messageid";
		public static final String CHAT_MESSAGE = "message";
		public static final String CHAT_MESSAGE_TYPE = "messagetype";
		public static final String CHAT_ISREAD = "isread";
		public static final String CHAT_ISSEND = "issend";
		public static final String CHAT_TIME = "time";
		public static final String CHAT_SEQ = "seq";
	}

	@Id
	@Column(name = "id")
	private int id;
	/**
	 * 发送uid
	 */
	@Column(name = ChatField.CHAT_SENDERID)
	private String sendUid;
	/**
	 * 接收uid
	 */
	@Column(name = ChatField.CHAT_SELFUID)
	private String selfUid;
	/**
	 * 消息id
	 */
	@Column(name = ChatField.CHAT_MESSAGEID)
	private String msgid;
	/**
	 * 消息类型
	 */
	@Column(name = ChatField.CHAT_MESSAGE_TYPE)
	private int msgtype;
	/**
	 * 消息内容
	 */
	@Column(name = ChatField.CHAT_MESSAGE)
	private String message;
	/**
	 * 是否发送
	 */
	@Column(name = ChatField.CHAT_ISSEND)
	private int issend;
	/**
	 * 是否已阅读
	 */
	@Column(name = ChatField.CHAT_ISREAD)
	private int isread;
	/**
	 * 发送时间
	 */
	@Column(name = ChatField.CHAT_TIME)
	private Long time;
	/**
	 * 发送顺序
	 */
	@Column(name = ChatField.CHAT_SEQ)
	private Long seq;

	public ChatItem(Parcel in) {
		sendUid = in.readString();
		selfUid = in.readString();
		msgid = in.readString();
		msgtype = in.readInt();
		message = in.readString();
		issend = in.readInt();
		isread = in.readInt();
		time = in.readLong();
		seq = in.readLong();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSendUid() {
		return sendUid;
	}

	public void setSendUid(String sendUid) {
		this.sendUid = sendUid;
	}

	public String getSelfUid() {
		return selfUid;
	}

	public void setSelfUid(String selfUid) {
		this.selfUid = selfUid;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public int getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(int msgtype) {
		this.msgtype = msgtype;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean issend() {
		return issend==1?true:false;
	}

	public void setIssend(boolean issend) {
		this.issend =issend?1:0;
	}

	public boolean isread() {
		return isread==1?true:false;
	}

	public void setIsread(boolean isread) {
		this.isread = isread?1:0;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(sendUid);
		dest.writeString(selfUid);
		dest.writeString(msgid);
		dest.writeInt(msgtype);
		dest.writeString(message);
		dest.writeInt(issend);
		dest.writeInt(isread);
		dest.writeLong(time);
		dest.writeLong(seq);
	}

	public static final Creator<ChatItem> CREATOR = new Creator<ChatItem>() {
		public ChatItem createFromParcel(final Parcel in) {
			return new ChatItem(in);
		}

		public ChatItem[] newArray(final int size) {
			return new ChatItem[size];
		}
	};

	// 根据seq排序
	public static class ComparatorPY implements Comparator<ChatItem> {

		@Override
		public int compare(ChatItem lhs, ChatItem rhs) {
			Long seq1 = lhs.getSeq();
			Long seq2 = rhs.getSeq();
			return seq1.compareTo(seq2);
		}
	}
}
