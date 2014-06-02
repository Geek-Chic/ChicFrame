/**
 * @Title: MessageFragment.java
 * @Package com.geekchic.wuyou.ui.main
 * @Description:消息Fragment
 * @author: evil
 * @date: May 8, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.main;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.geekchic.framework.ui.BaseFrameFragment;
import com.geekchic.framework.ui.dialog.BasicDialog;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.bean.MessageItem;
import com.geekchic.wuyou.model.MessageItemDao;
import com.widget.pulltofresh.PullToRefreshListView;

/**
 * @ClassName: MessageFragment
 * @Descritpion: 消息Fragment
 * @author evil
 * @date May 8, 2014
 */
public class MessageFragment extends BaseFrameFragment{
	/**
	 * 下拉刷新
	 */
   private PullToRefreshListView mPullToRefreshListView;
   /**
    * 消息列表
    */
   private ListView mMessageListView;
   /**
    * 消息列表 
    */
   private List<MessageItem> mMessageItems;
   /**
    * 消息Adapter
    */
   private MessageAdapter mMessageAdapter;
   private OnItemClickListener mMessageClickListener=new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
//		FriendAuthDialog friendAuthDialog=new FriendAuthDialog(getActivity());
//		friendAuthDialog=friendAuthDialog.create();
//		friendAuthDialog.show();
		BasicDialog basicDialog = new BasicDialog.Builder(getActivity())
		.setContentView(R.layout.friend_info_dialog)
		.setTitle("成功获取新好友")
		.create();
basicDialog.show();
	}
};
   @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	   super.onCreateView(inflater, container, savedInstanceState);
	   View view=inflater.inflate(R.layout.message_fragment,null);
	   initView(view);
	   initData();
	return view;
}
	private void initView(View view) {
	mPullToRefreshListView=(PullToRefreshListView) view.findViewById(R.id.message_refersh_layout);
	mMessageListView=mPullToRefreshListView.getRefreshableView();
	mMessageListView.setOnItemClickListener(mMessageClickListener);
	
} 
	private void initData(){
		mMessageItems=MessageItemDao.getInstance(getActivity()).find();
		MessageItem messageItem=new MessageItem();
		messageItem.setMessage("蒋鹏已确认加您为好友，点击可查看对方信息");
		messageItem.setMsgtype(0);
		mMessageItems.add(messageItem);
		mMessageAdapter=new MessageAdapter(getActivity(), mMessageItems);
	    mMessageListView.setAdapter(mMessageAdapter);
	}
	/**
	 * @ClassName: MessageAdapter
	 * @Descritpion: 消息列表Adapter
	 * @author evil
	 * @date May 17, 2014
	 */
	class MessageAdapter extends BaseAdapter{
       /**
        * 上下文
        */
		
		private Context mContext;
		/**
		 * 消息List
		 */
		private List<MessageItem> messageItems;
		/**
		 * MessageAdapter构造函数
		 * @param context
		 * @param messageItem
		 */
		public MessageAdapter(Context context,List<MessageItem> messageItems){
			this.mContext=context;
			this.messageItems=messageItems;
		}
		@Override
		public int getCount() {
			return messageItems.size();
		}

		@Override
		public Object getItem(int position) {
			return messageItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder=null;
			if(convertView==null){
				convertView=LayoutInflater.from(mContext).inflate(R.layout.message_list_item, null);
				viewHolder=new ViewHolder();
				viewHolder.icon=(ImageView) convertView.findViewById(R.id.message_item_icon);
				viewHolder.mTitleTextView=(TextView) convertView.findViewById(R.id.message_item_title);
				viewHolder.mContenTextView=(TextView) convertView.findViewById(R.id.message_item_content);
				viewHolder.mLevelTextView=(TextView)convertView.findViewById(R.id.message_item_level);
				convertView.setTag(viewHolder);
			}else {
				viewHolder=(ViewHolder) convertView.getTag();
			}
			viewHolder.mContenTextView.setText(messageItems.get(position).getMessage());
			if(messageItems.get(position).getMsgtype()==0){
				viewHolder.mTitleTextView.setText("新好友通知");
			}else if(messageItems.get(position).getMsgtype()==1){
				viewHolder.mTitleTextView.setText("新任务通知");
			}else {
				viewHolder.mTitleTextView.setText("好友确认通知");
			}
			return convertView;
		}
		/**
		 * @ClassName: ViewHolder
		 * @Descritpion: View Holder
		 * @author evil
		 * @date May 19, 2014
		 */
		class ViewHolder{
			/**
			 * 消息图标
			 */
			ImageView icon;
			/**
			 * 消息标题
			 */
			TextView mTitleTextView;
			/**
			 * 消息内容 
			 */
			TextView mContenTextView;
			/**
			 * 消息等级
			 */
			TextView mLevelTextView;
		}
		
	}
}
