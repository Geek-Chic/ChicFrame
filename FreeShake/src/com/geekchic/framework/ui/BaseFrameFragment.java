/**
 * @Title: BaseFrameFragment.java
 * @Package com.geekchic.framework.ui
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 5, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * @ClassName: BaseFrameFragment
 * @Descritpion: Fragment的扩展UI基类
 * @author evil
 * @date May 5, 2014
 */
public  class BaseFrameFragment extends BaseFragment implements CommonDialogInterface{
	/**
	 * TAG
	 */
	private static final String TAG = "BaseFrameFragment";
	/**
	 * 通用UI操作
	 */
	private CommonDialogInterface mCommonDialogInterface;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mCommonDialogInterface=new BaseCommDialog(getActivity());
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void showProgressDialog(int messageId, boolean cancelable) {
		if (mCommonDialogInterface != null) {
			mCommonDialogInterface.showProgressDialog(getString(messageId),
					cancelable);
		}
	}

	@Override
	public void showProgressDialog(String message, boolean cancelable) {
		if (mCommonDialogInterface != null) {
			mCommonDialogInterface.showProgressDialog(message, cancelable);
		}
	}
	/**
	 * 显示短时间的提示内容<BR>
	 * 
	 * @param resId
	 *            提示内容
	 */
	protected void showShortToast(int resId) {
		showShortToast(getString(resId));
	}

	/**
	 * 显示短时间的提示内容<BR>
	 * 
	 * @param content
	 *            提示内容
	 */
	protected void showShortToast(String content) {
		Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 显示长时间的提示内容<BR>
	 * 
	 * @param content
	 *            提示内容
	 */
	protected void showLongToast(String content) {
		Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
	}

	/**
	 * 显示长时间的提示内容<BR>
	 * 
	 * @param id
	 *            提示内容的stringid
	 */
	protected void showLongToast(int id) {
		Toast.makeText(getActivity(), id, Toast.LENGTH_LONG).show();
	}

	@Override
	public void closeProgressDialog() {
		mCommonDialogInterface.closeProgressDialog();
	}
	@Override
	protected void initLogics() {
		// TODO Auto-generated method stub
		
	}
}
