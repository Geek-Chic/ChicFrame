/**
 * @Title: RegisterStepThirdFragment.java
 * @Package com.geekchic.wuyou.ui.login
 * @Description: 注册步骤三
 * @author: evil
 * @date: May 1, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.geekchic.wuyou.R;

/**
 * @ClassName: RegisterStepThirdFragment
 * @Descritpion: 注册步骤三
 * @author evil
 * @date May 1, 2014
 */
public class RegisterStepThirdFragment extends Fragment implements OnClickListener{
	/**
	 * 完成注册去主界面
	 */
	private Button mGoButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	View view=inflater.inflate(R.layout.register_step_third, null);
    	mGoButton=(Button) view.findViewById(R.id.register_nickname_confirm);
    	mGoButton.setOnClickListener(this);
    	return view;
    }
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.register_nickname_confirm){
			RegisterActivity activity=(RegisterActivity) getActivity();
			activity.stepToMain();
		}
	}
}
