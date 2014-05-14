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
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.geekchic.common.log.Logger;
import com.geekchic.common.utils.StringUtil;
import com.geekchic.constant.AppAction;
import com.geekchic.constant.AppActionCode;
import com.geekchic.framework.ui.BaseFrameFragment;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.bean.Person;
import com.geekchic.wuyou.bean.UserInfo;
import com.geekchic.wuyou.logic.contacts.IContactsLogic;
import com.geekchic.wuyou.ui.main.adapter.ContactSearchAdapter;
import com.geekchic.wuyou.ui.main.adapter.ContactsExpandableListAdapter;
import com.geekchic.wuyou.ui.main.view.IphoneTreePullToRefreshView;
import com.widget.pulltofresh.PullToRefreshBase;
import com.widget.pulltofresh.PullToRefreshBase.OnRefreshListener;
import com.widget.slidinglayer.SlidingLayer;
import com.widget.slidinglayer.SlidingLayer.OnInteractListener;
import com.widget.slidingmenu.SlidingMenu;
import com.widget.slidingmenu.SlidingMenu.OnCloseListener;
import com.widget.xlistview.IphoneTreeView;

/**
 * @ClassName: ContactsFragment
 * @Descritpion:联系人
 * @author evil
 * @date May 3, 2014
 */
public class ContactsFragment extends BaseFrameFragment implements
		OnClickListener {
	/**
	 * TAG
	 */
	private static final String TAG="ContactsFragment";
	private static final String[] groups = { "未分组好友", "我的好友", "我的同学", "我的家人",
			"我的同事" };
	private List<String> mGroup;// 组名
	private Map<Integer, List<UserInfo>> mChildren;
	private List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
	private List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
	private LayoutInflater mInflater;
	/**
	 * 本地联系人
	 */
	private ArrayList<Person> mContactArrayList;
	/**
	 * 下拉多级列表
	 */
	private IphoneTreePullToRefreshView mIphoneTreePullToRefreshView;;
	/**
	 * 多级列表
	 */
	private IphoneTreeView xListView;
	/**
	 * 多级列表Adapter
	 */
	private ContactsExpandableListAdapter mContactsExpandableListAdapter;
	/**
	 * 侧滑图层
	 */
	private SlidingLayer mSlidingLayer;
	/**
	 * 查找框
	 */
	private View mSearchBox;
	/**
	 * 查找输入框
	 */
	private EditText mSearchEditText;
	/**
	 * 查找结果List
	 */
	private ListView mSearchResultListView;
	/**
	 * 查找结果Apdater
	 */
	private ContactSearchAdapter mContactSearchAdapter;
	/**
	 * 查找结果值
	 */
	private ArrayList<Person> mSearchList;
	/**
	 * 设置
	 */
	private Button mSettingButton;
	/**
	 * 查找设定
	 */
	private Button mLocalSearchButton;
	/**
	 * 头像ImageView
	 */
	private ImageView mAvatorImageView;
	/**
	 * 姓名地址
	 */
	private TextView mNickNameTextView;
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
			Intent intent = new Intent(AppAction.ChatAction.ACTION);
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
	/**
	 * 监听SlideMenu关闭
	 */
	private OnCloseListener mSlideClosedListener = new OnCloseListener() {

		@Override
		public void onClose() {
			if (null != mSlidingLayer && mSlidingLayer.isOpened()) {
				mSlidingLayer.closeLayer(true);
			}
		}
	};
	/**
	 * SlideLayer状态监听器
	 */
   public OnInteractListener mOnInteractListener=new OnInteractListener() {
	
	@Override
	public void onOpened() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onOpen() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onClosed() {
		mSlidingLayer.removeAllViews();
	}
	
	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}
};
/**
 * 搜索监听
 */
private TextWatcher mSearchTextWatcher=new TextWatcher() {
	
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		 String key=s.toString().trim();
		 if(!StringUtil.isNullOrEmpty(key)){
			 mContactsLogic.searchLocalContacts(key, mContactArrayList);
		 }else {
			mSearchList.clear();
			mContactSearchAdapter.updateListView(mSearchList);
		}
	}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}
};
/**
 * 查找点击
 */
public OnItemClickListener mSearchItemClickListener=new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
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
		mContactsExpandableListAdapter = new ContactsExpandableListAdapter(getActivity(),
				mGroup, mChildren,xListView);
		xListView.setAdapter(mContactsExpandableListAdapter);
		mContactsExpandableListAdapter.notifyDataSetChanged();

		// mSearchBox = view.findViewById(R.id.contact_search_box);
		mSettingButton = (Button) view.findViewById(R.id.contact_act_profile);
		mSettingButton.setOnClickListener(this);
		mLocalSearchButton = (Button) view
				.findViewById(R.id.contact_act_search);
		mLocalSearchButton.setOnClickListener(this);

		mSlidingLayer = (SlidingLayer) view
				.findViewById(R.id.contact_sliding_layer);
		SlidingMenu slidingMenu = ((MainActivity) getActivity())
				.getSlidingMenu();
		slidingMenu.setOnCloseListener(mSlideClosedListener);

		mSearchBox = mInflater.inflate(R.layout.search_local_layout, null);
		mSearchEditText=(EditText) mSearchBox.findViewById(R.id.contact_local_search_edt);
		mSearchEditText.addTextChangedListener(mSearchTextWatcher);
		mSearchResultListView=(ListView) mSearchBox.findViewById(R.id.contact_search_result);
		//初始化查找
		mSearchList=new ArrayList<Person>();
        mContactSearchAdapter=new ContactSearchAdapter(getActivity(), mSearchList);	
        mSearchResultListView.setAdapter(mContactSearchAdapter);
        mSearchResultListView.setOnItemClickListener(mSearchItemClickListener);        
        mAvatorImageView=(ImageView) view.findViewById(R.id.contact_people_avator);
        mAvatorImageView.setOnClickListener(this);
        mNickNameTextView=(TextView) view.findViewById(R.id.contact_header_nickname_loc);
        mNickNameTextView.setText(String.format(getString(R.string.user_info),"蒋鹏",""));
	}
	/**
	 * 设置位置
	 * @param city
	 */
    public void setLoc(String city){
    	 mNickNameTextView.setText(String.format(getString(R.string.user_info),"蒋鹏",city));
    }
	private void onDataLoad(ArrayList<Person> cantacts) {
		mContactArrayList=cantacts;
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
		case AppActionCode.ContactsCode.MESSAGE_CONSTACTS_LOCAL_SEARCH_SUCCESS:
			ArrayList<Person> searchRes=(ArrayList<Person>)msg.obj;
			mContactSearchAdapter.updateListView(searchRes);
			Logger.d(TAG, searchRes.size()+"asdfasdfasdf");
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.contact_act_profile) {
			Intent intent = new Intent(AppAction.SettingAction.ACTION);
			startActivity(intent);
		} else if (v.getId() == R.id.contact_act_search) {
			mSlidingLayer.removeAllViews();
			if (!mSlidingLayer.isOpened()) {
				mSlidingLayer.addView(mSearchBox);
				mSlidingLayer.openLayer(true);
			}
		}else if(v.getId()==R.id.contact_people_avator){
			//跳转至个人资料设置界面
			Intent intent=new Intent(AppAction.ProfileSetting.ACTION);
			startActivity(intent);
		}
	}
}
