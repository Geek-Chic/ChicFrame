package com.geekchic.base.share.ui.ui;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.geekchic.base.share.ShareService;
import com.geekchic.common.utils.DeviceInfoUtil;
import com.geekchic.common.utils.DisplayInfo;
import com.geekchic.common.utils.NetUtil;
import com.geekchic.wuyou.R;

/**
 * viewpager+gridview 黑色网格样式分享样式
 * @author youtui
 * @since 14/4/25
 */
public class ViewPagerPopup extends ShareBasePopupWindow implements
        OnClickListener, OnPageChangeListener
{
    /**判断该分享页面是否正在运行*/
    private GridView pagerOne_gridView, pagerTwo_gridView;
    
    private ShareGridAdapter pagerOne_gridAdapter, pagerTwo_gridAdapter;
    
    private View sharepopup_indicator_linelay;
    
    private ImageView zeroIamge, oneIamge;
    
    private ArrayList<ShareService> services;
    
    private ViewPager viewPager;
    
    private final int ITEM_AMOUNT = 6;
    
    public ViewPagerPopup(Context context, ArrayList<ShareService> services)
    {
        super(context);
        this.services = services;
        instance = this;
    }
    
    /**
     * 显示分享界面
     */
    public void show()
    {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.yt_popup_viewpager, null);
        initButton(view);
        initViewPager(view);
        // 设置popupwindow的属性
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        setWidth(DisplayInfo.getScreenWidth(context));
        setHeight(DeviceInfoUtil.dip2px(context, 310));
        setAnimationStyle(R.style.SharePopupAnim);
        showAtLocation(getContentView(), Gravity.BOTTOM, 0, 0);
    }
    
    /**
     * 初始化积分按钮
     * 
     * @param view
     */
    
    private void initButton(View view)
    {
        zeroIamge = (ImageView) view.findViewById(R.id.sharepopup_zero_iv);
        oneIamge = (ImageView) view.findViewById(R.id.sharepopup_one_iv);
        Button cancelBt = (Button) view.findViewById(R.id.cancel_bt);
        cancelBt.setText("取  消");
        cancelBt.setOnClickListener(this);
    }
    
    /**
     * 初始化viewpager
     */
    private void initViewPager(View view)
    {
        viewPager = (ViewPager) view.findViewById(R.id.share_viewpager);
        sharepopup_indicator_linelay = view.findViewById(R.id.sharepopup_indicator_linelay);
        ArrayList<View> pagerList = new ArrayList<View>();
        // 如果分享的数目<=6，只放置一页
        if (services.size() <= 6)
        {
            View pagerOne = LayoutInflater.from(context)
                    .inflate(R.layout.yt_share_pager, null);
            pagerOne_gridView = (GridView) pagerOne.findViewById(R.id.sharepager_grid);
            pagerOne_gridAdapter = new ShareGridAdapter(context, services,
                    showStyle);
            pagerOne_gridView.setAdapter(pagerOne_gridAdapter);
            pagerOne_gridView.setOnItemClickListener(this);
            pagerList.add(pagerOne);
        }
        else if (services.size() > 6 && services.size() <= 12)
        {
            // 分享数量7~12之间,放置两页
            ArrayList<ShareService> pagerOneList = new ArrayList<ShareService>();
            for (int i = 0; i < 6; i++)
            {
                pagerOneList.add(services.get(i));
            }
            // 初始化第一页
            View pagerOne = LayoutInflater.from(context)
                    .inflate(R.layout.yt_share_pager, null);
            pagerOne_gridView = (GridView) pagerOne.findViewById(R.id.sharepager_grid);
            pagerOne_gridAdapter = new ShareGridAdapter(context, pagerOneList,
                    showStyle);
            pagerOne_gridView.setAdapter(pagerOne_gridAdapter);
            pagerOne_gridView.setOnItemClickListener(this);
            pagerList.add(pagerOne);
            
            ArrayList<ShareService> pagerTwoList = new ArrayList<ShareService>();
            for (int i = 6; i < services.size(); i++)
            {
                pagerTwoList.add(services.get(i));
            }
            // 初始化第二页
            View pagerTwo = LayoutInflater.from(context)
                    .inflate(R.layout.yt_share_pager, null);
            pagerTwo_gridView = (GridView) pagerTwo.findViewById(R.id.sharepager_grid);
            pagerTwo_gridAdapter = new ShareGridAdapter(context, pagerTwoList,
                    showStyle);
            pagerTwo_gridView.setAdapter(pagerTwo_gridAdapter);
            pagerTwo_gridView.setOnItemClickListener(this);
            pagerList.add(pagerTwo);
        }
        
        SharePagerAdapter pagerAdapter = new SharePagerAdapter(pagerList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        // 设置滑动下标
        if (services.size() > 6 && services.size() <= 12)
        {
            viewPager.setOnPageChangeListener(this);
        }
        else if (services.size() <= 6)
        {
            if (sharepopup_indicator_linelay != null)
            {
                sharepopup_indicator_linelay.setVisibility(View.INVISIBLE);
            }
        }
    }
    
    /**
     * 活动按钮事件
     */
    @Override
    public void onClick(View v)
    {
        
        if (v.getId() == R.id.cancel_bt)
        {
            this.dismiss();
        }
        
    }
    
    /**
     * 分享按钮点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View arg1,
            int position, long arg3)
    {
        if (NetUtil.isNetConnected(context))
        {
            if (adapterView == pagerOne_gridView)
            {
                //				new YTShare(act).doGridShare(position, 0,template,shareData,ITEM_AMOUNT);
                OnShare(position);
                
            }
            else if (adapterView == pagerTwo_gridView)
            {
                OnShare(position + ITEM_AMOUNT);
                //		new YTShare(act).doGridShare(position, 1,template,shareData,ITEM_AMOUNT);
            }
        }
        else
        {
            Toast.makeText(context, "无网络连接，请查看您的网络情况", Toast.LENGTH_SHORT)
                    .show();
        }
    }
    
    /**
     * viewpager状态变化监听
     */
    @Override
    public void onPageScrollStateChanged(int arg0)
    {
        
    }
    
    /**
     * viewpager滑动监听
     */
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2)
    {
        
    }
    
    /**
     * 页面选择监听，这里用来显示viewpager下标
     */
    @Override
    public void onPageSelected(int index)
    {
        // viewpager下标
        switch (index)
        {
            case 0:
                zeroIamge.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.icon_guide_dot_white));
                oneIamge.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.icon_guide_dot_black));
                break;
            case 1:
                zeroIamge.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.icon_guide_dot_black));
                oneIamge.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.icon_guide_dot_white));
                break;
            
            default:
                break;
        }
        
    }
    
    public static void close()
    {
        if (instance != null)
        {
            instance.dismiss();
        }
    }
    
    @Override
    public void dismiss()
    {
        // TODO Auto-generated method stub
        super.dismiss();
    }
}
