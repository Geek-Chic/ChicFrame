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
import com.geekchic.common.utils.StringUtils;
import com.geekchic.constant.AppAction;
import com.geekchic.constant.AppActionCode;
import com.geekchic.framework.ui.BaseFrameFragment;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.bean.Contact;
import com.geekchic.wuyou.logic.contacts.IContactsLogic;
import com.geekchic.wuyou.ui.dialog.FuzzySearchDialog;
import com.geekchic.wuyou.ui.dialog.QuickAction;
import com.geekchic.wuyou.ui.dialog.QuickActionBar;
import com.geekchic.wuyou.ui.dialog.QuickActionWidget;
import com.geekchic.wuyou.ui.dialog.QuickActionWidget.OnQuickActionClickListener;
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
	private static final String TAG = "ContactsFragment";
	private List<String> mGroup;// 组名
	private Map<Integer, List<Contact>> mChildren;
	private LayoutInflater mInflater;
	private QuickActionBar mQuickActionBar;
	private int mLongPressGroupId;
	private int mLongPressChildId;
	/**
	 * 本地联系人
	 */
	private ArrayList<Contact> mContactsArrayList;
	/**
	 * 本地查找联系人
	 */
	private ArrayList<Contact> mSearchContactArrayList;
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
	private ArrayList<Contact> mSearchList;
	/**
	 * 设置
	 */
	private Button mSettingButton;
	/**
	 * 查找设定
	 */
	private Button mLocalSearchButton;
	/**
	 * 联系人管理
	 */
	private Button mContactManagerButton;
	/**
	 * 联系人模糊查找
	 */
	private Button mContactfuzzySearchButton;
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
			ArrayList<Contact> list = new ArrayList<Contact>(10);
			for (Contact contact : mContactsArrayList) {
				mContactsLogic.syncContacts(contact);
			}
			mIphoneTreePullToRefreshView.onRefreshComplete();
			showProgressDialog(R.string.dialog_loading, true);
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
//			BasicDialog basicDialog = new BasicDialog.Builder(getActivity())
//					.setContentView(R.layout.friend_info_dialog)
//					.setTitle("成功获取新好友")
//					.create();
//			basicDialog.show();

			int groupPos = (Integer) view.getTag(R.id.groupid); // 参数值是在setTag时使用的对应资源id号
			int childPos = (Integer) view.getTag(R.id.childid);
			mLongPressGroupId = groupPos;
			mLongPressChildId = childPos;
			if (childPos == -1) {// 长按的是父项
				// 根据groupPos判断你长按的是哪个父项，做相应处理（弹框等）
				showGroupQuickActionBar(view
						.findViewById(R.id.group_indicator));
//				T.showShort(getActivity(), "LongPress group position = "
//						+ groupPos);
			} else {
				// 根据groupPos及childPos判断你长按的是哪个父项下的哪个子项，然后做相应处理。
				// T.showShort(getActivity(), "onClick child position = "
				// + groupPos + ":" + childPos);
				showChildQuickActionBar(view.findViewById(R.id.contact_item_avator));

			}

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
	public OnInteractListener mOnInteractListener = new OnInteractListener() {

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
	private TextWatcher mSearchTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			String key = s.toString().trim();
			if (!StringUtils.isNullOrEmpty(key)) {
				mContactsLogic
						.searchLocalContacts(key, mSearchContactArrayList);
			} else {
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
	public OnItemClickListener mSearchItemClickListener = new OnItemClickListener() {

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
				.findViewById(R.id.contact_pullrefresh_expandlist);
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
		mChildren = new HashMap<Integer, List<Contact>>();
		mContactsExpandableListAdapter = new ContactsExpandableListAdapter(
				getActivity(), mGroup, mChildren, xListView);
		xListView.setAdapter(mContactsExpandableListAdapter);
		mContactsExpandableListAdapter.notifyDataSetChanged();

		// mSearchBox = view.findViewById(R.id.contact_search_box);
		mSettingButton = (Button) view.findViewById(R.id.contact_act_profile);
		mSettingButton.setOnClickListener(this);
		mContactManagerButton = (Button) view
				.findViewById(R.id.contact_act_manager);
		mContactfuzzySearchButton = (Button) view
				.findViewById(R.id.contact_act_fuzzysearch);
		mContactManagerButton.setOnClickListener(this);
		mContactfuzzySearchButton.setOnClickListener(this);
		mLocalSearchButton = (Button) view
				.findViewById(R.id.contact_act_search);
		mLocalSearchButton.setOnClickListener(this);

		mSlidingLayer = (SlidingLayer) view
				.findViewById(R.id.contact_sliding_layer);
		SlidingMenu slidingMenu = ((MainActivity) getActivity())
				.getSlidingMenu();
		slidingMenu.setOnCloseListener(mSlideClosedListener);

		mSearchBox = mInflater.inflate(R.layout.search_local_layout, null);
		mSearchEditText = (EditText) mSearchBox
				.findViewById(R.id.contact_local_search_edt);
		mSearchEditText.addTextChangedListener(mSearchTextWatcher);
		mSearchResultListView = (ListView) mSearchBox
				.findViewById(R.id.contact_search_result);
		// 初始化查找
		mSearchList = new ArrayList<Contact>();
		mContactSearchAdapter = new ContactSearchAdapter(getActivity(),
				mSearchList);
		mSearchResultListView.setAdapter(mContactSearchAdapter);
		mSearchResultListView.setOnItemClickListener(mSearchItemClickListener);
		mAvatorImageView = (ImageView) view
				.findViewById(R.id.contact_people_avator);
		mAvatorImageView.setOnClickListener(this);
		mNickNameTextView = (TextView) view
				.findViewById(R.id.contact_header_nickname_loc);
		mNickNameTextView.setText(String.format(getString(R.string.user_info),
				"蒋鹏", ""));
	}

	/**
	 * 设置位置
	 * 
	 * @param city
	 */
	public void setLoc(String city) {
		city = "武汉";
		mNickNameTextView.setText(String.format(getString(R.string.user_info),
				"蒋鹏", city));
	}

	private void onDataLoad(ArrayList<Contact> contacts) {
		this.mContactsArrayList = contacts;
		initSearchList(contacts);
		initMainList(contacts);
	}
	private void showChildQuickActionBar(View view) {
		mQuickActionBar = new QuickActionBar(getActivity());
		mQuickActionBar.addQuickAction(new QuickAction(getActivity(),
				R.drawable.icon_action_pager, "传呼"));
		mQuickActionBar.addQuickAction(new QuickAction(getActivity(),
				R.drawable.icon_action_orgsearch,"旧寻新"));
		mQuickActionBar.setOnQuickActionClickListener(new OnQuickActionClickListener() {
			public void onQuickActionClicked(QuickActionWidget widget, int position) {
				if (true)
					switch (position) {
					case 0:
						// T.showShort(mApplication, "open");
						break;
					case 1:
//						T.showShort(mApplication, "rename");
						break;
					case 2:
//						T.showShort(mApplication, "move");
						break;
					case 3:
						break;
					default:
						break;
					}
			}
		}
);
		mQuickActionBar.show(view);
	}

	private void showGroupQuickActionBar(View view) {
		mQuickActionBar = new QuickActionBar(getActivity());
		mQuickActionBar.addQuickAction(new QuickAction(getActivity(),
				R.drawable.icon_action_orgsearch, "群同步"));
		mQuickActionBar.setOnQuickActionClickListener(new OnQuickActionClickListener() {

			@Override
			public void onQuickActionClicked(QuickActionWidget widget,
					int position) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
//					T.showShort(getActivity(), "rename group "
//							+ mLongPressGroupId);
					break;
				case 1:
//					T.showShort(getActivity(), "delete group "
//							+ mLongPressGroupId);
					break;
				default:
					break;
				}
			}
		});
		mQuickActionBar.show(view);
	}
	private void initSearchList(ArrayList<Contact> contacts) {
		mSearchContactArrayList = new ArrayList<Contact>();
		for (Contact contact : contacts) {
			for (String phone : contact.phone) {
				Contact search = new Contact();
				search.pY = contact.pY;
				search.fisrtSpell = contact.fisrtSpell;
				search.searchPhone = phone;
				search.name = contact.name;
				mSearchContactArrayList.add(search);
			}
		}
	}

	private void initMainList(ArrayList<Contact> contacts) {
		mGroup.add("默认分组");
		mChildren.put(0, new ArrayList<Contact>());
		for (Contact person : contacts) {
			if (person.groups.size() > 0) {
				for (String groupName : person.groups) {
					int gIndex = mGroup.indexOf(groupName);
					person.listGroup = groupName;
					if (gIndex < 0) {
						mGroup.add(groupName);
						ArrayList<Contact> list = new ArrayList<Contact>();
						list.add(person);
						mChildren.put(mGroup.size() - 1, list);
					} else {
						mChildren.get(gIndex).add(person);

					}
				}
			} else {
				mChildren.get(0).add(person);
			}
		}
//		mChildren.get(0).get(1).status = 1;
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
			ArrayList<Contact> contacts = (ArrayList<Contact>) msg.obj;
			onDataLoad(contacts);
			break;
		case AppActionCode.ContactsCode.MESSAGE_CONSTACTS_LOCAL_SEARCH_SUCCESS:
			ArrayList<Contact> searchRes = (ArrayList<Contact>) msg.obj;
			mContactSearchAdapter.updateListView(searchRes);
			Logger.d(TAG, searchRes.size() + "asdfasdfasdf");
			break;
		case AppActionCode.ContactsCode.MESSAGE_CONTACTS_SYNC_SUCCESS:
			closeProgressDialog();
			break;
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
		} else if (v.getId() == R.id.contact_people_avator) {
			// 跳转至个人资料设置界面
			Intent intent = new Intent(AppAction.ProfileSetting.ACTION);
			startActivity(intent);
		} else if (v.getId() == R.id.contact_act_fuzzysearch) {
			FuzzySearchDialog fuzzySearchDialog = new FuzzySearchDialog(
					getActivity());
			fuzzySearchDialog = fuzzySearchDialog.create();
			fuzzySearchDialog.show();
		} else if (v.getId() == R.id.contact_act_manager) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.setType("vnd.android.cursor.item/phone");
			startActivity(intent);
		}
	}
}
