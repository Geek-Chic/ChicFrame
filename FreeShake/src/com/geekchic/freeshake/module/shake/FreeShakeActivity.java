package com.geekchic.freeshake.module.shake;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.geekchic.freeshake.R;
import com.widget.slidingmenu.SlidingActivity;
import com.widget.slidingmenu.SlidingMenu;

public class FreeShakeActivity extends SlidingActivity
{
    protected SlidingMenu mSlidingMenu;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int mScreenWidth = dm.widthPixels;// 获取屏幕分辨率宽度
        // customize the SlidingMenu
        setBehindContentView(R.layout.menu_frame_right);
        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);// 设置是左滑还是右滑，还是左右都可以滑，我这里左右都可以滑
        mSlidingMenu.setShadowWidth(mScreenWidth / 40);// 设置阴影宽度
        mSlidingMenu.setBehindOffset(mScreenWidth / 8);// 设置菜单宽度
        mSlidingMenu.setFadeDegree(0.35f);// 设置淡入淡出的比例
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        mSlidingMenu.setShadowDrawable(R.drawable.slidingmenu_left_shadow);// 设置左菜单阴影图片
        mSlidingMenu.setSecondaryShadowDrawable(R.drawable.slidingmenu_right_shadow);// 设置右菜单阴影图片
        mSlidingMenu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
        mSlidingMenu.setBehindScrollScale(0.333f);// 设置滑动时拖拽效果
        mSlidingMenu.setSecondaryMenu(R.layout.menu_frame);
        
    }
}
