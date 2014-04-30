package com.geekchic.framework.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.geekchic.common.utils.StringUtil;
import com.geekchic.wuyou.R;

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
    private String mTitle;
    
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
     * 自定义View
     */
    private View mCustomView;
    /**
     * 自定义ListView
     */
    private ListView mListView;
    /**
     * 列表对话框ListView的adapter
     */
    private ListAdapter mAdapter;
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
     * 左按钮监听
     */
    private DialogInterface.OnClickListener mPositiveButtonListener;
    
    /**
     * 中立按钮监听
     */
    private DialogInterface.OnClickListener mNeutralButtonListener;
    
    /**
     * 右按钮监听
     */
    private DialogInterface.OnClickListener mNegativeButtonListener;
    
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
    private void initView(){
    	mWindow.setContentView(R.layout.dialog_basic);
    	LinearLayout top=(LinearLayout) mWindow.findViewById(R.id.topPanel);
    	LinearLayout content=(LinearLayout) mWindow.findViewById(R.id.contentPanel);
    	LinearLayout bottom=(LinearLayout) mWindow.findViewById(R.id.bottompanel);
    	initTop(top);
    	initBottom(bottom);
    }
    /**
     * 初始化对话框TopPanel
     * @param topPanel
     */
    private void initTop(LinearLayout topPanel){
    	if(!StringUtil.isNullOrEmpty(mTitle)){
    		topPanel.setVisibility(View.VISIBLE);
    		mTitleView=(TextView) topPanel.findViewById(R.id.title);
    		mTitleView.setText(mTitle);
    	}
    }
    /**
     * 初始化对话框BottomPanel
     * @param bottomPanel
     */
    private void initBottom(LinearLayout bottomPanel){
    	if(null!=mPositiveButtonText){
    		bottomPanel.setVisibility(View.VISIBLE);
    		mPositiveButton=(Button) bottomPanel.findViewById(R.id.positiveButton);
    		mPositiveButton.setVisibility(View.VISIBLE);
    		mPositiveButton.setText(mPositiveButtonText);
    		mPositiveButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(null!=mPositiveButtonListener){
						mPositiveButtonListener.onClick(mDialogInterface, DialogInterface.BUTTON_POSITIVE);
					}
					mDialogInterface.dismiss();
				}
			});
    	}
    	if(null!=mNeutralButtonText){
    		bottomPanel.setVisibility(View.VISIBLE);
    		mNeutralButton=(Button) bottomPanel.findViewById(R.id.neutralButton);
    		mNeutralButton.setVisibility(View.VISIBLE);
    		mNeutralButton.setText(mNeutralButtonText);
    		mNeutralButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(null!=mNeutralButtonListener){
						mNeutralButtonListener.onClick(mDialogInterface, DialogInterface.BUTTON_NEUTRAL);
					}
					mDialogInterface.dismiss();
				}
			});
    	}
    	if(null!=mNegativeButtonText){
    		bottomPanel.setVisibility(View.VISIBLE);
    		mNegativeButton=(Button) bottomPanel.findViewById(R.id.bottompanel);
    		mNegativeButton.setVisibility(View.VISIBLE);
    		mNegativeButton.setText(mNegativeButtonText);
    		mNegativeButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(null!=mNegativeButtonListener){
						mNegativeButtonListener.onClick(mDialogInterface, DialogInterface.BUTTON_NEGATIVE);
					}
					mDialogInterface.dismiss();
					
				}
			});
    	}
    }
    private void initContent(LinearLayout contentPanel){
    	ScrollView scrollView=(ScrollView) contentPanel.findViewById(R.id.dialog_content_scrollview);
    	if(null!=mMgeText){
    		mMessageView=(TextView)scrollView.findViewById(R.id.message_text);
    		mMessageView.setVisibility(View.VISIBLE);
    		mMessageView.setText(mMgeText);
    	}
    	if(null!=mCustomView){
    		if(mCustomView instanceof ListView){
    			mListView=(ListView) mCustomView;
    		}
    		else {
				contentPanel.removeAllViews();
				LinearLayout.LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
				contentPanel.addView(mCustomView,params);
			}
    	}
    	if(null!=mListView){
    		if(null!=mAdapter){
    			mListView.setAdapter(mAdapter);
    		}
    		contentPanel.removeAllViews();
    		LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    		contentPanel.addView(mListView,params);
    	}
    }
    /**
     * 消息内容
     * @param message
     */
    public void setMessage(String message){
    	mMgeText=message;
    }
    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title){
    	mTitle=title;
    	if(null!=mTitleView){
    		mTitleView.setText(mTitle);
    	}
    }
    /**
     * 设置自定义View
     * @param customView
     */
    public void setCustomView(View customView){
    	mCustomView=customView;
    }
    /**
     * 设定确定按钮
     * @param positiveText
     * @param listener
     */
    public void setPositiveButton(String positiveText,DialogInterface.OnClickListener listener){
    	mPositiveButtonText=positiveText;
    	mPositiveButtonListener=listener;
    }
    /**
     * 设置取消按钮
     * @param negativeText
     * @param listener
     */
    public void setNegativeButton(String negativeText,DialogInterface.OnClickListener listener){
    	mNegativeButtonText=negativeText;
    	mNegativeButtonListener=listener;
    }
    /**
     * 设置中间按钮
     * @param neutralText
     * @param listener
     */
    public void setNeutralButton(String neutralText,DialogInterface.OnClickListener listener){
    	mNeutralButtonText=neutralText;
    	mNeutralButtonListener=listener;
    }
    /**
     * 获取dialog上的button<BR>
     * @param whichButton 
     *               DialogInterface.BUTTON_POSITIVE 左按钮
     *               DialogInterface.BUTTON_NEGATIVE 右按钮
     * @return button
     */
    public Button getButton(int whichButton)
    {
        switch (whichButton)
        {
            case DialogInterface.BUTTON_POSITIVE:
                return mPositiveButton;
            case DialogInterface.BUTTON_NEUTRAL:
                return mNeutralButton;
            case DialogInterface.BUTTON_NEGATIVE:
                return mNegativeButton;
            default:
                return null;
        }
    }
    
    /**
     * 获取自定义视图
     * @return
     */
    public View getContentView(){
    	return mCustomView;
    }
    public static class DialogParams
    {
    	/**
    	 * Context
    	 */
    	public final Context mContext;
    	/**
    	 * LayoutInflater
    	 */
    	public final LayoutInflater mInflater;
    	/**
    	 * 标题
    	 */
    	public String mTitle;
    	/**
    	 * 内容
    	 */
    	public String mMessageText;
    	/**
    	 * 确定按钮文字
    	 */
    	public String mPossitiveButtonText;
    	/**
    	 * 中间按钮文字
    	 */
    	public String mNeutralButtonText;
    	/**
    	 * 取消按钮文字 
    	 */
    	public String mNegativeButtonText;
    	/**
    	 * 自定义布局
    	 */
    	public View mCustomView;
    	/**
    	 * 布局ID
    	 */
    	public int mCustomViewID=-1;
    	/**
    	 * Adapter
    	 */
    	public ListAdapter mAdapter;
    	/**
    	 * 列表按钮监听器
    	 */
    	public DialogInterface.OnClickListener mOnClickListener;
    	/**
    	 * 确定按钮监听器
    	 */
    	public DialogInterface.OnClickListener mPositiveClickListener;
    	/**
    	 * 取消按钮监听器
    	 */
    	public DialogInterface.OnClickListener mNegativeClickListener;
    	/**
    	 * 中间钮按监听器
    	 */
    	public DialogInterface.OnClickListener mNeutralClickListener;
    	/**
    	 * DialogParams构造函数
    	 * @param context
    	 */
    	public DialogParams(Context context){
    		this.mContext=context;
    		mInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	}
    	public void apply(DialogController controller){
    		if(!StringUtil.isNullOrEmpty(mTitle)){
    			controller.setTitle(mTitle);
    		}
    		if(!StringUtil.isNullOrEmpty(mPossitiveButtonText)){
    			controller.setPositiveButton(mPossitiveButtonText, mPositiveClickListener);
    		}
    		if(!StringUtil.isNullOrEmpty(mNegativeButtonText)){
    			controller.setNegativeButton(mNegativeButtonText, mNegativeClickListener);
    		}
    		if(!StringUtil.isNullOrEmpty(mNeutralButtonText)){
    			controller.setNeutralButton(mNeutralButtonText, mNeutralClickListener);
    		}
    		if(!StringUtil.isNullOrEmpty(mMessageText)){
    			controller.setMessage(mMessageText);
    		}
    		if(mCustomViewID>=0x01000000){
    			mCustomView=mInflater.inflate(mCustomViewID, null);
    		}
    		if(null!=mCustomView){
    			controller.setCustomView(mCustomView);
    		}
    	}
    }
}
