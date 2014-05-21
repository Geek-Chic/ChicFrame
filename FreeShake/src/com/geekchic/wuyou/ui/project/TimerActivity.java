/**
 * @Title: TimerActivity.java
 * @Package com.geekchic.wuyou.ui.main
 * @Description: 任务时间轴
 * @author: evil
 * @date: May 17, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.project;



import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.geekchic.framework.ui.titlebar.BaseTitleBarActivity;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.bean.Timer;
import com.widget.pulltofresh.PullToRefreshListView;

/**
 * @ClassName: TimerActivity
 * @Descritpion: 任务时间轴
 * @author evil
 * @date May 17, 2014
 */
public class TimerActivity extends BaseTitleBarActivity{
	/**
	 * 下拉刷新
	 */
	private PullToRefreshListView mPullToRefreshListView;
	/**
	 * 时间轴
	 */
	private ListView mTimerListView;
	/**
	 * Adatper
	 */
	private TimerAdapter mTimerAdapter;
	private List<Timer> mTimeLists;

	
	private OnClickListener backClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finishActivity();
		}
	};
	@Override
	public int getLayoutId() {
		return R.layout.project_timer_layout;
	}

	@Override
	public boolean initializeTitlBar() {
		setMiddleTitle("任务时间轴");
		setTitleBarBackground(R.color.blue);
		setLeftButton(R.drawable.icon_tab_metra_back_selector, backClickListener);
		return true;
	}
  @Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	initView();
	onDataLoad();
}
  private void initView(){
	  mPullToRefreshListView=(PullToRefreshListView) findViewById(R.id.timer_listview);
	  mTimerListView=mPullToRefreshListView.getRefreshableView();
	 
  }
  private void onDataLoad(){
	  mTimeLists=new ArrayList<Timer>();
	  Timer timer=new Timer();
	  timer.name="毕业";
	  mTimeLists.add(timer);
	  timer.time=System.currentTimeMillis()-20000;
	  Timer timer2=new Timer();
	  timer2.name="旅行";
	  timer2.note="要交毕业论文了";
	  timer2.time=System.currentTimeMillis();
	  mTimeLists.add(timer2);
	  
	  mTimerAdapter=new TimerAdapter(this, mTimeLists);
	  mTimerListView.setAdapter(mTimerAdapter);
  }
}
