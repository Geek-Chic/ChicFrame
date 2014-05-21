/**
 * @Title: NewTaskActivity.java
 * @Package com.geekchic.wuyou.ui.task
 * @Description: 创建项目
 * @author: evil
 * @date: May 10, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.project;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.geekchic.common.utils.DateUtil;
import com.geekchic.common.utils.StringUtil;
import com.geekchic.constant.AppActionCode;
import com.geekchic.framework.ui.titlebar.BaseTitleBarActivity;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.bean.Contact;
import com.geekchic.wuyou.bean.Project;
import com.geekchic.wuyou.logic.contacts.IContactsLogic;
import com.geekchic.wuyou.model.ProjectDao;
import com.geekchic.wuyou.ui.dialog.PeopleAddDialog;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

/**
 * @ClassName: NewProjectActivity
 * @Descritpion: 创建项目
 * @author evil
 * @date May 10, 2014
 */
public class NewProjectActivity extends BaseTitleBarActivity implements
		OnDateSetListener, TimePickerDialog.OnTimeSetListener, OnClickListener {
	public static final String DATEPICKER_TAG = "datepicker";
	public static final String TIMEPICKER_TAG = "timepicker";
	final Calendar calendar = Calendar.getInstance();

	final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
			this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), false);
	final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
			this, calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE), false, false);

	/**
	 * 项目名
	 */
	private EditText mProjectName;
	/**
	 * 项目说明
	 */
	private EditText mProjectNote;
	/**
	 * 人员配置
	 */
	private TextView mPeopleTextView;
	/**
	 * 开始时间
	 */
	private TextView mStartTimeTextView;
	/**
	 * 结束时间
	 */
	private TextView mEndTimeTextView;
	/**
	 * 开始日期
	 */
	private TextView mStartDateTextView;
	/**
	 * 结束日期
	 */
	private TextView mEndDateTextView;
	/**
	 * 项目优先级
	 */
	private ImageButton mLevelButton;
	/**
	 * 优先级
	 */
	private int mLevel = 0;
	/**
	 * 项目实体
	 */
	private Project project;
	/**
	 * 开始时间
	 */
	private Calendar mStartDate;
	/**
	 * 结束时间
	 */
	private Calendar mEndDate;
	/**
	 * 当前时间选择的时开始时间还是结束时间
	 */
	private boolean isSelectStartTime;
	/**
	 * 当前Project
	 */
	private Project mProject;
	/**
	 * 联系人List
	 */
	private ArrayList<Contact> mContacts;
	/**
	 * 联系人获取
	 */
	private IContactsLogic mContactsLogic;
	/**
	 * 后退
	 */
	private OnClickListener mBackClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			finishActivity();
		}
	};
	private OnClickListener mCreateClickLinstener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if(checkInfo()){
				mProject=new Project();
				mProject.setEndTime(mEndDate.getTimeInMillis());
				mProject.setLevel(mLevel);
				mProject.setProjectName(mProjectName.getText().toString());
				mProject.setProjectNote(mProjectNote.getText().toString().trim());
				mProject.setStartTime(mStartDate.getTimeInMillis());
				ProjectDao.getInstance(NewProjectActivity.this).insert(mProject);
				finishActivity();
			}
		}
	};

	private boolean checkInfo() {
		long startTime = mStartDate.getTimeInMillis();
		long endTime = mEndDate.getTimeInMillis();
		if (endTime - startTime <= 0) {
			showShortToast("结束时间不能早于开始时间");
			return false;
		}
		String projectName=mProjectName.getText().toString();
		if(StringUtil.isNullOrEmpty(projectName)){
			showShortToast("任务名不能为空");
			return false;
		}
		return true;
	}

	@Override
	public int getLayoutId() {
		return R.layout.project_new;
	}

	@Override
	public boolean initializeTitlBar() {
		setMiddleTitle(R.string.project_create_title);
		setLeftButton(R.drawable.icon_tab_metra_back_selector,
				mBackClickListener);
		setRightButton(R.drawable.icon_tab_metra_add_selector,
				mCreateClickLinstener);
		setTitleBarBackground(R.color.blue);
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		if (savedInstanceState != null) {
			DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager()
					.findFragmentByTag(DATEPICKER_TAG);
			if (dpd != null) {
				dpd.setOnDateSetListener(this);
			}

			TimePickerDialog tpd = (TimePickerDialog) getSupportFragmentManager()
					.findFragmentByTag(TIMEPICKER_TAG);
			if (tpd != null) {
				tpd.setOnTimeSetListener(this);
			}
		}
		mContactsLogic.getContactsFromProvider();
	}

	private void initView() {
		mProjectName=(EditText) findViewById(R.id.project_new_name);
		mProjectNote=(EditText) findViewById(R.id.project_new_note);
		mStartTimeTextView = (TextView) findViewById(R.id.project_new_starttime);
		mEndTimeTextView = (TextView) findViewById(R.id.project_new_endtime);
		mStartDateTextView = (TextView) findViewById(R.id.project_new_startdate);
		mEndDateTextView = (TextView) findViewById(R.id.project_new_enddate);
		mLevelButton = (ImageButton) findViewById(R.id.project_new_levell);
		mPeopleTextView=(TextView) findViewById(R.id.project_pepole_new);
		mPeopleTextView.setOnClickListener(this);
		mStartTimeTextView.setOnClickListener(this);
		mStartDateTextView.setOnClickListener(this);
		mEndTimeTextView.setOnClickListener(this);
		mEndDateTextView.setOnClickListener(this);
		mLevelButton.setOnClickListener(this);
		mStartDateTextView.setText(DateUtil.getDateStr(System
				.currentTimeMillis()));
		mStartTimeTextView.setText(DateUtil.getHourAndMin(System
				.currentTimeMillis()));
		mEndDateTextView
				.setText(DateUtil.getDateStr(System.currentTimeMillis()));
		mEndTimeTextView.setText(DateUtil.getHourAndMin(System
				.currentTimeMillis()));
		mStartDate = Calendar.getInstance();
		mEndDate = Calendar.getInstance();

	}
     @Override
    protected void initLogics() {
    	super.initLogics();
    	mContactsLogic=(IContactsLogic) getLogic(IContactsLogic.class);
    }
     @Override
    protected void handleStateMessage(Message msg) {
    	super.handleStateMessage(msg);
    	switch (msg.what) {
		case AppActionCode.ContactsCode.MESSAGE_CONSTACTS_PROVIDE_SUCCESS:
			mContacts = (ArrayList<Contact>) msg.obj;
			break;
			default:
				break;
    	}
    }

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
		if (isSelectStartTime) {
			mStartDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
			mStartDate.set(Calendar.MINUTE, minute);
			mStartTimeTextView.setText(DateUtil.getHourAndMin(mStartDate
					.getTimeInMillis()));
		} else {
			mEndDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
			mEndDate.set(Calendar.MINUTE, minute);
			mEndTimeTextView.setText(DateUtil.getHourAndMin(mEndDate
					.getTimeInMillis()));
		}
	}

	@Override
	public void onDateSet(DatePickerDialog datePickerDialog, int year,
			int month, int day) {
		if (isSelectStartTime) {
			mStartDate.set(Calendar.YEAR, year);
			mStartDate.set(Calendar.MONTH, month);
			mStartDate.set(Calendar.DAY_OF_MONTH, day);
			mStartDateTextView.setText(DateUtil.getDateStr(mStartDate
					.getTimeInMillis()));
		} else {
			mEndDate.set(Calendar.YEAR, year);
			mEndDate.set(Calendar.MONTH, month);
			mEndDate.set(Calendar.DAY_OF_MONTH, day);
			mEndDateTextView.setText(DateUtil.getDateStr(mEndDate
					.getTimeInMillis()));
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.project_new_starttime) {
			isSelectStartTime = true;
			showTime();
		} else if (v.getId() == R.id.project_new_endtime) {
			isSelectStartTime = false;
			showTime();
		} else if (v.getId() == R.id.project_new_enddate) {
			isSelectStartTime = false;
			showDate();
		} else if (v.getId() == R.id.project_new_startdate) {
			isSelectStartTime = true;
			showDate();
		} else if (v.getId() == R.id.project_new_levell) {
			mLevel = (mLevel + 1) % 3;
			changeLevel();
		}else if(v.getId()==R.id.project_pepole_new){
			if(null!=mContacts){
				PeopleAddDialog dialog=new PeopleAddDialog(this, mContacts);
				dialog=dialog.create();
				dialog.show();
			}
		}
	}

	private void showDate() {
		datePickerDialog.setYearRange(1985, 2028);
		datePickerDialog.setCloseOnSingleTapDay(false);
		datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
	}

	private void showTime() {
		timePickerDialog.setCloseOnSingleTapMinute(false);
		timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
	}

	/**
	 * 改变等级
	 */
	private void changeLevel() {
		if (mLevel == LEVEL_LOW) {
			mLevelButton.setImageResource(R.drawable.icon_red_flag);
		} else if (mLevel == LEVEL_MIDDLE) {
			mLevelButton.setImageResource(R.drawable.icon_red_flag_two);
		} else {
			mLevelButton.setImageResource(R.drawable.icon_red_flag_three);
		}
	}

	public static final int LEVEL_LOW = 0;
	public static final int LEVEL_MIDDLE = 1;
	public static final int LEVEL_HIGH = 2;
}
