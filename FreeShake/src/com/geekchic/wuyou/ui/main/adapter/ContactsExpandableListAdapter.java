/**
 * @Title: ContactsExpandableListAdapter.java
 * @Package com.geekchic.wuyou.ui.main
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 11, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.main.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekchic.common.log.Logger;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.bean.UserInfo;
import com.hp.hpl.sparta.xpath.PositionEqualsExpr;
import com.widget.xlistview.IphoneTreeView;
import com.widget.xlistview.IphoneTreeView.IphoneTreeHeaderAdapter;

/**
 * @ClassName: ContactsExpandableListAdapter
 * @Descritpion: 联系人Adapter
 * @author evil
 * @date May 11, 2014
 */
public class ContactsExpandableListAdapter  extends
BaseExpandableListAdapter implements IphoneTreeHeaderAdapter{
	/**
	 * TAG
	 */
	private static final String TAG="ContactsExpandableListAdapter";
    private LayoutInflater mInflater;
	/**
	 * 用户组
	 */
	private List<String> mGroup;
	/**
	 * 用户组成员
	 */
	private Map<Integer, List<UserInfo>> mChildrenMap;
	/**
	 * 上下文
	 */
	private Context mContext;
	/**
	 * 组状态
	 */
	private HashMap<Integer, Integer> groupStatusMap = new HashMap<Integer, Integer>();
	/**
	 * 多级列表
	 */
    private IphoneTreeView mXListView;
    int tempPosition;
	/**
	 * ContactsExpandableListAdapter构造函数
	 * 
	 * @param group
	 * @param children
	 */
	public ContactsExpandableListAdapter(Context context,List<String> group,
			Map<Integer, List<UserInfo>> children,IphoneTreeView xListView) {
		this.mContext=context;
		this.mGroup = group;
		this.mChildrenMap = children;
		this.mXListView=xListView;
		mInflater=LayoutInflater.from(mContext);
	}

	public void addUser(UserInfo userInfo) {
		int groupID = userInfo.getGroup();
		if (mChildrenMap.containsKey(groupID)) {
			mChildrenMap.get(groupID).add(userInfo);
			notifyDataSetChanged();
		}
	}

	@Override
	public int getGroupCount() {
		return mGroup.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		int count = -1;
		if (mChildrenMap.get(groupPosition) != null) {
			count = mChildrenMap.get(groupPosition).size();
		}
		return count;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mGroup.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mChildrenMap.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.contacts_list_group,
					null);
		}
		TextView groupName = (TextView) convertView
				.findViewById(R.id.group_name);
		groupName.setText(getGroup(groupPosition).toString());
		TextView groupNum = (TextView) convertView
				.findViewById(R.id.group_child_count);
		groupNum.setText("人数：" + getChildrenCount(groupPosition));
		ImageView indicator = (ImageView) convertView
				.findViewById(R.id.group_indicator);
		if (isExpanded) {
			indicator.setImageResource(R.drawable.icon_indicator_expanded);
		} else {
			indicator
					.setImageResource(R.drawable.icon_indicator_unexpanded);
		}
		convertView.setTag(R.id.groupid, groupPosition);
		convertView.setTag(R.id.childid, -1);
		return convertView;

	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		ChildHolder childHolder = null;
		if (convertView == null) {
			childHolder = new ChildHolder();
			convertView = mInflater.inflate(
					R.layout.contacts_list_child_item, null);
			childHolder.avator = (ImageView) convertView
					.findViewById(R.id.contact_item_avator);
			childHolder.nickName = (TextView) convertView
					.findViewById(R.id.contact_item_nickname);
			childHolder.phone = (TextView) convertView
					.findViewById(R.id.contact_item_phone);
			childHolder.state = (ImageView) convertView
					.findViewById(R.id.contact_vertify_level);
			convertView.setTag(R.id.groupid, groupPosition);
			convertView.setTag(R.id.childid, childPosition);
			convertView.setTag(childHolder);
		} else {
			childHolder = (ChildHolder) convertView.getTag();
		}
		UserInfo userInfo = mChildrenMap.get(groupPosition).get(
				childPosition);
		childHolder.nickName.setText(userInfo.getNickName());
	
		childHolder.phone.setText(userInfo.getPhone());
		tempPosition=childPosition;
		return convertView;

	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public int getTreeHeaderState(int groupPosition, int childPosition) {
		return PINNED_HEADER_GONE;
//		final int childCount = getChildrenCount(groupPosition);
//		childPosition=tempPosition;
//		if(childCount<0|| groupPosition<0){
//			return PINNED_HEADER_GONE;
//		}else if (childPosition == childCount - 1) {
//			return PINNED_HEADER_PUSHED_UP;
//		} else if (childPosition == -1
//				&& !mXListView.isGroupExpanded(groupPosition)) {
//			return PINNED_HEADER_GONE;
//		} else {
//			return PINNED_HEADER_VISIBLE;
//		}
	}

	@Override
	public void configureTreeHeader(View header, int groupPosition,
			int childPosition, int alpha) {
		if (groupPosition < 0) {
			return;
		}
//		((TextView) header.findViewById(R.id.group_name))
//				.setText(mGroup.get(groupPosition));
//		((TextView) header.findViewById(R.id.group_child_count))
//				.setText("人数：" + getChildrenCount(groupPosition));
	}

	@Override
	public void onHeadViewClick(int groupPosition, int status) {
		groupStatusMap.put(groupPosition, status);
	}

	@Override
	public int getHeadViewClickStatus(int groupPosition) {
		if (groupStatusMap.containsKey(groupPosition)) {
			return groupStatusMap.get(groupPosition);
		} else {
			return 0;
		}
	}

	/**
	 * @ClassName: ChildHolder
	 * @Descritpion: Child Holder
	 * @author evil
	 * @date May 8, 2014
	 */
	class ChildHolder {
		/**
		 * 头像
		 */
		ImageView avator;
		/**
		 * 姓名
		 */
		TextView nickName;
		/**
		 * 电话
		 */
		TextView phone;
		/**
		 * 认证
		 */
		ImageView state;
	}


}
