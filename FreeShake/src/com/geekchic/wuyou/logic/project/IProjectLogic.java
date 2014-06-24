/**
 * @Title: IProjectLogic.java
 * @Package com.geekchic.wuyou.logic.project
 * @Description: 项目逻辑接口
 * @author: evil
 * @date: Jun 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.logic.project;

import com.geekchic.wuyou.bean.Project;

/**
 * @ClassName: IProjectLogic
 * @Descritpion: 项目逻辑接口
 * @author evil
 * @date Jun 2, 2014
 */
public interface IProjectLogic  {
	/**
	 * 创建项目
	 * @param project
	 */
   public void createProject(Project project);
}
