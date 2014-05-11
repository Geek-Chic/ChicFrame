/**
 * @Title: IContactsLogic.java
 * @Package com.geekchic.wuyou.logic.contacts
 * @Description: 联系人ILogic
 * @author: evil
 * @date: May 8, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.logic.contacts;

import java.util.ArrayList;
import java.util.List;

import com.geekchic.wuyou.bean.Person;

/**
 * @ClassName: IContactsLogic
 * @Descritpion: 联系人ILogic
 * @author evil
 * @date May 8, 2014
 */
public interface IContactsLogic {
	/**
	 * 从联系人Provider处获取
	 */
  public void getContactsFromProvider();
  /**
   * 查找本地联系人
   * @param contacts
   */
  public void searchLocalContacts(String key,ArrayList<Person> contacts);
}
