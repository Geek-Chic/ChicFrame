/**
 * @Title: ContactSearchAdapter.java
 * @Package com.geekchic.wuyou.ui.main.adapter
 * @Description: 查找显示ListView的Adapter
 * @author: evil
 * @date: May 11, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.main.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geekchic.wuyou.R;
import com.geekchic.wuyou.bean.Person;

/**
 * @ClassName: ContactSearchAdapter
 * @Descritpion: 查找显示ListView的Adapter
 * @author evil
 * @date May 11, 2014
 */
public class ContactSearchAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<Person> persons;
	private Context context;

	public ContactSearchAdapter(Context context, ArrayList<Person> persons) {
		this.mInflater = LayoutInflater.from(context);
		this.persons = persons;
		this.context = context;
	}

	// 当联系人列表数据发生变化时,用此方法来更新列表
	public void updateListView(ArrayList<Person> persons) {
		this.persons = persons;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return persons.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return persons.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.search_local_item, null);
		}

		if (persons.isEmpty()) {
			return convertView;
		}

		TextView name = (TextView) convertView
				.findViewById(R.id.search_contacts_name);
		name.setText(persons.get(position).name);

		TextView number = (TextView) convertView
				.findViewById(R.id.search_contacts_number);
		number.setText(persons.get(position).phone);

		// 字母提示textview的显示
		TextView letterTag = (TextView) convertView
				.findViewById(R.id.search_item_LetterTag);
		// 获得当前姓名的拼音首字母
		String firstLetter = persons.get(position).pY.substring(0, 1)
				.toUpperCase();

		// 如果是第1个联系人 那么letterTag始终要显示
		if (position == 0) {
			letterTag.setVisibility(View.VISIBLE);
			letterTag.setText(firstLetter);
		} else {
			// 获得上一个姓名的拼音首字母
			String firstLetterPre = persons.get(position - 1).pY
					.substring(0, 1).toUpperCase();
			// 比较一下两者是否相同
			if (firstLetter.equals(firstLetterPre)) {
				letterTag.setVisibility(View.GONE);
			} else {
				letterTag.setVisibility(View.VISIBLE);
				letterTag.setText(firstLetter);
			}
		}

		return convertView;
	}

}
