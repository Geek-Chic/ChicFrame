/**
 * @Title: ShareAllGird.java
 * @Package com.geekchic.base.share.ui
 * @Description: [用一句话描述做什么]
 * @author: Administrator
 * @date: 2014-6-27
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.geekchic.base.share.util.ShareUtils;

/**
 * @ClassName: ShareAllGird
 * @Descritpion: 分享对话格子
 * @author Administrator
 * @date 2014-6-27
 */
public class ShareAllGird extends Activity implements OnClickListener
{
    private FrameLayout flPage;
    private WeiboGridView grid;
    private Button btnCancel;
    private Animation animShow;
    private Animation animHide;
    private boolean finishing; 
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initPageView();
        initAnim();
        setContentView(flPage);
        grid.setData(getIntent());
        btnCancel.setOnClickListener(this);
        flPage.clearAnimation();
        flPage.startAnimation(animShow);
//        AbstractWeibo.logDemoEvent(1, null);
    }
    private void initPageView()
    {
        flPage = new FrameLayout(this);
        LinearLayout llPage = new LinearLayout(this);
        llPage.setOrientation(1);
//        llPage.setBackgroundDrawable(ShareUtils.getDrawable(this, "share_vp_back"));
        llPage.setBackgroundColor(Color.BLACK);
        FrameLayout.LayoutParams lpLl = new FrameLayout.LayoutParams(-1, -2);
        lpLl.gravity = 80;
        llPage.setLayoutParams(lpLl);
        flPage.addView(llPage);
        grid = new WeiboGridView(this);
        android.widget.LinearLayout.LayoutParams lpWg = new android.widget.LinearLayout.LayoutParams(-1, -2);
        int dp_10 = ShareUtils.dipToPx(this, 10);
        lpWg.setMargins(0, dp_10, 0, 0);
        grid.setLayoutParams(lpWg);
        llPage.addView(grid);
        btnCancel = new Button(this);
        btnCancel.setTextColor(-1);
        btnCancel.setTextSize(1, 20F);
        btnCancel.setText(ShareUtils.getString(this, "cancel"));
        btnCancel.setPadding(0, 0, 0, ShareUtils.dipToPx(this, 5));
//        btnCancel.setBackgroundDrawable(ShareUtils.getDrawable(this, "btn_cancel_back"));
        android.widget.LinearLayout.LayoutParams lpBtn = new android.widget.LinearLayout.LayoutParams(-1, ShareUtils.dipToPx(this, 45));
        lpBtn.setMargins(dp_10, 0, dp_10, dp_10 * 2);
        btnCancel.setLayoutParams(lpBtn);
        llPage.addView(btnCancel);
    }
    
    private void initAnim()
    {
        animShow = new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, 1.0F, 1, 0.0F);
        animShow.setDuration(300L);
        animHide = new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, 0.0F, 1, 1.0F);
        animHide.setDuration(300L);
    }
    @Override
    public void onClick(View v)
    {
        if(v.equals(btnCancel)){
            finish();
        }
    }
    @Override
    public void finish()
    {
        super.finish();
        if(finishing){
            return;
        }else {
            finishing=true;
            animHide.setAnimationListener(new AnimationListener()
            {
                
                @Override
                public void onAnimationStart(Animation animation)
                {
                    // TODO Auto-generated method stub
                    
                }
                
                @Override
                public void onAnimationRepeat(Animation animation)
                {
                    // TODO Auto-generated method stub
                    
                }
                
                @Override
                public void onAnimationEnd(Animation animation)
                {
                    flPage.setVisibility(View.GONE);
                    finish();
                }
            });
            flPage.clearAnimation();
            flPage.startAnimation(animHide);
        }
    }
    
    
}
