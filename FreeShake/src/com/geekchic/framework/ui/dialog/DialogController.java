package com.geekchic.framework.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class DialogController
{
    /**
     * DialogInterface
     */
    private final DialogInterface mDialogInterface;
    /**
     * 窗体
     */
    private final Window mWindow;
    /**
     * 标题
     */
    private CharSequence mTitle;
    
    /**
     * 标题视图
     */
    private TextView mTitleView;
    
    /**
     * 消息体
     */
    private CharSequence mMgeText;
    
    /**
     * 消息体视图
     */
    private TextView mMessageView;
    
    /**
     * 左按钮文字
     */
    private CharSequence mPositiveButtonText;
    
    /**
     * 左按钮
     */
    private Button mPositiveButton;
    
    /**
     * 中立按钮文字
     */
    private CharSequence mNeutralButtonText;
    
    /**
     * 中立按钮
     */
    private Button mNeutralButton;
    
    /**
     * 右按钮文字
     */
    private CharSequence mNegativeButtonText;
    
    /**
     * 右按钮
     */
    private Button mNegativeButton;
    /**
     * 构造函数
     * @param context 上下文
     * @param dialogInterface DialogInterface
     * @param window 窗体
     */
    public DialogController(Context context,DialogInterface dialogInterface,Window window){
        this.mDialogInterface=dialogInterface;
        this.mWindow=window;
    }
    
    
    
}
