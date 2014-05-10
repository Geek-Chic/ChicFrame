/**
 * @Title: Project.java
 * @Package com.geekchic.wuyou.bean
 * @Description: 项目实体类
 * @author: evil
 * @date: May 10, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.bean;

import java.util.List;

/**
 * @ClassName: Project
 * @Descritpion: 项目实体类
 * @author evil
 * @date May 10, 2014
 */
public class Project {
   /**
    * id
    */
  private String id;
  /**
   * 项目名
   */
  private String projectName;
  /**
   * 参与人员
   */
  private List<String> people;
  /**
   * 结束时间
   */
  private long endTime;
  /**
   * 项目优先级
   */
  private int level;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
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
public void setPeople(List<String> people) {
	this.people = people;
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
  
}
