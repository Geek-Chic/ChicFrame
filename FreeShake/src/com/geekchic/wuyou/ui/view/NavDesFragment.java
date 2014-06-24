/**
 * @Title: NavDesFragment.java
 * @Package com.geekchic.wuyou.ui.view
 * @Description: 导航说明
 * @author: evil
 * @date: May 4, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geekchic.constant.AppAction;
import com.geekchic.wuyou.R;

/**
 * 
 * @ClassName: NavDesFragment
 * @Descritpion: 导航说明
 * @author evil
 * @date May 4, 2014
 */
public class NavDesFragment extends Fragment {

	private static final String ARG_POSITION = "position";
	/**
	 * 导航图IDs
	 */
	private final int[] mNavImgIds = { R.drawable.nav_des_one,
			R.drawable.nav_des_two, R.drawable.nav_des_three,
			R.drawable.nav_des_four };
	/**
	 * 导航说明IDs
	 */
	private final int[] mNavDesIds = { R.string.nav_step_one,
			R.string.nav_step_two, R.string.nav_step_three,
			R.string.nav_step_four };
	private int position;

	public static NavDesFragment newInstance(int position) {
		NavDesFragment f = new NavDesFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = getArguments().getInt(ARG_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// final int margin = (int) TypedValue.applyDimension(
		// TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
		// .getDisplayMetrics());
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.nav_item_des, null);
		ImageView imageView = (ImageView) layout
				.findViewById(R.id.nav_item_des_iv);
		imageView.setBackgroundResource(mNavImgIds[position]);
		TextView textView = (TextView) layout
				.findViewById(R.id.nav_item_des_tv);
		textView.setText(getResources().getString(mNavDesIds[position]));
		//最后一页显示跳转
		if (position == mNavDesIds.length - 1) {
			Button button = (Button) layout
					.findViewById(R.id.nav_item_start_btn);
			button.setVisibility(View.VISIBLE);
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(AppAction.LoginAction.ACTION);
					startActivity(intent);
					NavigateActivity navigateActivity = (NavigateActivity) getActivity();
					navigateActivity.finishActivity();
				}
			});
		}
		return layout;
	}

}