/**
 * @Title: ToolsActionDialog.java
 * @Package com.geekchic.wuyou.ui.dialog
 * @Description: 右滑的Action Bar
 * @author: evil
 * @date: May 6, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.dialog;

import java.util.List;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geekchic.common.log.Logger;
import com.geekchic.common.utils.DeviceInfoUtil;
import com.geekchic.common.utils.DisplayInfo;
import com.geekchic.wuyou.R;

/**
 * @ClassName: ToolsActionDialog
 * @Descritpion: 右滑的Action Bar
 * @author evil
 * @date May 6, 2014
 */
public class ToolsActionDialog extends ToolsActionWidget {
	/**
	 * TAG
	 */
	private static final String TAG = "ToolsActionDialog";
	/**
	 * 动作选项
	 */
	private GridView mGridView;

	public ToolsActionDialog(Context context) {
		super(context);
		setContentView(R.layout.actionbar_grid);
		final View v = getContentView();
		mGridView = (GridView) v.findViewById(R.id.actionbar_gridview);
	}

	@Override
	protected void populateQuickActions(final List<QuickAction> quickActions) {

		mGridView.setAdapter(new BaseAdapter() {

			public View getView(int position, View view, ViewGroup parent) {

				TextView textView = (TextView) view;

				if (view == null) {
					textView = new TextView(getContext());
				}

				QuickAction quickAction = quickActions.get(position);
				textView.setText(quickAction.mTitle);
				textView.setGravity(Gravity.CENTER_HORIZONTAL);
				textView.setTextColor(getContext().getResources().getColor(R.color.font_color_most_black));
				textView.setCompoundDrawablesWithIntrinsicBounds(null,
						quickAction.mDrawable, null, null);

				return textView;

			}

			public long getItemId(int position) {
				return position;
			}

			public Object getItem(int position) {
				return null;
			}

			public int getCount() {
				return quickActions.size();
			}
		});

		mGridView.setOnItemClickListener(mInternalItemClickListener);
		mGridView.setOnKeyListener(mKeyListener);
	}

	@Override
	protected void onMeasureAndLayout(Rect anchorRect, View contentView) {

		contentView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		contentView.measure(MeasureSpec.makeMeasureSpec(getScreenWidth(),
				MeasureSpec.EXACTLY), LayoutParams.WRAP_CONTENT);

		int rootHeight = contentView.getMeasuredHeight();
		setWidth(rootHeight);
		int screenHeight=DisplayInfo.getScreenHeight(getContext());
		//高度为Screen高-TitleBar高-TabHost高
		setHeight(screenHeight - anchorRect.bottom-DeviceInfoUtil.dip2px(getContext(), 55));
         Logger.d(TAG, "弹窗高height:"+(getScreenHeight() - anchorRect.bottom-DeviceInfoUtil.dip2px(getContext(), 55)));
         Logger.d(TAG, "总高"+getScreenHeight());
         Logger.d(TAG, "下边框高"+DeviceInfoUtil.dip2px(getContext(), 55));
         Logger.d(TAG, "上边框"+anchorRect.bottom);
		setWidgetSpecs(getScreenWidth() - rootHeight, anchorRect.bottom);
	}

	private OnItemClickListener mInternalItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> adapterView, View view,
				int position, long id) {
			getOnQuickActionClickListener().onQuickActionClicked(
					ToolsActionDialog.this, position);
			if (getDismissOnClick()) {
				dismiss();
			}
		}
	};
    /**
     * 监听回退键和菜单键
     */
	private LinearLayout.OnKeyListener mKeyListener = new LinearLayout.OnKeyListener() {
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if ((keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU)
					&& event.getRepeatCount() == 0 && isShowing()) {
				if (isMenuClick()) {
					setMenuClick(false);
				} else {
					dismiss();
				}
			} else if (keyCode == KeyEvent.KEYCODE_MENU
					&& event.getRepeatCount() == 0 && !isShowing()) {
				show();
			}
			return true;
		}
	};

}
