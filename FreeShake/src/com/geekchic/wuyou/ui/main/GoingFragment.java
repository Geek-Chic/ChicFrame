/**
 * @Title: GoingFragment.java
 * @Package com.geekchic.wuyou.ui.main
 * @Description: 正在进行的项目
 * @author: evil
 * @date: May 3, 2014
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
import com.widget.swipelistview.BaseSwipeListViewListener;
import com.widget.swipelistview.SwipeListView;

/**
 * @ClassName: GoingFragment
 * @Descritpion: 正在进行的项目
 * @author evil
 * @date May 3, 2014
 */
public class GoingFragment extends BaseFrameFragment {
	/**
	 * TAG
	 */
	private static final String TAG = "GoingFragment";
	/**
	 * 下拉刷新
	 */
	private SwipePullToRefreshView mPullToRefreshListView;
	/**
	 * 当前项目列表
	 */
	private SwipeListView mCurProjectListView;
	/**
	 * 项目列表
	 */
	private List<Project> mProjectList;
	/**
	 * Adapter
	 */
	private CurProjectAdapter mCurProjectAdapter;
	/**
	 * 点击跳转
	 */
	private BaseSwipeListViewListener mItemClickListener = new BaseSwipeListViewListener() {
		public void onClickFrontView(int position) {
			// 跳转至群聊,position从1开始
			Project project = mProjectList.get(position - 1);
			Intent chatIntent = new Intent(AppAction.ChatAction.ACTION);
			chatIntent.putExtra("project", project);
			startActivity(chatIntent);
		};
	};

	/**
	 * 创建ProfileFragment
	 * 
	 * @return
	 */
	public static GoingFragment newInstance() {
		GoingFragment profileFragment = new GoingFragment();
		return profileFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.going_fragment_layout, null);
		onDataLoad();
		initView(view);
		return view;
	}

	private void initView(View view) {
		mPullToRefreshListView = (SwipePullToRefreshView) view
				.findViewById(R.id.project_current_listview);
		mCurProjectListView = mPullToRefreshListView.getRefreshableView();
		mCurProjectListView.setClickable(true);
		mCurProjectAdapter = new CurProjectAdapter(getActivity());
		mCurProjectListView.setAdapter(mCurProjectAdapter);
		mCurProjectListView.setSwipeListViewListener(mItemClickListener);
		;
	}

	private void onDataLoad() {
		mProjectList = ProjectDao.getInstance(getActivity()).find();
	}

	/**
	 * @ClassName: CurProjectAdapter
	 * @Descritpion: 当前项目Adapter
	 * @author evil
	 * @date May 17, 2014
	 */
	class CurProjectAdapter extends BaseAdapter implements OnClickListener {
		private LayoutInflater inflater;
		private Context mContext;

		public CurProjectAdapter(Context context) {
			this.mContext = context;
			inflater = LayoutInflater.from(mContext);
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
			ViewHolder holder = null;
			if (null == convertView) {
				holder = new ViewHolder();
				convertView = inflater.inflate(
						R.layout.project_going_list_item, null);
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
				holder.mNewTaskButton = (TextView) convertView
						.findViewById(R.id.project_going_newtask);
				holder.mCompleteButton = (TextView) convertView
						.findViewById(R.id.project_going_complete);
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
			holder.mNewTaskButton.setOnClickListener(this);
			holder.mCompleteButton.setOnClickListener(this);
			return convertView;
		}

		class ViewHolder {
			private TextView projectNameTextView;
			private TextView peoplesTextView;
			private TextView noteTextView;
			private TextView timeTextView;
			private ImageButton mTimerButton;
			private ImageButton mNotifyButton;
			private TextView mNewTaskButton;
			private TextView mCompleteButton;
			private View bg;
		}

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.project_going_timer) {
				Intent intent = new Intent(AppAction.Timer.ACTION);
				startActivity(intent);
			} else if (v.getId() == R.id.project_going_notify) {
				showShortToast("notify");
			} else if (v.getId() == R.id.project_going_newtask) {
				Intent intent=new Intent(AppAction.Task.ACTION);
				startActivity(intent);
			} else if (v.getId() == R.id.project_going_complete) {
				showShortToast("cccc");
			}
		}
	}
	public void startActivityForResult(Intent intent, int requestCode) {
		
	};
}
