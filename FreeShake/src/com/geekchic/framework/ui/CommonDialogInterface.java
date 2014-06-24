/**
 * @Title: CommDialogInterface.java
 * @Package com.geekchic.framework.ui
 * @Description:  通用Dialog
 * @author: evil
 * @date: Apr 29, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.ui;

/**
 * @ClassName: CommDialogInterface
 * @Descritpion: 通用Dialog
 * @author evil
 * @date Apr 29, 2014
 */
public interface CommonDialogInterface {
   public void showProgressDialog(String message,boolean cancelable);
   public void showProgressDialog(int messageId, boolean cancelable);
   public void closeProgressDialog();
}
