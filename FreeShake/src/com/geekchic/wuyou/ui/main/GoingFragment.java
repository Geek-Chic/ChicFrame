/**
 * @Title: ProfileFragment.java
 * @Package com.geekchic.wuyou.ui.main
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 3, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.main;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.geekchic.framework.ui.BaseFrameFragment;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.bean.Project;
import com.geekchic.wuyou.ui.main.view.SwipePullToRefreshView;
import com.widget.pulltofresh.PullToRefreshListView;

/**
 * @ClassName: ProfileFragment
 * @Descritpion: [用一句话描述作用]
 * @author evil
 * @date May 3, 2014
 */
public class GoingFragment extends BaseFrameFragment {
	/**
	 * 下拉刷新
	 */
	private SwipePullToRefreshView mPullToRefreshListView;
	/**
	 * 当前项目列表
	 */
	private ListView mCurProjectListView;
	/**
	 * 项目列表
	 */
	private List<Project> mProjectList;
	/**
	 * Adapter
	 */
	private CurProjectAdapter mCurProjectAdapter;
	/**
	 * 创建ProfileFragment
	 * @return
	 */
	public static GoingFragment newInstance(){
		GoingFragment profileFragment=new GoingFragment();
		return profileFragment;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.profile_fragment_content, null);
		initView(view);
		onDataLoad();
		return view;
	}
	private void initView(View view){
		mPullToRefreshListView=(SwipePullToRefreshView) view.findViewById(R.id.project_current_listview);
		mCurProjectListView=mPullToRefreshListView.getRefreshableView();
		mProjectList=new ArrayList<Project>();
		mCurProjectAdapter=new CurProjectAdapter(getActivity(), mProjectList);
		mCurProjectListView.setAdapter(mCurProjectAdapter);
;	}
	private void onDataLoad(){
		Project project=new Project();
		mProjectList.add(project);
		mCurProjectAdapter.notifyDataSetChanged();
	}
	class CurProjectAdapter extends BaseAdapter{
		private LayoutInflater inflater;
       private List<Project> mProjectsList;
       private Context mContext;
		public CurProjectAdapter(Context context,List<Project> projects) {
			this.mContext=context;
			this.mProjectsList=projects;
			inflater=LayoutInflater.from(mContext);
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
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder=null;
			if(null==convertView){
				holder=new ViewHolder();
				convertView=inflater.inflate(R.layout.project_going_list_item,null);
				holder.projectNameTextView=(TextView) convertView.findViewById(R.id.proejct_item_name);
				
				
			}else {
				holder=(ViewHolder) convertView.getTag();
			}
			return convertView;
		}
		class ViewHolder{
			private TextView projectNameTextView;
			private TextView peoplesTextView;
			private TextView noteTextView;
			private TextView timeTextView;
		}
	}
}
