package com.geekchic.framework.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.geekchic.wuyou.R;

public class BasicDialog extends Dialog
{
	private DialogController mDialogController;
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
        mDialogController=new DialogController(getContext(), this, getWindow());
        setCanceledOnTouchOutside(cancelable);
        mCancelable=cancelable;
    }
    /**
     * 获取自定义视图
     * @return
     */
    public View getContentView(){
    	return mDialogController.getContentView();
    }
    public static class Builder{
    	/**
    	 * 构造参数
    	 */
    	private final DialogController.DialogParams params;
    	/**
    	 * Builder构造函数
    	 * @param context
    	 */
    	public Builder(Context context){
    		params=new DialogController.DialogParams(context);
    	}
    	/**
    	 * 设置消息内容
    	 * @param message
    	 * @return
    	 */
    	public Builder setMessage(String message){
    		params.mMessageText=message;
    		return this;
    	}
    	/**
    	 * 设置消息内容
    	 * @param messageID id
    	 * @return
    	 */
    	public Builder setMessage(int messageID){
    		setMessage(params.mContext.getString(messageID));
    		return this;
    	}
    	/**
    	 * 设置消息标题
    	 * @param title
    	 * @return
    	 */
    	public Builder setTitle(String title){
    		params.mTitle=title;
    		return this;
    	}
    	/**
    	 * 设置消息标题
    	 * @param titleID
    	 * @return
    	 */
    	public Builder setTitle(int titleID){
    		setTitle(params.mContext.getString(titleID));
    		return this;
    	}
    	/**
    	 * 设置自定义布局
    	 * @param view
    	 * @return
    	 */
    	public Builder setContentView(View view){
    		params.mCustomView=view;
    		return this;
    	}
    	/**
    	 * 设置自定义布局
    	 * @param viewID
    	 * @return
    	 */
    	public Builder setContentView(int viewID){
    		params.mCustomViewID=viewID;
    		return this;
    	}
    	/**
    	 * 创建确定按钮
    	 * @param positiveButtonText
    	 * @param listener
    	 * @return
    	 */
    	public Builder setPositiveButton(String positiveButtonText,DialogInterface.OnClickListener listener){
    		params.mPossitiveButtonText=positiveButtonText;
    		params.mPositiveClickListener=listener;
    		return this;
    	}
    	/**
    	 * 创建确定按钮
    	 * @param positiveText
    	 * @param listener
    	 * @return
    	 */
        public Builder setPositivewButton(int positiveText,DialogInterface.OnClickListener listener){
        	return setPositiveButton(params.mContext.getString(positiveText), listener);
        }
        /**
         * 创建取消按钮
         * @param negativeText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeText,DialogInterface.OnClickListener listener){
            params.mNegativeButtonText=negativeText;
            params.mNegativeClickListener=listener;
            return this;
        }
        /**
         * 创建取消按钮
         * @param negativeText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(int negativeTextID,DialogInterface.OnClickListener listener){
        	return setNegativeButton(params.mContext.getString(negativeTextID), listener);
        }
        /**
         * 创建中间按钮
         * @param neutralButtonText
         * @param listener
         * @return
         */
        public Builder setNeutralButtonBuilder(String neutralButtonText,DialogInterface.OnClickListener listener){
        	params.mNeutralButtonText=neutralButtonText;
        	params.mNegativeClickListener=listener;
        	return this;
        }
        /**
         * 创建中间按钮
         * @param neutralTextID
         * @param listener
         * @return
         */
        public Builder setNeutralButtonBuilder(int neutralTextID,DialogInterface.OnClickListener listener){
        	return setNegativeButton(params.mContext.getString(neutralTextID), listener);
        }
        public BasicDialog create(){
        	final BasicDialog dialog=new BasicDialog(params.mContext,R.style.AppDialog);
        	params.apply(dialog.mDialogController);
        	return dialog;
        }
    }
    
}
