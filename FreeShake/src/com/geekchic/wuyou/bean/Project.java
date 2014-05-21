/**
 * @Title: Project.java
 * @Package com.geekchic.wuyou.bean
 * @Description: 项目实体类
 * @author: evil
 * @date: May 10, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.bean;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.geekchic.base.db.annotation.Column;
import com.geekchic.base.db.annotation.Id;
import com.geekchic.base.db.annotation.Table;

/**
 * @ClassName: Project
 * @Descritpion: 项目实体类
 * @author evil
 * @date May 10, 2014
 */
@Table(name = "project")
public class Project implements Parcelable {
	 public static class ProjectField{
		 public static final String PROJECT_ID="id";
		 public static final String PROJECT_PID="pid";
		 public static final String PROJECT_PNAME="proejctname";
		 public static final String PROJECT_NOTE="projectnote";
		 public static final String PROJECT_PEOPLE="people";
		 public static final String PROJECT_STARTTIME="starttime";
		 public static final String PROJECT_ENDTIME="endtime";
		 public static final String PROJECT_LEVEL="level";
		 public static final String PROJECT_TAG="tag";
	   }
	/**
	 * id
	 */
	@Id
	@Column(name=ProjectField.PROJECT_ID)
	private int id;
	/**
	 * gid
	 */
	@Column(name=ProjectField.PROJECT_PID)
	private String pid;
	/**
	 * 项目名
	 */
	@Column(name=ProjectField.PROJECT_PNAME)
	private String projectName;
	/**
	 * 项目说明
	 */
	@Column(name=ProjectField.PROJECT_NOTE)
	private String projectNote;
	/**
	 * 参与人员
	 */
	@Column(name=ProjectField.PROJECT_PEOPLE)
	private List<String> people;
	/**
	 * 开始时间
	 */
	@Column(name=ProjectField.PROJECT_STARTTIME)
	private Long startTime;
	/**
	 * 结束时间
	 */
	@Column(name=ProjectField.PROJECT_ENDTIME)
	private Long endTime;
	/**
	 * 项目优先级
	 */
	@Column(name=ProjectField.PROJECT_LEVEL)
	private int level;
	/**
	 *  推送Tag
	 */
	@Column(name=ProjectField.PROJECT_TAG)
	private String tag;
    public Project(){
    	people=new ArrayList<String>();
    }
    /**
     * Project构造函数
     * @param in
     */
	public Project(Parcel in){
		id=in.readInt();
		pid=in.readString();
		projectName=in.readString();
		projectNote=in.readString();
		startTime=in.readLong();
		endTime=in.readLong();
		level=in.readInt();
		tag=in.readString();
		people=new ArrayList<String>();
		in.readStringList(people);
		
	}
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<String> getPeople() {
		return people;
	}

	public void addPeople(String people) {
		if(people!=null){
			this.people.add(people);
		}
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	
	public String getProjectNote() {
		return projectNote;
	}
	public void setProjectNote(String projectNote) {
		this.projectNote = projectNote;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
         dest.writeInt(id);
         dest.writeString(pid);
         dest.writeString(projectName);
         dest.writeString(projectNote);
         dest.writeLong(startTime);
         dest.writeLong(endTime);
         dest.writeInt(level);
         dest.writeString(tag);
         dest.writeList(people);
	}

}
