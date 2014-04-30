/**
 * @Title: CommonTitleBarInterface.java
 * @Package com.geekchic.framework.ui
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.ui;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * @ClassName: CommonTitleBarInterface
 * @Descritpion: [用一句话描述作用] 
 * @author evil
 * @date Apr 30, 2014
 */
public interface CommonTitleBarInterface {
	/**
	 * 设置左边按钮
	 * @param id
	 * @param listener
	 */
     public void setLeftButton(int id,OnClickListener listener);
     /**
      * 设置右边按钮
      * @param id
      * @param listener
      */
     public void setRightButton(int id,OnClickListener listener);
     /**
      * 设置Title
      * @param id
      */
     public void setMiddleTitle(int id);
     /**
      * 设置Title
      * @param title
      */
     public void setMiddleTitle(String title);
     /**
      * 设置Title资源
      * @param id
      */
     public void setMiddleTitleDrawable(int id);
     /**
      * 设置TitleBar背景
      * @param id
      */
     public void setTitleBarBackground(int id);
     /**
      * TitleBar是否可见
      * @param visible
      */
     public void setTitleVisible(boolean visible);
}
