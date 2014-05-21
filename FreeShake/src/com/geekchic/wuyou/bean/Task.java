/**
 * @Title: Task.java
 * @Package com.geekchic.wuyou.bean
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 19, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.geekchic.base.db.annotation.Column;
import com.geekchic.base.db.annotation.Id;
import com.geekchic.base.db.annotation.Table;

/**
 * @ClassName: Task
 * @Descritpion: Task实体类
 * @author evil
 * @date May 19, 2014
 */
@Table(name = "task")
public class Task implements Parcelable {
	public static class TaskField {
		public static final String TASK_UUID = "uuid";
		public static final String TASK_NAME = "task_name";
		public static final String TASK_ENDTIME = "end_time";
	}

	@Id
	@Column(name = "id")
	private int id;
	@Column(name = TaskField.TASK_UUID)
	private String uuid;
	@Column(name = TaskField.TASK_NAME)
	private String name;
	@Column(name = TaskField.TASK_ENDTIME)
	private long endtime;

	public Task() {

	}

	public Task(Parcel in) {
		id = in.readInt();
		uuid = in.readString();
		name = in.readString();
		endtime = in.readLong();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(uuid);
		dest.writeString(name);
		dest.writeLong(endtime);
	}

	public static final Creator<Task> CREATOR = new Creator<Task>() {
		public Task createFromParcel(final Parcel in) {
			return new Task(in);
		}

		public Task[] newArray(final int size) {
			return new Task[size];
		}
	};
}
