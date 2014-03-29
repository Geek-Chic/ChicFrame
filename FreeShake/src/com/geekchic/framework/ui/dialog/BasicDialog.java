package com.geekchic.framework.ui.dialog;

import android.app.Dialog;
import android.content.Context;

public class BasicDialog extends Dialog
{
    private boolean mCancelable;
    /**
     * 默认构造函数
     * @param context 上下文
     */
    public BasicDialog(Context context)
    {
        super(context, 0);
    }
    /**
     * 构造函数
     * @param context 上下文
     * @param theme 主题
     */
    public BasicDialog(Context context, int theme)
    {
        super(context, theme);
    }
    /**
     * 构造函数，是否可出取消
     * @param context 上下文
     * @param theme 主题
     * @param cancelable 是否可取消
     */
    public BasicDialog(Context context, int theme,boolean cancelable)
    {
        super(context, theme);
    }
    
    
}
