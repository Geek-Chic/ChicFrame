/**
 * @Title: ProfileFragment.java
 * @Package com.geekchic.wuyou.ui.main
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 3, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.main;

import com.geekchic.framework.ui.BaseFrameFragment;
import com.geekchic.wuyou.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @ClassName: ProfileFragment
 * @Descritpion: [用一句话描述作用]
 * @author evil
 * @date May 3, 2014
 */
public class ProfileFragment extends BaseFrameFragment {
	/**
	 * 创建ProfileFragment
	 * @return
	 */
	public static ProfileFragment newInstance(){
		ProfileFragment profileFragment=new ProfileFragment();
		return profileFragment;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.profile_fragment_content, null);
		return view;
	}
}
