package com.geekchic.base.share.ui.ui;

import com.geekchic.common.utils.DeviceInfoUtil;
import com.geekchic.common.utils.DisplayInfo;
import com.geekchic.wuyou.R;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow;

/**
 * 友推基础分享框，其他的分享popupwindow继承于此类
 * @author youtui
 * @since 14/5/4 
 */
public abstract class ShareBasePopupWindow extends PopupWindow implements
        OnItemClickListener
{
    private static final String TAG = "at YTBasePopupWindow:";
    
    /** 主分享界面实例 */
    protected static ShareBasePopupWindow instance;
    
    /** 主分享界面样式 */
    protected int showStyle = -1;
    
    /**传入的activity*/
    protected Context context;
    
    protected OnShareClickListener mOnShareClickListener;
    
    public ShareBasePopupWindow(Context context)
    {
        super(context);
        this.context = context;
    }
    
    /** 释放instance */
    @Override
    public void dismiss()
    {
        instance = null;
        super.dismiss();
    }
    
    /**
     * 设置弹框类型
     * @param popUpStyle
     */
    public void setStyle(int popUpStyle)
    {
        this.showStyle = popUpStyle;
    }
    
    /**
     * 添加操作监听器
     * @param shareClickListener
     */
    public void addShareClickListener(OnShareClickListener shareClickListener)
    {
        this.mOnShareClickListener = shareClickListener;
    }
    
    /**
     * 移除监听
     */
    public void removeShareClickListener()
    {
        this.mOnShareClickListener = null;
    }
    
    /**
     * 分享
     * @param index
     */
    public void OnShare(int index)
    {
        if (mOnShareClickListener != null)
        {
            mOnShareClickListener.onShareClick(index);
        }
    }
    
    /**
     * 显示选择弹框
     */
    public abstract void show();
    
    public interface OnShareClickListener
    {
        /**
         * 分享回调
         * @param index
         */
        public void onShareClick(int index);
        
    }
}
