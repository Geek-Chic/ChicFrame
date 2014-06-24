/**
 * @Title: QuickAction.java
 * @Package com.geekchic.wuyou.ui.dialog
 * @Description: ActionBar的子项数据
 * @author: evil
 * @date: May 6, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.dialog;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * @ClassName: QuickAction
 * @Descritpion: ActionBar的子项数据
 * @author evil
 * @date May 6, 2014
 */
public class QuickAction {
    /**
     * 图标
     */
    public Drawable mDrawable;
    /**
     * 说明
     */
    public CharSequence mTitle;

    /* package */WeakReference<View> mView;

    public QuickAction(Drawable d, CharSequence title) {
        mDrawable = d;
        mTitle = title;
    }

    public QuickAction(Context ctx, int drawableId, CharSequence title) {
        mDrawable = ctx.getResources().getDrawable(drawableId);
        mTitle = title;
    }

    public QuickAction(Context ctx, Drawable d, int titleId) {
        mDrawable = d;
        mTitle = ctx.getResources().getString(titleId);
    }

    public QuickAction(Context ctx, int drawableId, int titleId) {
        mDrawable = ctx.getResources().getDrawable(drawableId);
        mTitle = ctx.getResources().getString(titleId);
    }

	public Drawable getIcon() {
		return mDrawable;
	}

	public void setIcon(Drawable mDrawable) {
		this.mDrawable = mDrawable;
	}

	public CharSequence getTitle() {
		return mTitle;
	}

	public void setTitle(CharSequence mTitle) {
		this.mTitle = mTitle;
	}    


}
