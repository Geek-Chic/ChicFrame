/**
 * @Title: ContactsFragment.java
 * @Package com.geekchic.wuyou.ui.main
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 3, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekchic.wuyou.R;

/**
 * @ClassName: ContactsFragment
 * @Descritpion: [用一句话描述作用]
 * @author evil
 * @date May 3, 2014
 */
public class ContactsFragment extends Fragment {
	@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	  View view=inflater.inflate(R.layout.main_left_fragment_content, null);
	return view;
}
}
