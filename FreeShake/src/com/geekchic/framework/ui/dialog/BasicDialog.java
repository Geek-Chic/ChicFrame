package com.geekchic.framework.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

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
        super(context, R.style.AppDialog);
    }
    /**
     * 构造函数
     * @param context 上下文
     * @param theme 主题
     */
    public BasicDialog(Context context, int theme)
    {
        super(context, theme);
        mDialogController=new DialogController(getContext(), this, getWindow());
        setCanceledOnTouchOutside(true);
        mCancelable=true;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	mDialogController.initView();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU)
        {
            // 屏蔽Menu键
            return true;
        }
        else if ((keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_SEARCH)
                && !mCancelable)
        {
            // 如果是返回键或搜索键，并且设置了不可取消，则返回不再处理
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
    /**
     * @ClassName: OnCheckStateChangeListener
     * @Descritpion: 多选状态监听器
     * @author evil
     * @date May 30, 2014
     */
    public interface OnCheckStateChangeListener
    {
         /**
          * 多选状态监听器
          * @param which
          * @param isChecked
          */
        public void onCheckedChanged(int which, boolean isChecked);
    }
    /**
     * 获取自定义视图
     * @return
     */
    public View getContentView(){
    	return mDialogController.getContentView();
    }
    public ListView getListView(){
    	return mDialogController.getListView();
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
         * 设置listview的adapter<BR>
         * @param adapter listview的adapter
         * @param listener listview的点击事件监听
         * @return builder
         */
        public Builder setAdapter(final ListAdapter adapter,
                DialogInterface.OnClickListener listener)
        {
            params.mAdapter = adapter;
            params.mOnClickListener = listener;
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
