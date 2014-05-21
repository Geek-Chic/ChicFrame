/**
 * @Title: ChatPopupDialog.java
 * @Package com.geekchic.wuyou.ui.chat.adapter
 * @Description:选择默认聊天交流方式
 * @author: evil
 * @date: May 17, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.chat.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.geekchic.wuyou.R;

/**
 * @ClassName: ChatPopupDialog
 * @Descritpion: 选择默认聊天交流方式
 * @author evil
 * @date May 17, 2014
 */
public class PopNumAdapter extends BaseAdapter {
	/**
	 * 选项值
	 */
	private List<CommSelectItem> mSelectItems;
	/**
	 * 上下文
	 */
	private Context mContext;
	/**
	 * PopNumAdapter构造函数
	 * 
	 * @param context
	 * @param selectItems
	 */
	public PopNumAdapter(Context context, List<CommSelectItem> selectItems) {
		this.mContext = context;
		this.mSelectItems = selectItems;
	}

	@Override
	public int getCount() {
		return mSelectItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mSelectItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.chat_title_popup_item, null);
			viewHolder.mPhoneTextView = (TextView) convertView
					.findViewById(R.id.chat_popup_phone);
			viewHolder.mNameTextView = (TextView) convertView
					.findViewById(R.id.chat_popup_title);
			viewHolder.mSelectButton = (RadioButton) convertView
					.findViewById(R.id.chat_popup_select_rbt);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
       
		CommSelectItem item = mSelectItems.get(position);
		viewHolder.mPhoneTextView.setText(item.phone);
		if(position>0&&mSelectItems.get(position-1).getName().equals(item.name)){
			viewHolder.mNameTextView.setVisibility(View.GONE);
		}else {
			viewHolder.mNameTextView.setVisibility(View.VISIBLE);
			viewHolder.mNameTextView.setText(item.name);
		}
		viewHolder.mSelectButton.setChecked(item.isSelected);
		return convertView;
	}

	/**
	 * @ClassName: ViewHolder
	 * @Descritpion: ViewHolder
	 * @author evil
	 * @date May 17, 2014
	 */
	class ViewHolder {
		/**
		 * 姓名
		 */
		TextView mNameTextView;
		/**
		 * 通信方式
		 */
		TextView mPhoneTextView;
		/**
		 * 选项
		 */
		RadioButton mSelectButton;

	}



}
