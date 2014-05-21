/**
 * @Title: NewTaskActivity.java
 * @Package com.geekchic.wuyou.ui.project
 * @Description: 新建Task
 * @author: evil
 * @date: May 19, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.project;

import java.util.Calendar;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.geekchic.common.utils.DateUtil;
import com.geekchic.framework.ui.titlebar.BaseTitleBarActivity;
import com.geekchic.wuyou.R;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

/**
 * @ClassName: NewTaskActivity
 * @Descritpion: 新建Task
 * @author evil
 * @date May 19, 2014
 */
public class NewTaskActivity extends BaseTitleBarActivity implements
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
	 * 任务名
	 */
	private EditText mNameEditText;
	/**
	 * 结束日期
	 */
	private TextView mEndDateTextView;
	/**
	 * 结束时间
	 */
	private TextView mEndTimeTextView;
	/**
	 * 结束时间
	 */
	private Calendar mEndDate;
	/**
	 * 后退
	 */
	private OnClickListener mBackClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			finishActivity();
		}
	};
	/**
	 * 创建Task
	 */
	private OnClickListener mCeateClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public int getLayoutId() {
		return R.layout.project_task_new;
	}

	@Override
	public boolean initializeTitlBar() {
		setMiddleTitle("新建Task");
		setLeftButton(R.drawable.icon_tab_metra_back_selector,
				mBackClickListener);
		setRightButton(R.drawable.icon_tab_metra_add_selector,
				mCeateClickListener);
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
	}

	private void initView() {
		mNameEditText = (EditText) findViewById(R.id.task_content);
		mEndDateTextView = (TextView) findViewById(R.id.task_new_enddate);
		mEndTimeTextView = (TextView) findViewById(R.id.task_new_endtime);
		mEndTimeTextView.setOnClickListener(this);
		mEndDateTextView.setOnClickListener(this);
		mEndDateTextView
				.setText(DateUtil.getDateStr(System.currentTimeMillis()));
		mEndTimeTextView.setText(DateUtil.getHourAndMin(System
				.currentTimeMillis()));
		mEndDate = Calendar.getInstance();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.task_new_enddate) {
			showDate();
		} else if (v.getId() == R.id.task_new_endtime) {
			showTime();
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

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
		mEndDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
		mEndDate.set(Calendar.MINUTE, minute);
		mEndTimeTextView.setText(DateUtil.getHourAndMin(mEndDate
				.getTimeInMillis()));
	}

	@Override
	public void onDateSet(DatePickerDialog datePickerDialog, int year,
			int month, int day) {
		mEndDate.set(Calendar.YEAR, year);
		mEndDate.set(Calendar.MONTH, month);
		mEndDate.set(Calendar.DAY_OF_MONTH, day);
		mEndDateTextView
				.setText(DateUtil.getDateStr(mEndDate.getTimeInMillis()));
	}

}
