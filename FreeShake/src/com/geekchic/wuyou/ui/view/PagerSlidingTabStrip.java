package com.geekchic.wuyou.ui.view;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geekchic.wuyou.R;

public class PagerSlidingTabStrip extends HorizontalScrollView {

	public interface IconTabProvider {
		public int getPageIconResId(int position);
	}

	// @formatter:off
	private static final int[] ATTRS = new int[] { android.R.attr.textSize,
			android.R.attr.textColor };
	// @formatter:on

	private LinearLayout.LayoutParams defaultTabLayoutParams;
	private LinearLayout.LayoutParams expandedTabLayoutParams;

	private final PageListener pageListener = new PageListener();
	public OnPageChangeListener delegatePageListener;

	private LinearLayout tabsContainer;
	private ViewPager pager;

	private int tabCount;

	private int currentPosition = 0;

	private boolean checkedTabWidths = false;

	private boolean shouldExpand = false;
	private boolean textAllCaps = true;

	private int scrollOffset = 52;

	private int tabTextSize = 12;
	private int tabTextColor = 0xFF666666;
	private Typeface tabTypeface = null;
	private int tabTypefaceStyle = Typeface.BOLD;

	private int lastScrollX = 0;

	private Locale locale;
	private LayoutInflater layoutInflater;

	public PagerSlidingTabStrip(Context context) {
		this(context, null);
	}

	public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PagerSlidingTabStrip(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

		setFillViewport(true);
		setWillNotDraw(false);
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		tabsContainer = new LinearLayout(context);
		tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
		tabsContainer.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		addView(tabsContainer);

		DisplayMetrics dm = getResources().getDisplayMetrics();

		scrollOffset = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
		tabTextSize = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, tabTextSize, dm);

		// get system attrs (android:textSize and android:textColor)

		TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);

		tabTextSize = a.getDimensionPixelSize(0, tabTextSize);
		tabTextColor = a.getColor(1, tabTextColor);

		a.recycle();

		// get custom attrs

		a = context.obtainStyledAttributes(attrs,
				R.styleable.PagerSlidingTabStrip);

		shouldExpand = a.getBoolean(
				R.styleable.PagerSlidingTabStrip_shouldExpand, shouldExpand);
		scrollOffset = a.getDimensionPixelSize(
				R.styleable.PagerSlidingTabStrip_scrollOffset, scrollOffset);
		textAllCaps = a.getBoolean(
				R.styleable.PagerSlidingTabStrip_textAllCaps, textAllCaps);

		a.recycle();

		defaultTabLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		defaultTabLayoutParams.gravity = Gravity.BOTTOM;
		expandedTabLayoutParams = new LinearLayout.LayoutParams(0,
				LayoutParams.MATCH_PARENT, 1.0f);

		if (locale == null) {
			locale = getResources().getConfiguration().locale;
		}
	}

	public void setViewPager(ViewPager pager) {
		this.pager = pager;

		if (pager.getAdapter() == null) {
			throw new IllegalStateException(
					"ViewPager does not have adapter instance.");
		}

		pager.setOnPageChangeListener(pageListener);

		notifyDataSetChanged();
	}

	public void setOnPageChangeListener(OnPageChangeListener listener) {
		this.delegatePageListener = listener;
	}

	public void notifyDataSetChanged() {

		tabsContainer.removeAllViews();

		tabCount = pager.getAdapter().getCount();

		for (int i = 0; i < tabCount; i++) {

			if (pager.getAdapter() instanceof IconTabProvider) {
				addIconTab(i,
						((IconTabProvider) pager.getAdapter())
								.getPageIconResId(i));
			} else {
				addTextTab(i, pager.getAdapter().getPageTitle(i).toString());
			}

		}

		updateTabStyles();

		checkedTabWidths = false;

		getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@SuppressWarnings("deprecation")
					@SuppressLint("NewApi")
					@Override
					public void onGlobalLayout() {

						if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
							getViewTreeObserver().removeGlobalOnLayoutListener(
									this);
						} else {
							getViewTreeObserver().removeOnGlobalLayoutListener(
									this);
						}

						currentPosition = pager.getCurrentItem();
						scrollToChild(currentPosition, 0);
					}
				});

	}

	private void addTextTab(final int position, String title) {

		TextView tab = new TextView(getContext());
		tab.setText(title);
		tab.setFocusable(true);
		tab.setGravity(Gravity.BOTTOM);
		tab.setSingleLine();

		tab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pager.setCurrentItem(position);
			}
		});
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		tabsContainer.addView(tab, lp);

	}

	private void addIconTab(final int position, int resId) {
		RelativeLayout layout = (RelativeLayout) layoutInflater.inflate(
				R.layout.nav_bg_item, null);
		ImageView tab = (ImageView) layout
				.findViewById(R.id.nav_item_imageview);
		tab.setFocusable(true);
		tab.setImageResource(resId);
		tab.setBackgroundColor(getResources().getColor(R.color.bg_color_white));

		tab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pager.setCurrentItem(position);
			}
		});
		tabsContainer.setGravity(Gravity.BOTTOM);
		tabsContainer.addView(layout);

	}

	private void updateTabStyles() {

		for (int i = 0; i < tabCount; i++) {

			View v = tabsContainer.getChildAt(i);

			v.setLayoutParams(defaultTabLayoutParams);
			v.setPadding(0, 0, 0, 0);

			if (v instanceof TextView) {

				TextView tab = (TextView) v;
				tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
				tab.setTypeface(tabTypeface, tabTypefaceStyle);
				tab.setTextColor(tabTextColor);

				// setAllCaps() is only available from API 14, so the upper case
				// is made manually if we are on a
				// pre-ICS-build
				if (textAllCaps) {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
						tab.setAllCaps(true);
					} else {
						tab.setText(tab.getText().toString()
								.toUpperCase(locale));
					}
				}
			}
		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		if (!shouldExpand
				|| MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.UNSPECIFIED) {
			return;
		}

		int myWidth = getMeasuredWidth();
		int childWidth = 0;
		for (int i = 0; i < tabCount; i++) {
			childWidth += tabsContainer.getChildAt(i).getMeasuredWidth();
		}

		if (!checkedTabWidths && childWidth > 0 && myWidth > 0) {

			if (childWidth <= myWidth) {
				for (int i = 0; i < tabCount; i++) {
					tabsContainer.getChildAt(i).setLayoutParams(
							expandedTabLayoutParams);
				}
			}

			checkedTabWidths = true;
		}
	}

	private void scrollToChild(int position, int offset) {

		if (tabCount == 0) {
			return;
		}

		int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;

		if (position > 0 || offset > 0) {
			newScrollX -= scrollOffset;
		}

		if (newScrollX != lastScrollX) {
			lastScrollX = newScrollX;
			scrollTo(newScrollX, 0);
		}

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (isInEditMode() || tabCount == 0) {
			return;
		}

	}

	private class PageListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {

			currentPosition = position;

			scrollToChild(position, (int) (positionOffset * tabsContainer
					.getChildAt(position).getWidth()));

			invalidate();

			if (delegatePageListener != null) {
				delegatePageListener.onPageScrolled(position, positionOffset,
						positionOffsetPixels);
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_IDLE) {
				scrollToChild(pager.getCurrentItem(), 0);
			}

			if (delegatePageListener != null) {
				delegatePageListener.onPageScrollStateChanged(state);
			}
		}

		@Override
		public void onPageSelected(int position) {
			if (delegatePageListener != null) {
				delegatePageListener.onPageSelected(position);
			}
		}

	}

	public void setScrollOffset(int scrollOffsetPx) {
		this.scrollOffset = scrollOffsetPx;
		invalidate();
	}

	public int getScrollOffset() {
		return scrollOffset;
	}

	public void setShouldExpand(boolean shouldExpand) {
		this.shouldExpand = shouldExpand;
		requestLayout();
	}

	public boolean getShouldExpand() {
		return shouldExpand;
	}

	public boolean isTextAllCaps() {
		return textAllCaps;
	}

	public void setAllCaps(boolean textAllCaps) {
		this.textAllCaps = textAllCaps;
	}

	public void setTextSize(int textSizePx) {
		this.tabTextSize = textSizePx;
		updateTabStyles();
	}

	public int getTextSize() {
		return tabTextSize;
	}

	public void setTextColor(int textColor) {
		this.tabTextColor = textColor;
		updateTabStyles();
	}

	public void setTextColorResource(int resId) {
		this.tabTextColor = getResources().getColor(resId);
		updateTabStyles();
	}

	public int getTextColor() {
		return tabTextColor;
	}

	public void setTypeface(Typeface typeface, int style) {
		this.tabTypeface = typeface;
		this.tabTypefaceStyle = style;
		updateTabStyles();
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		SavedState savedState = (SavedState) state;
		super.onRestoreInstanceState(savedState.getSuperState());
		currentPosition = savedState.currentPosition;
		requestLayout();
	}

	@Override
	public Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		SavedState savedState = new SavedState(superState);
		savedState.currentPosition = currentPosition;
		return savedState;
	}

	static class SavedState extends BaseSavedState {
		int currentPosition;

		public SavedState(Parcelable superState) {
			super(superState);
		}

		private SavedState(Parcel in) {
			super(in);
			currentPosition = in.readInt();
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeInt(currentPosition);
		}

		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
			@Override
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			@Override
			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}

}
