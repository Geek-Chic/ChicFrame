/**
 * @Title: TimerAdapter.java
 * @Package com.geekchic.wuyou.ui.main.adapter
 * @Description: 任务时间轴Adapter 
 * @author: evil
 * @date: May 17, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.project;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geekchic.common.utils.DateUtil;
import com.geekchic.common.utils.StringUtil;
import com.geekchic.constant.AppAction.Timer;
import com.geekchic.wuyou.R;

/**
 * @ClassName: TimerAdapter
 * @Descritpion: 任务时间轴Adapter 
 * @author evil
 * @date May 17, 2014
 */
public class TimerAdapter extends BaseAdapter {
	/**
	 * 上下文
	 */
  private Context mContext;
  /**
   * 时间List
   */
  private List<com.geekchic.wuyou.bean.Timer> timerList;
  /**
   * TimerAdapter构造函数
   * @param context
   */
  public TimerAdapter(Context context,List<com.geekchic.wuyou.bean.Timer> mTimeLists){
	  this.mContext=context;
	  this.timerList=mTimeLists;
  }
	@Override
	public int getCount() {
		return timerList.size();
	}

	@Override
	public Object getItem(int position) {
		return timerList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TimerViewHolder viewHolder=null;
		if(convertView==null){
			viewHolder=new TimerViewHolder();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.project_timer_item, null);
			viewHolder.mNameTextView=(TextView) convertView.findViewById(R.id.time_task_name);
			viewHolder.mTimeTextView=(TextView)convertView.findViewById(R.id.time_task_time);
			viewHolder.mNoteTextView=(TextView)convertView.findViewById(R.id.time_task_note);
		}else {
	        viewHolder=(TimerViewHolder) convertView.getTag();
		}
		com.geekchic.wuyou.bean.Timer timer=timerList.get(position);
		viewHolder.mNameTextView.setText(timer.name);
		viewHolder.mTimeTextView.setText(DateUtil.getTime(timer.time));
		if(!StringUtil.isNullOrEmpty(timer.note)){
			viewHolder.mNoteTextView.setText(timer.note);
		}
		return convertView;
	}
    class TimerViewHolder{
    	/**
    	 * Task名
    	 */
    	public TextView mNameTextView;
    	/**
    	 * 说明 
    	 */
    	public TextView mNoteTextView;
    	public TextView mTimeTextView;
    }
}
