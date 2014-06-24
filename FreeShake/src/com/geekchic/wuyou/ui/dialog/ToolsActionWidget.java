/**
 * @Title: ToolsActionWidget.java
 * @Package com.geekchic.wuyou.ui.dialog
 * @Description: 自定义PopupWinow弹窗
 * @author: evil
 * @date: May 6, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

import com.geekchic.common.log.Logger;
import com.geekchic.common.utils.DisplayInfo;
import com.geekchic.wuyou.R;

/**
 * @ClassName: ToolsActionWidget
 * @Descritpion: 自定义PopupWinow弹窗
 * @author evil
 * @date May 6, 2014
 */
public abstract class ToolsActionWidget extends PopupWindow {
	/**
	 * TAG
	 */
    private static final String TAG="ToolsActionWidget";
	private static final int MEASURE_AND_LAYOUT_DONE = 1 << 1;

	private final int[] mLocation = new int[2];
	private final Rect mRect = new Rect();

	private int mPrivateFlags;

	private Context mContext;

	private View mAnchor;
	private boolean mIsMenuClick;

	private boolean mDismissOnClick;

	private int mPopupX = -1;
	private int mPopupY = -1;

	private boolean mIsDirty;

	private OnQuickActionClickListener mOnQuickActionClickListener;
	private ArrayList<QuickAction> mQuickActions = new ArrayList<QuickAction>();

	/**
	 * @ClassName: OnQuickActionClickListener
	 * @Descritpion:QuickAcion的监听器
	 * @author evil
	 * @date May 6, 2014
	 */
	public static interface OnQuickActionClickListener {
		/**
		 * 实现此回调，完成监听
		 * 
		 * @param widget
		 * @param position
		 */
		void onQuickActionClicked(ToolsActionWidget widget, int position);
	}

	/**
	 * ToolsActionWidget构造函数
	 * 
	 * @param context
	 */
	public ToolsActionWidget(Context context) {
		super(context);

		mContext = context;

		initializeDefault();

		setFocusable(true);
		setTouchable(true);
		setOutsideTouchable(true);
	}

	/**
	 * 自定义View
	 * 
	 * @param layoutId
	 */
	public void setContentView(int layoutId) {
		setContentView(LayoutInflater.from(mContext).inflate(layoutId, null));
	}

	/**
	 * 初始化默认信息
	 */
	private void initializeDefault() {
		mDismissOnClick = true;
	}

	/**
	 * 获取相应位置的Aciton
	 * 
	 * @param position
	 * @return
	 */
	public QuickAction getQuickAction(int position) {
		if (position < 0 || position >= mQuickActions.size())
			return null;
		return mQuickActions.get(position);
	}

	/**
	 * 获取屏幕宽
	 * 
	 * @return The width of the screen
	 */
	protected int getScreenWidth() {
		return DisplayInfo.getInstance().getWindowVisibleDisplayWidth();
	}

	/**
	 * 获取屏幕高
	 * 
	 * @return The height of the screen
	 */
	protected int getScreenHeight() {
		return DisplayInfo.getInstance().getWindowVisibleDisplayHeight();
	}

	/**
	 * 默认情况是点击后自动取消弹窗，此处可以自行设置是否点击取消
	 * 
	 * @param dismissOnClick
	 */
	public void setDismissOnClick(boolean dismissOnClick) {
		mDismissOnClick = dismissOnClick;
	}

	public boolean getDismissOnClick() {
		return mDismissOnClick;
	}

	/**
	 * 设置监听器
	 * 
	 * @param listener
	 */
	public void setOnQuickActionClickListener(
			OnQuickActionClickListener listener) {
		mOnQuickActionClickListener = listener;
	}

	/**
	 * 添加QuickAction
	 * 
	 * @param action
	 */
	public void addQuickAction(QuickAction action) {
		if (action != null) {
			mQuickActions.add(action);
			mIsDirty = true;
		}
	}

	/**
	 * 清除所有的QuckAction
	 */
	public void clearAllQuickActions() {
		if (!mQuickActions.isEmpty()) {
			mQuickActions.clear();
			mIsDirty = true;
		}
	}

	/**
	 * 显示窗口
	 * 
	 * @param anchor
	 *            与之相对的窗口
	 */
	public void show(View anchor) {

		final View contentView = getContentView();

		if (contentView == null) {
			throw new IllegalStateException(
					"You need to set the content view using the setContentView method");
		}

		// 设置触摸事件 - 修复触摸弹窗以外的地方无法隐藏弹窗
		contentView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int x = (int) event.getX();
				final int y = (int) event.getY();

				if ((event.getAction() == MotionEvent.ACTION_DOWN)
						&& ((x < 0) || (x >= getWidth()) || (y < 0) || (y >= getHeight()))) {
					dismiss();
					return true;
				} else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					dismiss();
					return true;
				} else {
					return contentView.onTouchEvent(event);
				}
			}
		});

		// Replaces the background of the popup with a cleared background
		// setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		// 修复点击背景空白
		setBackgroundDrawable(null);

		final int[] loc = mLocation;
		anchor.getLocationOnScreen(loc);
		Logger.d(TAG, "height:"+anchor.getHeight());
		mRect.set(loc[0], loc[1], loc[0] + anchor.getWidth(),
				loc[1] + anchor.getHeight());

		if (mIsDirty) {
			clearQuickActions();
			populateQuickActions(mQuickActions);
		}
		onMeasureAndLayout(mRect, contentView);

		if ((mPrivateFlags & MEASURE_AND_LAYOUT_DONE) != MEASURE_AND_LAYOUT_DONE) {
			throw new IllegalStateException(
					"onMeasureAndLayout() did not set the widget specification by calling"
							+ " setWidgetSpecs()");
		}

		prepareAnimationStyle();
		Logger.d(TAG, "Y:"+mPopupY);
		showAtLocation(anchor, Gravity.NO_GRAVITY, mPopupX, mPopupY);
	}

	public void show(View anchor, boolean isMenuClick) {
		this.mIsMenuClick = isMenuClick;
		show(anchor);
	}

	protected void show() {
		if (mAnchor != null)
			show(mAnchor);
	}

	protected boolean isMenuClick() {
		return mIsMenuClick;
	}

	protected void setMenuClick(boolean isMenuClick) {
		this.mIsMenuClick = isMenuClick;
	}

	protected void clearQuickActions() {
		if (!mQuickActions.isEmpty()) {
			onClearQuickActions();
		}
	}

	/**
	 * 清除Actions
	 */
	protected void onClearQuickActions() {
	}

	protected abstract void populateQuickActions(List<QuickAction> quickActions);

	/**
	 * 计算弹窗位置大小
	 * 
	 * @param anchorRect
	 * @param contentView
	 */
	protected abstract void onMeasureAndLayout(Rect anchorRect, View contentView);

	/**
	 * 设置弹窗位置
	 * 
	 * @param popupX
	 * @param popupY
	 */
	protected void setWidgetSpecs(int popupX, int popupY) {
		mPopupY = popupY;
		mPopupX = popupX;
		mPrivateFlags |= MEASURE_AND_LAYOUT_DONE;
	}

	/**
	 * 设置动画样式
	 */
	private void prepareAnimationStyle() {

		setAnimationStyle(R.style.PopupAnimation_RightToLeft);
	}

	protected Context getContext() {
		return mContext;
	}

	/**
	 * 设置监听
	 * 
	 * @return
	 */
	protected OnQuickActionClickListener getOnQuickActionClickListener() {
		return mOnQuickActionClickListener;
	}

	public int getmPopupX() {
		return mPopupX;
	}

	public void setmPopupX(int mPopupX) {
		this.mPopupX = mPopupX;
	}

	public int getmPopupY() {
		return mPopupY;
	}

	public void setmPopupY(int mPopupY) {
		this.mPopupY = mPopupY;
	}

}
