/**
 * @Title: CommSelectItem.java
 * @Package com.geekchic.wuyou.ui.chat.adapter
 * @Description: 选择方式条目
 * @author: evil
 * @date: May 17, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.chat.adapter;

/**
 * @ClassName: CommSelectItem
 * @Descritpion: 选择方式条目
 * @author evil
 * @date May 17, 2014
 */
public class CommSelectItem {
	String name;
	String phone;
	boolean isSelected;
	/**
	 * CommSelectItem构造函数
	 * @param name
	 * @param phone
	 * @param isSelected
	 */
	public CommSelectItem(String name,String phone,boolean isSelected){
		this.name=name;
		this.phone=phone;
		this.isSelected=isSelected;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
}