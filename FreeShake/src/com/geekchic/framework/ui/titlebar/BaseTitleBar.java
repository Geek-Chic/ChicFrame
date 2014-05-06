package com.geekchic.framework.ui.titlebar;

import android.view.View.OnClickListener;

public class BaseTitleBar implements CommonTitleBarInterface{
    private TitleBar mTitleBar;
    public  BaseTitleBar(TitleBar titleBar){
    	this.mTitleBar=titleBar;
    }
	@Override
	public void setLeftButton(int id, OnClickListener listener) {
        mTitleBar.setLeftButtonDrawable(id);
        mTitleBar.setLeftButtonLinster(listener);
        
	}

	@Override
	public void setRightButton(int id, OnClickListener listener) {
		mTitleBar.setRightButtonDrawable(id);
		mTitleBar.setRightButtonListener(listener);
	}


	@Override
	public void setMiddleTitle(int id) {
		mTitleBar.setTitle(id);
	}

	@Override
	public void setMiddleTitle(String title) {
		mTitleBar.setTitle(title);
	}

	@Override
	public void setTitleBarBackground(int id) {
		 mTitleBar.setBackgroundResource(id);
	}

	@Override
	public void setTitleVisible(boolean visible) {
		if(visible){
			mTitleBar.showTitleBar();
		}else{
			mTitleBar.hideTitleBar();
		}
	}
	@Override
	public void setMiddleTitleDrawable(int id) {
		mTitleBar.setTitleDrawable(id);
	}

}
