/**
 * @Title: ContactsFragment.java
 * @Package com.geekchic.wuyou.ui.main
 * @Description: 联系人
 * @author: evil
 * @date: May 3, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekchic.framework.ui.titlebar.BaseTitleBarFragment;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.bean.UserInfo;
import com.widget.pulltofresh.PullToRefreshBase;
import com.widget.pulltofresh.PullToRefreshBase.OnRefreshListener;
import com.widget.pulltofresh.PullToRefreshExpandableListView;
import com.widget.xlistview.IphoneTreeView;
import com.widget.xlistview.IphoneTreeView.IphoneTreeHeaderAdapter;

/**
 * @ClassName: ContactsFragment
 * @Descritpion:联系人
 * @author evil
 * @date May 3, 2014
 */
public class ContactsFragment extends BaseTitleBarFragment {
	private static final String[] groups = { "未分组好友", "我的好友", "我的同学", "我的家人",
	"我的同事" };
	private List<String> mGroup;// 组名
	private Map<Integer, List<UserInfo>> mChildren;
	private List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
	private List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();

	private PullToRefreshExpandableListView mPullRefreshListView;
	private IphoneTreeView xListView;
	private OnRefreshListener<ExpandableListView> listOnRefreshListener=new OnRefreshListener<ExpandableListView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
			// TODO Auto-generated method stub
			
		}
	};
	@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View view=super.onCreateView(inflater, container, savedInstanceState);
		mPullRefreshListView=(PullToRefreshExpandableListView) view.findViewById(R.id.pull_refresh_expandable_list);
	return view;
}

	@Override
	public int getLayoutId() {
		return R.layout.main_left_fragment;
	}

	@Override
	public boolean initializeTitlBar() {
		setMiddleTitle("联系人");
		return true;
	}
	public class ContactsExpandableListAdapter extends BaseExpandableListAdapter implements IphoneTreeHeaderAdapter{
		/**
		 * 用户组
		 */
         private List<String> mGroup;
         /**
          * 用户组成员
          */
         private Map<Integer,List<UserInfo>> mChildrenMap;
         /**
          * 上下文
          */
         private Context mContext;
         /**
          * ContactsExpandableListAdapter构造函数
          * @param group
          * @param children
          */
         public ContactsExpandableListAdapter(List<String> group,Map<Integer,List<UserInfo>> children){
        	 this.mGroup=group;
        	 this.mChildrenMap=children;
         }
         public void addUser(UserInfo userInfo){
        	 int groupID=userInfo.getGroup();
        	 if(mChildrenMap.containsKey(groupID)){
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
			// TODO Auto-generated method stub
			return mChildrenMap.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
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
				LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(
						R.layout.contacts_list_group, null);
			}
			TextView groupName = (TextView) convertView
					.findViewById(R.id.group_name);
			groupName.setText(getGroup(groupPosition).toString());
			TextView onlineNum = (TextView) convertView
					.findViewById(R.id.group_online_count);
			onlineNum.setText(getChildrenCount(groupPosition) + "/"
					+ getChildrenCount(groupPosition));
			ImageView indicator = (ImageView) convertView
					.findViewById(R.id.group_indicator);
			if (isExpanded){
				indicator.setImageResource(R.drawable.icon_indicator_expanded);
			}
			else{
				indicator.setImageResource(R.drawable.icon_indicator_unexpanded);
			}
			convertView.setTag(R.id.groupid, groupPosition);
			convertView.setTag(R.id.childid, -1);
			return convertView;
	
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {

			if (convertView == null) {
				LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(
						R.layout.contacts_list_child_item, null);
			}
		
		return null;
	
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getTreeHeaderState(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void configureTreeHeader(View header, int groupPosition,
				int childPosition, int alpha) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onHeadViewClick(int groupPosition, int status) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getHeadViewClickStatus(int groupPosition) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
}
