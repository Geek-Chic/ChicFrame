/**
 * @Title: TitleBar.java
 * @Package com.geekchic.framework.ui
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: Apr 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.ui.titlebar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geekchic.wuyou.R;

/**
 * @ClassName: TitleBar
 * @Descritpion: TitleBar
 * @author evil
 * @date Apr 30, 2014
 */
public class TitleBar extends LinearLayout {
	/**
	 * 左按钮
	 */
	private ImageView mLeftButton;
	/**
	 * 右按钮
	 */
	private ImageView mRightButton;
	/**
	 * TitleBar的总体布局
	 */
	private View mTitlebarLayout;
	/**
	 * 中间标题
	 */
	private TextView mMiddleTitleView;
	/**
	 * 上下文
	 */
	private Context mContext;

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext=context;
		initView();
	}

	private void initView() {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mTitlebarLayout = inflater.inflate(R.layout.base_titlebar, null);
		setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		addView(mTitlebarLayout, layoutParams);
		
		mLeftButton=(ImageView) mTitlebarLayout.findViewById(R.id.titlebar_btn_left);
		mRightButton=(ImageView) mTitlebarLayout.findViewById(R.id.titlebar_btn_right);
		mMiddleTitleView=(TextView) mTitlebarLayout.findViewById(R.id.titlebar_text_title);
	}

	/**
	 * 不显示标题栏
	 */
	public void hideTitleBar() {
		setVisibility(View.GONE);
	}

	/**
	 * 显示标题栏
	 */
	public void showTitleBar() {
		setVisibility(View.VISIBLE);
	}
     /**
      * 设置中间标题资源
      * @param id
      */
	public void setTitleDrawable(int id) {
		mMiddleTitleView.setVisibility(View.VISIBLE);
		mMiddleTitleView.setBackgroundResource(id);
	}
	/**
	 * 设置中间标题文字
	 * @param title
	 */
	public void setTitle(String title){
		mMiddleTitleView.setVisibility(View.VISIBLE);
		mMiddleTitleView.setText(title);
	}
	/**
	 * 设置文字资源
	 * @param id
	 */
	public void setTitle(int id){
		setTitle(mContext.getString(id));
	}
    /**
     * 设置左边按钮资源
     * @param id
     */
	public void setLeftButtonDrawable(int id) {
		mLeftButton.setVisibility(View.VISIBLE);
		mLeftButton.setImageResource(id);
	}
    /**
     * 左边按钮图片
     * @param id
     */
	public void setRightButtonDrawable(int id) {
		mRightButton.setVisibility(View.VISIBLE);
		mRightButton.setImageResource(id);
	}
    /**
     * 左边按钮监听器
     * @param listener
     */
	public void setLeftButtonLinster(OnClickListener listener) {
		mLeftButton.setOnClickListener(listener);
	}
    /**
     * 右边按钮监听器
     * @param listener
     */
	public void setRightButtonListener(OnClickListener listener) {
		mRightButton.setOnClickListener(listener);
	}
	/**
	 *  获取右键
	 * @return
	 */
	public View getRightButton(){
		return mRightButton;
	}

}
