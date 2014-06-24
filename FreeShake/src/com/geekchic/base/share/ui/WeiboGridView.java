/**
 * @Title: WeiboGridView.java
 * @Package com.geekchic.base.share.ui
 * @Description: 分享界面
 * @author: Administrator
 * @date: 2014-6-26
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.share.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geekchic.base.share.ShareService;
import com.geekchic.common.utils.DeviceInfoUtil;

/**
 * @ClassName: WeiboGridView
 * @Descritpion: 分享网格 
 * @author Administrator
 * @date 2014-6-26
 */
public class WeiboGridView extends LinearLayout implements Runnable,OnPageChangeListener,OnClickListener
{
    private static final int PAGE_SIZE = 9;
    private ViewPager pager;
    private ImageView points[];
    private Bitmap grayPoint;
    private Bitmap whitePoint;
    private boolean silent;
    private ShareService weiboList[];
    private Handler handler;
    private Intent intent;
    private static class GridView extends LinearLayout{
        public void setData(int lines, ShareService weibos[])
        {
            this.lines = lines;
            this.weibos = weibos;
            init();
        }

        private void init()
        {
            int dp_10 = DeviceInfoUtil.dip2px(getContext(), 10);
            int scrW = getResources().getDisplayMetrics().widthPixels;
            iconWidth = (scrW - dp_10 * 2) / 3 - dp_10 * 4;
            setOrientation(1);
            int size = weibos != null ? weibos.length : 0;
            int lineSize = size / 3;
            if(size % 3 > 0)
                lineSize++;
            android.widget.LinearLayout.LayoutParams lp = new android.widget.LinearLayout.LayoutParams(-1, -1);
            lp.weight = 1.0F;
            for(int i = 0; i < lines; i++)
            {
                LinearLayout llLine = new LinearLayout(getContext());
                llLine.setLayoutParams(lp);
                addView(llLine);
                if(i < lineSize)
                {
                    for(int j = 0; j < 3; j++)
                    {
                        int index = i * 3 + j;
                        if(index >= size)
                        {
                            LinearLayout llItem = new LinearLayout(getContext());
                            llItem.setLayoutParams(lp);
                            llLine.addView(llItem);
                        } else
                        {
                            LinearLayout llItem = getView(index, getContext());
                            llItem.setTag(weibos[index]);
                            llItem.setOnClickListener(callback);
                            llItem.setLayoutParams(lp);
                            llLine.addView(llItem);
                        }
                    }

                }
            }

        }
    
        private LinearLayout getView(int position, Context context)
        {
            LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(1);
            int dp_5 = DeviceInfoUtil.dip2px(context, 5);
            ll.setPadding(dp_5, dp_5, dp_5, dp_5);
            ImageView iv = new ImageView(context);
            iv.setScaleType(android.widget.ImageView.ScaleType.CENTER_INSIDE);
            android.widget.LinearLayout.LayoutParams lpIv = new android.widget.LinearLayout.LayoutParams(iconWidth, iconWidth);
            lpIv.gravity = 1;
            iv.setLayoutParams(lpIv);
            iv.setImageBitmap(getIcon(weibos[position]));
            ll.addView(iv);
            TextView tv = new TextView(context);
            tv.setTextColor(-1);
            tv.setTextSize(1, 14F);
            tv.setSingleLine();
            tv.setGravity(17);
            android.widget.LinearLayout.LayoutParams lpTv = new android.widget.LinearLayout.LayoutParams(-1, -2);
            lpTv.weight = 1.0F;
            tv.setLayoutParams(lpTv);
            tv.setText(getName(weibos[position]));
            ll.addView(tv);
            return ll;
        }

        private Bitmap getIcon(ShareService weibo)
        {
            if(weibo == null)
                return null;
            String name = weibo.getName();
            if(name == null)
                return null;
            else
                return null;
//                        R.getBitmap(getContext(), (new StringBuilder("logo_")).append(weibo.getName().toLowerCase()).toString());
        }

        private String getName(ShareService weibo)
        {
            if(weibo == null)
                return "";
            String name = weibo.getName();
            if(name == null)
                return "";
            else
                return null;
                        //R.getString(getContext(), weibo.getName().toLowerCase());
        }

        private ShareService weibos[];
        private android.view.View.OnClickListener callback;
        private int lines;
        private int iconWidth;

        public GridView(Context context, android.view.View.OnClickListener callback)
        {
            super(context);
            this.callback = callback;
        }
    
    }
    public WeiboGridView(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }
    public void setData(Intent intent)
    {
        this.intent = intent;
        silent = intent.getBooleanExtra("silent", false);
    }
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onPageScrollStateChanged(int arg0)
    {
        // TODO Auto-generated method stub
         
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onPageSelected(int arg0)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void run()
    {
        // TODO Auto-generated method stub
        
    }
    
}
