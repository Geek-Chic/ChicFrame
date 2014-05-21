/**
 * @Title: PeopleAddDialog.java
 * @Package com.geekchic.wuyou.ui.dialog
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 20, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.dialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.geekchic.framework.ui.dialog.BasicDialog;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.bean.Contact;

/**
 * @ClassName: PeopleAddDialog
 * @Descritpion: 添加用户对话框
 * @author evil
 * @date May 20, 2014
 */
public class PeopleAddDialog implements OnClickListener,OnItemClickListener{
	/**
	 * 上下文
	 */
	private Context mContext;
	/**
	 * 联系人列表
	 */
    private List<Contact> mContacts;
    /**
     * 对话框
     */
    private BasicDialog mBasicDialog;
    /**
     * 对话框View
     */
    private View mRootView;
    /**
     * 联系人列表 
     */
    private ListView mContactListView;
    /**
     * Adapter
     */
    private ContactsDeleteAdapter mContactsDeleteAdapter;
    /**
     * 选择确认
     */
    private Button mConfrimButton;
    /**
     * 取消
     */
    private Button mCancelButton;
    
    private CheckBox mSelectAllBox;
	private final class ContactViewHolder {
		ImageView markImageView;
		TextView nameView;
		CheckBox selectCheckBox;
	}
	public PeopleAddDialog(Context context,List<Contact> contacts) {
		this.mContext=context;
		this.mContacts=contacts;
		initView();
	}
	private void initView(){
		LayoutInflater layoutInflater=LayoutInflater.from(mContext);
		mRootView=layoutInflater.inflate(R.layout.project_contact_select,null);
		mConfrimButton=(Button) mRootView.findViewById(R.id.contact_select_confirm);
		mCancelButton=(Button) mRootView.findViewById(R.id.contact_select_cancel);
		mContactListView=(ListView) mRootView.findViewById(R.id.contact_select_listview);
		mSelectAllBox=(CheckBox) mRootView.findViewById(R.id.contact_select_all);
		mConfrimButton.setOnClickListener(this);
		mCancelButton.setOnClickListener(this);
		mContactListView.setOnItemClickListener(this);
		mSelectAllBox.setOnClickListener(this);
	}
	public PeopleAddDialog create(){
		mBasicDialog=new BasicDialog(mContext,R.style.AppDialog);
		mBasicDialog.setContentView(mRootView);
		mContactsDeleteAdapter=new ContactsDeleteAdapter(mContext, mContacts);
		mContactListView.setAdapter(mContactsDeleteAdapter);
		return this;
	}
	/**
	 * 显示对话框
	 */
	public void show(){
		mBasicDialog.show();
	}
	/**
	 * 关闭对话框
	 */
	public void dismiss(){
		mBasicDialog.dismiss();
	}
	/**
	 * @ClassName: ContactsDeleteAdapter
	 * @Descritpion: 联系人多选Adapter
	 * @author evil
	 * @date May 21, 2014
	 */
	class ContactsDeleteAdapter extends BaseAdapter {
		Map<Integer, Boolean> selectedMap;
        List<Contact> mContacts;
		public ContactsDeleteAdapter(Context context, List<Contact> conctacts) {
			this.mContacts=conctacts;
			// 保存每条记录是否被选中的状态
			selectedMap = new HashMap<Integer, Boolean>();

			for (int i = 0; i < mContacts.size(); i++) {
				selectedMap.put(i, false);
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext)
						.inflate(R.layout.project_contact_select_item, null);
				ContactViewHolder holderViews = new ContactViewHolder();
				holderViews.nameView = (TextView) convertView
						.findViewById(R.id.project_contact_name);
				holderViews.selectCheckBox = (CheckBox) convertView
						.findViewById(R.id.project_select_ckb);
				convertView.setTag(holderViews);
			}
			ContactViewHolder views = (ContactViewHolder) convertView
					.getTag();
			final String name = mContacts.get(position).name;
			views.nameView.setText(name);
			views.selectCheckBox.setChecked(selectedMap.get(position));
			return convertView;
		}

		@Override
		public int getCount() {
			return mContacts.size();
		}

		@Override
		public Object getItem(int position) {
			return mContacts.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.contact_select_confirm){
			
		}else if(v.getId()==R.id.contact_select_cancel){
			dismiss();
		}else if(v.getId()==R.id.contact_select_all){
			if (mSelectAllBox.isChecked()) {
				for (int i = 0; i < mContactsDeleteAdapter.getCount(); i++) {
					mContactsDeleteAdapter.selectedMap.put(i, true);
				}
				mConfrimButton.setEnabled(true);
			} else {
				for (int i = 0; i < mContactsDeleteAdapter.getCount(); i++) {
					mContactsDeleteAdapter.selectedMap.put(i, false);
				}
				//click事件：全选checkbox被取消勾选则把delContactsIdSet清空
				mConfrimButton.setEnabled(false);
			}
			mContactsDeleteAdapter.notifyDataSetChanged();
		}
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ContactViewHolder viewHolder = (ContactViewHolder) view
				.getTag();
		viewHolder.selectCheckBox.toggle();
		mContactsDeleteAdapter.selectedMap.put(position, viewHolder.selectCheckBox
				.isChecked());
		mContactsDeleteAdapter.notifyDataSetChanged();
		// 判断是否有记录没被选中，以便修改全选CheckBox勾选状态
		if (mContactsDeleteAdapter.selectedMap.containsValue(false)) {
			mSelectAllBox.setChecked(false);
		} else {
			mSelectAllBox.setChecked(true);
		}
		// 判断是否有记录被选中，以便设置删除按钮是否可用
		if (mContactsDeleteAdapter.selectedMap.containsValue(true)) {
			mConfrimButton.setEnabled(true);
		} else {
			mConfrimButton.setEnabled(false);
		}
	
	}
}
