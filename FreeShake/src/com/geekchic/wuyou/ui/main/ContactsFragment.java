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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekchic.constant.AppAction;
import com.geekchic.constant.AppActionCode;
import com.geekchic.framework.ui.BaseFrameFragment;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.bean.Person;
import com.geekchic.wuyou.bean.UserInfo;
import com.geekchic.wuyou.logic.contacts.IContactsLogic;
import com.geekchic.wuyou.ui.main.view.IphoneTreePullToRefreshView;
import com.widget.pulltofresh.PullToRefreshBase;
import com.widget.pulltofresh.PullToRefreshBase.OnRefreshListener;
import com.widget.xlistview.IphoneTreeView;
import com.widget.xlistview.IphoneTreeView.IphoneTreeHeaderAdapter;

/**
 * @ClassName: ContactsFragment
 * @Descritpion:联系人
 * @author evil
 * @date May 3, 2014
 */
public class ContactsFragment extends BaseFrameFragment implements
		OnClickListener {
	private static final String[] groups = { "未分组好友", "我的好友", "我的同学", "我的家人",
			"我的同事" };
	private List<String> mGroup;// 组名
	private Map<Integer, List<UserInfo>> mChildren;
	private List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
	private List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
	private LayoutInflater mInflater;
	private IphoneTreePullToRefreshView mIphoneTreePullToRefreshView;;
	private IphoneTreeView xListView;
	private ContactsExpandableListAdapter mContactsExpandableListAdapter;
	/**
	 * 查找框
	 */
	private View mSearchBox;
	/**
	 * 查找输入框
	 */
	private EditText mSearchEditText;
	/**
	 * 设置
	 */
	private Button mSettingButton;
	/**
	 * 查找设定
	 */
	private CheckBox mCheckBox;
	/**
	 * 联系人Logic
	 */
	private IContactsLogic mContactsLogic;
	/**
	 * 列表刷新监听器
	 */
	private OnRefreshListener<IphoneTreeView> listOnRefreshListener = new OnRefreshListener<IphoneTreeView>() {

		@Override
		public void onRefresh(PullToRefreshBase<IphoneTreeView> refreshView) {
			mIphoneTreePullToRefreshView.onRefreshComplete();

		}
	};
	/**
	 * 列表项点击Click
	 */
	private OnChildClickListener mContactChildClickListener = new OnChildClickListener() {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			Intent intent=new Intent(AppAction.ChatAction.ACTION);
			startActivity(intent);
			return false;
		}
	};
	/**
	 * 列表子项长按
	 */
	private OnItemLongClickListener mOnItemLongClickListener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			return false;
		}
	};
	private OnCheckedChangeListener mSearchChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {
				mSearchBox.setVisibility(View.VISIBLE);
			} else {
				mSearchBox.setVisibility(View.GONE);
			}
		}
	};

	public static ContactsFragment newInstance() {
		ContactsFragment fragment = new ContactsFragment();
		// Bundle args = new Bundle();
		// args.putString(EXTRA_KEY_SHOP_ID, shopId);
		// fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.main_left_fragment, null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		mInflater = LayoutInflater.from(getActivity());
		mIphoneTreePullToRefreshView = (IphoneTreePullToRefreshView) view
				.findViewById(R.id.contact_pull_refresh_expandable_list);
		mIphoneTreePullToRefreshView
				.setOnRefreshListener(listOnRefreshListener);
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		xListView = mIphoneTreePullToRefreshView.getRefreshableView();
		xListView.setBackgroundColor(getResources().getColor(
				android.R.color.transparent));
		xListView.setCacheColorHint(getResources().getColor(
				android.R.color.transparent));
		xListView.setDivider(null);
		xListView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_NORMAL);
		// 去掉原生按钮
		xListView.setGroupIndicator(null);
		// 展开显示第一条
		xListView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);
		// 隐藏ScrollBar
		xListView.setVerticalScrollBarEnabled(false);
		Drawable seperator = getResources().getDrawable(
				R.drawable.line_seperator);
		xListView.setDivider(seperator);
		xListView.setDividerHeight(1);
		xListView.setHeaderView(inflater.inflate(R.layout.contacts_list_group,
				xListView, false));
		xListView.setOnChildClickListener(mContactChildClickListener);
		xListView.setOnItemLongClickListener(mOnItemLongClickListener);
		mContactsLogic.getContactsFromProvider();

		mGroup = new ArrayList<String>();
		mChildren = new HashMap<Integer, List<UserInfo>>();
		mContactsExpandableListAdapter = new ContactsExpandableListAdapter(
				mGroup, mChildren);
		xListView.setAdapter(mContactsExpandableListAdapter);
		mContactsExpandableListAdapter.notifyDataSetChanged();

		mSearchBox = view.findViewById(R.id.contact_search_box);
		mSettingButton = (Button) view.findViewById(R.id.contact_act_profile);
		mSettingButton.setOnClickListener(this);
		mCheckBox = (CheckBox) view.findViewById(R.id.contact_act_search);
		mCheckBox.setOnCheckedChangeListener(mSearchChangeListener);

	}

	private void onDataLoad(List<Person> cantacts) {
		mGroup.add("未分组");
		List<UserInfo> list = new ArrayList<UserInfo>();
		for (Person person : cantacts) {
			UserInfo userInfo = new UserInfo();
			userInfo.setNickName(person.name);
			userInfo.setPhone(person.phone);
			list.add(userInfo);
		}
		mChildren.put(0, list);
		mContactsExpandableListAdapter.notifyDataSetChanged();
	}

	@Override
	protected void initLogics() {
		super.initLogics();
		mContactsLogic = (IContactsLogic) getLogicByInterfaceClass(IContactsLogic.class);
	}

	@Override
	protected void handleStateMessage(Message msg) {
		super.handleStateMessage(msg);
		switch (msg.what) {
		case AppActionCode.ContactsCode.MESSAGE_CONSTACTS_PROVIDE_SUCCESS:
			ArrayList<Person> contacts = (ArrayList<Person>) msg.obj;
			onDataLoad(contacts);
			break;

		default:
			break;
		}
	}

	/**
	 * @ClassName: ContactsExpandableListAdapter
	 * @Descritpion: 联系人Adapter
	 * @author evil
	 * @date May 8, 2014
	 */
	public class ContactsExpandableListAdapter extends
			BaseExpandableListAdapter implements IphoneTreeHeaderAdapter {
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
		 * ContactsExpandableListAdapter构造函数
		 * 
		 * @param group
		 * @param children
		 */
		public ContactsExpandableListAdapter(List<String> group,
				Map<Integer, List<UserInfo>> children) {
			this.mGroup = group;
			this.mChildrenMap = children;
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
			int count = 0;
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
				UserInfo userInfo = mChildrenMap.get(groupPosition).get(
						childPosition);
				childHolder.nickName.setText(userInfo.getNickName());
				childHolder.phone = (TextView) convertView
						.findViewById(R.id.contact_item_phone);
				childHolder.phone.setText(userInfo.getPhone());
				childHolder.state = (ImageView) convertView
						.findViewById(R.id.contact_vertify_level);
				convertView.setTag(R.id.groupid, groupPosition);
				convertView.setTag(R.id.childid, childPosition);
				convertView.setTag(childHolder);
			} else {
				childHolder = (ChildHolder) convertView.getTag();
			}

			return convertView;

		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		@Override
		public int getTreeHeaderState(int groupPosition, int childPosition) {
			final int childCount = getChildrenCount(groupPosition);
			if (childPosition == childCount - 1) {
				return PINNED_HEADER_PUSHED_UP;
			} else if (childPosition == -1
					&& !xListView.isGroupExpanded(groupPosition)) {
				return PINNED_HEADER_GONE;
			} else {
				return PINNED_HEADER_VISIBLE;
			}
		}

		@Override
		public void configureTreeHeader(View header, int groupPosition,
				int childPosition, int alpha) {
			if (groupPosition < 0) {
				return;
			}
			((TextView) header.findViewById(R.id.group_name))
					.setText(groups[groupPosition]);
			((TextView) header.findViewById(R.id.group_child_count))
					.setText("人数：" + getChildrenCount(groupPosition));
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

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.contact_act_profile) {
			Intent intent = new Intent(AppAction.SettingAction.ACTION);
			startActivity(intent);
		}
	}
}
