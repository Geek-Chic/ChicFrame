/**
 * @Title: ResizeLayout.java
 * @Package com.geekchic.base.share.ui
 * @Description: 可变LinearLayout
 * @author: Administrator
 * @date: 2014-6-26
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @ClassName: ResizeLayout
 * @Descritpion: 可变LinearLayout
 * @author jp
 * @date 2014-6-26
 */
public class ResizeLayout extends LinearLayout
{
    private OnResizeListener mOnResizeListener;
    
    public static interface OnResizeListener
    {
        public abstract void OnResize(int i, int j, int k, int l);
    }
    
    public void addResizeLinstener(OnResizeListener onResizeListener)
    {
        this.mOnResizeListener = onResizeListener;
    }
    
    public ResizeLayout(Context context)
    {
        super(context);
    }
    
    public ResizeLayout(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mOnResizeListener != null)
            mOnResizeListener.OnResize(w, h, oldw, oldh);
    }
    
}
