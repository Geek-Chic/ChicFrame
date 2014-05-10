/**
 * @Title: ChatActivity.java
 * @Package com.geekchic.wuyou.ui.chat
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 9, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.geekchic.constant.AppConfig;
import com.geekchic.constant.AppConstants;
import com.geekchic.constant.AppConstants.ChatMessageType;
import com.geekchic.framework.ui.titlebar.BaseTitleBarActivity;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.bean.MessageItem;
import com.geekchic.wuyou.ui.chat.adapter.EmotionItemAdapter;
import com.geekchic.wuyou.ui.chat.adapter.EmotionPageAdeapter;
import com.geekchic.wuyou.ui.chat.adapter.MessageAdapter;
import com.geekchic.wuyou.ui.chat.view.CirclePageIndicator;
import com.geekchic.wuyou.ui.chat.view.JazzyViewPager;
import com.geekchic.wuyou.ui.chat.view.JazzyViewPager.TransitionEffect;
import com.geekchic.wuyou.ui.chat.view.MsgListView.IXListViewListener;
import com.geekchic.wuyou.ui.chat.view.MsgListView;
import com.widget.pulltofresh.PullToRefreshBase;
import com.widget.pulltofresh.PullToRefreshBase.OnRefreshListener;
import com.widget.pulltofresh.PullToRefreshListView;

/**
 * @ClassName: ChatActivity
 * @Descritpion: 聊天界面
 * @author evil
 * @date May 9, 2014
 */
public class ChatActivity extends BaseTitleBarActivity implements OnClickListener{
	/**
	 * TAG
	 */
	private static final String TAG="ChatActivity";
	/**
	 * 聊天输入框
	 */
	private EditText mChatEditText;
	/**
	 * 表情
	 */
	private JazzyViewPager emotionViewPager;
	/**
	 * 圆点
	 */
	private CirclePageIndicator indicator;
	/**
	 * 表情当前页
	 */
	private int mCurrentPage = 0;
	/**
	 * 表情索引（标识）
	 */
	private List<String> mEmotionKeys;
	/**
	 * 打开关闭表情
	 */
	private ImageButton mEmotionButton;
	/**
	 * 发送按钮
	 */
	private Button mSendButton;
	/**
	 * 表情布局
	 */
	private LinearLayout mEmotionLinearLayout;
	/**
	 * 是否展开表情
	 */
	private boolean isEmotionShow;
	/**
	 * 聊天内容List
	 */
//	private MsgListView mMsgListView;
	private PullToRefreshListView mPullToRefreshListView;
	private ListView mMessageListView;
	/**
	 * 聊天内容List的Adapter
	 */
	private com.geekchic.wuyou.ui.chat.adapter.MessageAdapter adapter;
	/**
	 * 输入法管理
	 */
	private InputMethodManager imm;
	/**
	 * 窗口参数
	 */
	private WindowManager.LayoutParams params;
	/**
	 * 后退
	 */
	private OnClickListener mBackClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finishActivity();
		}

	};
	private OnTouchListener mChatListOnTouchListener=new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(v.getId()==R.id.chat_msg_listView){
				imm.hideSoftInputFromWindow(mChatEditText.getWindowToken(), 0);
				mEmotionLinearLayout.setVisibility(View.GONE);
				isEmotionShow=false;
			}else if(v.getId()==R.id.chat_message_edt) {
				imm.showSoftInput(mChatEditText, 0);
				mEmotionLinearLayout.setVisibility(View.GONE);
				isEmotionShow=false;
			}
			return false;
		}
	};
	/**
	 * 圆点导航监听器
	 */
	private OnPageChangeListener mOnPageChangeListener=new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int position) {
			 mCurrentPage=position;
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	/**
	 * 下拉刷新监听器
	 */
    private OnRefreshListener<ListView> mPullRefreshListener=new OnRefreshListener<ListView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			mPullToRefreshListView.onRefreshComplete();
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadData();
		initViw();
		initFacePage();
	}
    private void loadData(){
    	adapter = new MessageAdapter(this, loadCacheData());
    }
    /**
     * 从数据库读取历史消息
     * @return
     */
    private List<MessageItem> loadCacheData(){
    	List<MessageItem> msgList=new ArrayList<MessageItem>();
    	MessageItem messageItem=new MessageItem(ChatMessageType.MESSAGE_TYPE_TEXT, "我是谁",new Date().getTime(), "你好", 1, true, 0);
    	MessageItem messageItem2=new MessageItem(ChatMessageType.MESSAGE_TYPE_TEXT, "是我",new Date().getTime(), "你好啊", 1, false, 0);
    	msgList.add(messageItem);
    	msgList.add(messageItem2);
    	return msgList;
    }
	private void initViw() {
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		params = getWindow().getAttributes();
		mPullToRefreshListView= (PullToRefreshListView) findViewById(R.id.chat_msg_listView);
		mMessageListView=mPullToRefreshListView.getRefreshableView();
		mMessageListView.setOnTouchListener(mChatListOnTouchListener);
//		mMsgListView.setPullLoadEnable(false);
//		mMsgListView.setXListViewListener(mRefreshListener);
		mPullToRefreshListView.setOnRefreshListener(mPullRefreshListener);
		mMessageListView.setSelection(adapter.getCount()-1);
		mMessageListView.setAdapter(adapter);
		
		indicator=(CirclePageIndicator) findViewById(R.id.chat_emotion_indicator);
		mEmotionLinearLayout=(LinearLayout) findViewById(R.id.chat_emotion_layout);
		emotionViewPager = (JazzyViewPager) findViewById(R.id.chat_emotion_page);
		mChatEditText=(EditText) findViewById(R.id.chat_message_edt);
		mEmotionButton=(ImageButton) findViewById(R.id.chat_emotion_btn);
		mSendButton=(Button) findViewById(R.id.chat_send_btn);
		mEmotionButton.setOnClickListener(this);
		mSendButton.setOnClickListener(this);
	}

	private void initFacePage() {
		List<View> emotionViews = new ArrayList<View>();
		for (int i = 0; i < AppConstants.NUM_PAGE; ++i){
			emotionViews.add(getGridView(i));
		}
		EmotionPageAdeapter adapter = new EmotionPageAdeapter(emotionViews, emotionViewPager);
	emotionViewPager.setAdapter(adapter);
	emotionViewPager.setCurrentItem(mCurrentPage);
	emotionViewPager.setTransitionEffect(TransitionEffect.Standard);
	indicator.setViewPager(emotionViewPager);
	indicator.setOnPageChangeListener(mOnPageChangeListener);
	
	}
    /**
     * 初始化表情每一页的GridView
     * @param i
     * @return
     */
	private View getGridView(int i) {
		Set<String> keySets=AppConfig.getInstance().getFaceMap().keySet();
		mEmotionKeys=new ArrayList<String>();
		mEmotionKeys.addAll(keySets);
		GridView gv = new GridView(this);
		gv.setNumColumns(7);
		gv.setSelector(new ColorDrawable(Color.TRANSPARENT));// 屏蔽GridView默认点击效果
		gv.setBackgroundColor(Color.TRANSPARENT);
		gv.setCacheColorHint(Color.TRANSPARENT);
		gv.setHorizontalSpacing(1);
		gv.setVerticalSpacing(1);
		gv.setVerticalScrollBarEnabled(false);
		gv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		gv.setGravity(Gravity.CENTER);
		EmotionItemAdapter emotionItemAdapter=new EmotionItemAdapter(this, i);
		gv.setAdapter(emotionItemAdapter);
		gv.setOnTouchListener(onFibidScrollTouchListener);
		gv.setOnItemClickListener(onGridViewClickListener);
		
		return gv;
	}
	private OnItemClickListener onGridViewClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			//删除键
			if(position==AppConstants.NUM){
				int selection = mChatEditText.getSelectionStart();
				String text = mChatEditText.getText().toString();
				if(selection>0){
					String last=text.substring(selection-1);
					if("]".equals(last)){
						int start=text.lastIndexOf("[");
						int end=selection;
						mChatEditText.getText().delete(start, end);
						return;
					}
				}else {
					int count=mCurrentPage*AppConstants.NUM+position;
					Map<String, Integer> emotionMaps=AppConfig.getInstance().getFaceMap();
					
					int resId=(Integer) emotionMaps.values().toArray()[count];
					Bitmap bitmap=BitmapFactory.decodeResource(getResources(),resId);
					if(bitmap!=null){
						int rawHeigh = bitmap.getHeight();
						int rawWidth = bitmap.getHeight();
						int newHeight = 40;
						int newWidth = 40;
						// 计算缩放因子
						float heightScale = ((float) newHeight) / rawHeigh;
						float widthScale = ((float) newWidth) / rawWidth;
						// 新建立矩阵
						Matrix matrix = new Matrix();
						matrix.postScale(heightScale, widthScale);
						// 压缩后图片的宽和高以及kB大小均会变化
						Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0,
								rawWidth, rawHeigh, matrix, true);
						ImageSpan imageSpan = new ImageSpan(ChatActivity.this,
								newBitmap);
						String emojiStr = mEmotionKeys.get(count);
						SpannableString spannableString = new SpannableString(
								emojiStr);
						spannableString.setSpan(imageSpan,
								emojiStr.indexOf('['),
								emojiStr.indexOf(']') + 1,
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						mChatEditText.append(spannableString);
					}
				}
			}
		}
	};
	/**
	 * 防止GridView滚动
	 */
   private OnTouchListener onFibidScrollTouchListener=new OnTouchListener() {
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			return true;
		}
		return false;
	
	}
};
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.chat_main;
	}

	@Override
	public boolean initializeTitlBar() {
		setMiddleTitle(R.string.chat_title);
		setTitleBarBackground(R.color.blue);
		setLeftButton(R.drawable.icon_tab_metra_back_selector, mBackClickListener);
		return true;
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
	@Override
	protected void onPause() {
		imm.hideSoftInputFromInputMethod(mChatEditText.getWindowToken(), 0);
		mEmotionLinearLayout.setVisibility(View.GONE);
		isEmotionShow=false;
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.chat_emotion_btn){
			if (!isEmotionShow) {
				imm.hideSoftInputFromWindow(mChatEditText.getWindowToken(), 0);
				try {
					Thread.sleep(80);// 解决此时会黑一下屏幕的问题
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mEmotionLinearLayout.setVisibility(View.VISIBLE);
				isEmotionShow = true;
			} else {
				mEmotionLinearLayout.setVisibility(View.GONE);
				isEmotionShow = false;
			}
		}else if(v.getId()==R.id.chat_send_btn){
			
		}
	}

}
