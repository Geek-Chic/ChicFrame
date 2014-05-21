/**
 * @Title: CompleteFragment.java
 * @Package com.geekchic.wuyou.ui.main
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 10, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.main;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.geekchic.common.utils.DateUtil;
import com.geekchic.constant.AppAction;
import com.geekchic.framework.ui.BaseFrameFragment;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.bean.Project;
import com.geekchic.wuyou.model.ProjectDao;
import com.geekchic.wuyou.ui.main.view.SwipePullToRefreshView;
import com.widget.swipelistview.SwipeListView;

/**
 * @ClassName: CompleteFragment
 * @Descritpion: 已完成Project列表
 * @author evil
 * @date May 10, 2014
 */
public class CompleteFragment extends BaseFrameFragment {
	/**
	 * 下拉刷新
	 */
	private SwipePullToRefreshView mSwipePullToRefreshView;
	/**
	 * 侧滑列表
	 */
	private SwipeListView mSwipeListView;
	/**
	 * 完成项目
	 */
	private List<Project> mProjects;
	/**
	 * Adapter
	 */
	private CompleteAdapter mCompleteAdapter;
  @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	  super.onCreateView(inflater, container, savedInstanceState);
	  View view = inflater.inflate(R.layout.complete_fragment_layout, null);
		initView(view);
		onDataLoad();
		return view;
}

private void initView(View view) {
	mSwipePullToRefreshView=(SwipePullToRefreshView) view.findViewById(R.id.project_complete_listview);
	mSwipeListView=mSwipePullToRefreshView.getRefreshableView();
}

private void onDataLoad() {
	  mProjects = ProjectDao.getInstance(getActivity()).find();
	  mCompleteAdapter=new CompleteAdapter(getActivity(), mProjects);
	  mSwipeListView.setAdapter(mCompleteAdapter);
}
class CompleteAdapter extends BaseAdapter implements OnClickListener{
	/**
	 * 上下文
	 */
    private Context mContext;
    private List<Project> mProjectList;
    private LayoutInflater inflater;
    public CompleteAdapter(Context context,List<Project> projects){
    	this.mContext=context;
    	this.mProjectList=projects;
    	inflater=LayoutInflater.from(context);
    }
	@Override
	public int getCount() {
		return mProjectList.size();
	}

	@Override
	public Object getItem(int position) {
		return mProjectList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = inflater.inflate(
					R.layout.project_compelte_list_item, null);
			holder.projectNameTextView = (TextView) convertView
					.findViewById(R.id.proejct_going_newname);
			holder.peoplesTextView = (TextView) convertView
					.findViewById(R.id.project_going_people);
			holder.noteTextView = (TextView) convertView
					.findViewById(R.id.project_going_note);
			holder.timeTextView = (TextView) convertView
					.findViewById(R.id.project_going_timer_tv);
			holder.mTimerButton = (ImageButton) convertView
					.findViewById(R.id.project_going_timer);
			holder.mRedoButton = (TextView) convertView
					.findViewById(R.id.project_complete_redo);
			holder.mNotifyButton = (ImageButton) convertView
					.findViewById(R.id.project_going_notify);
            holder.bg=convertView.findViewById(R.id.swipe_project_item_front);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Project project = mProjectList.get(position);
		holder.projectNameTextView.setText(project.getProjectName());
		holder.noteTextView.setText(project.getProjectNote());
		holder.timeTextView.setText(DateUtil.getTime(project.getEndTime()));
		if (project.getPeople().size()!=0) {
			StringBuffer buffer = new StringBuffer();
			for (String people : project.getPeople()) {
				buffer.append(people);
				buffer.append(",");
			}
			buffer.deleteCharAt(buffer.length() - 1);
			holder.peoplesTextView.setText(buffer.toString());
		}
		if(position%2==0){
			   holder.bg.setBackgroundColor(getResources().getColor(R.color.task_add_sub));
		}else {
			   holder.bg.setBackgroundColor(getResources().getColor(R.color.task_add_bg));
		}
		holder.mTimerButton.setOnClickListener(this);
		holder.mNotifyButton.setOnClickListener(this);
		holder.mRedoButton.setOnClickListener(this);
		return convertView;
	}
	class ViewHolder {
		private TextView projectNameTextView;
		private TextView peoplesTextView;
		private TextView noteTextView;
		private TextView timeTextView;
		private ImageButton mTimerButton;
		private ImageButton mNotifyButton;
		private TextView mRedoButton;
		private View bg;
	}
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.project_going_timer) {
			Intent intent = new Intent(AppAction.Timer.ACTION);
			startActivity(intent);
		} else if (v.getId() == R.id.project_going_notify) {
			showShortToast("notify");
		} else if (v.getId() == R.id.project_complete_redo) {
			showShortToast("redo");
		}
	
	}
}
}
