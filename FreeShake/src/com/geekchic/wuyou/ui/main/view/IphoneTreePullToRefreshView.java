/**
 * @Title: IphoneTreePullToRefreshView.java
 * @Package com.geekchic.wuyou.ui.main.view
 * @Description: [用一句话描述做什么]
 * @author: evil
 * @date: May 8, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.wuyou.ui.main.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;

import com.widget.pulltofresh.OverscrollHelper;
import com.widget.pulltofresh.PullToRefreshAdapterViewBase;
import com.widget.pulltofresh.extras.EmptyViewMethodAccessor;
import com.widget.xlistview.IphoneTreeView;

/**
 * @ClassName: IphoneTreePullToRefreshView
 * @Descritpion: [用一句话描述作用] 
 * @author evil
 * @date May 8, 2014
 */
public class IphoneTreePullToRefreshView extends PullToRefreshAdapterViewBase<IphoneTreeView> {



	public IphoneTreePullToRefreshView(Context context) {
		super(context);
	}

	public IphoneTreePullToRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public IphoneTreePullToRefreshView(Context context, Mode mode) {
		super(context, mode);
	}

	public IphoneTreePullToRefreshView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);
	}

	@Override
	public final Orientation getPullToRefreshScrollDirection() {
		return Orientation.VERTICAL;
	}
     public void addChildView(IphoneTreeView iphoneTreeView){
     }
	@Override
	protected IphoneTreeView createRefreshableView(Context context, AttributeSet attrs) {
		final IphoneTreeView lv =new IphoneTreeView(context, attrs);
		return lv;
	}

	class InternalExpandableListView extends ExpandableListView implements EmptyViewMethodAccessor {

		public InternalExpandableListView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void setEmptyView(View emptyView) {
			IphoneTreePullToRefreshView.this.setEmptyView(emptyView);
		}

		@Override
		public void setEmptyViewInternal(View emptyView) {
			super.setEmptyView(emptyView);
		}
	}

	@TargetApi(9)
	final class InternalExpandableListViewSDK9 extends InternalExpandableListView {

		public InternalExpandableListViewSDK9(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
				int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

			final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
					scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

			// Does all of the hard work...
			OverscrollHelper.overScrollBy(IphoneTreePullToRefreshView.this, deltaX, scrollX, deltaY, scrollY,
					isTouchEvent);

			return returnValue;
		}
	}


}
