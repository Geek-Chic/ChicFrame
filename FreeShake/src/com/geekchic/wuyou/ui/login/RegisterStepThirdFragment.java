/**
 * @Title: RegisterStepThirdFragment.java
 * @Package com.geekchic.wuyou.ui.login
 * @Description: 注册步骤三
 * @author: evil
 * @date: May 1, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.login;

import com.geekchic.wuyou.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @ClassName: RegisterStepThirdFragment
 * @Descritpion: 注册步骤三
 * @author evil
 * @date May 1, 2014
 */
public class RegisterStepThirdFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	View view=inflater.inflate(R.layout.register_step_third, null);
    	return view;
    }
}
