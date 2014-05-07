/**
 * @Title: BaseTabHostActivity.java
 * @Package com.geekchic.framework.ui
 * @Description: tab基础类
 * @author: evil
 * @date: May 5, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.ui;


import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.geekchic.common.log.Logger;
import com.geekchic.framework.ui.titlebar.BaseTitleBarActivity;
import com.geekchic.wuyou.R;

/**
 * @ClassName: BaseTabHostActivity
 * @Descritpion: tab基础类
 * @author evil
 * @date May 5, 2014
 */
public abstract class BaseTabHostActivity extends BaseTitleBarActivity
		implements OnTabChangeListener {
	/**
	 * TAG
	 */
	private static final String TAG="BaseTabhostActivity";
	/**
	 * TabHost
	 */
	private TabHost mTabHost;
	/**
	 * fragment的父容器
	 */
	private int mContainerID;
	/**
	 * 上次点
	 */
	private TabInfo mLastTab;
	/**
	 * TAB缓存
	 */
	private final Map<String, TabInfo> mTabs = new HashMap<String, TabInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initTabHost();
		initTabContent();
	}
	
	 /**
     * 初始化TabHost
     */
    private void initTabHost()
    {
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabHost.setOnTabChangedListener(this);
    }

	protected void initTabContent() {

		// tabSpec.setContent(new TabFactory(mActivity));
		TabInfo[] tabInfos = getTabInfos();
		for (int i = 0; i < tabInfos.length; i++) {
			View tabView=getIndicatorView(tabInfos[i].mTitileId, tabInfos[i].mIconId);
			addTab(mTabHost.newTabSpec(tabInfos[i].mTag).setIndicator(tabView), tabInfos[i]);
		}

	}
	 /**
     * 获得tab的indicator<BR>
     * @param textId 文字资源ID
     * @param drawableId 图片资源ID
     * @return indicator
     */
    private CommTabIndictorView getIndicatorView(int textId, int drawableId)
    {
        return new CommTabIndictorView(this, textId, drawableId);
    }
	public void addTab(TabHost.TabSpec tabSpec, TabInfo info) {
		// tabSpec.setContent(new TabFactory(mActivity));
		tabSpec.setContent(TabFactory.getInstance(this));
		String tag = tabSpec.getTag();

		final FragmentManager fm = getSupportFragmentManager();
		// final FragmentManager fm = mActivity.getFragmentManager();
		info.mFragment = fm.findFragmentByTag(tag);

		if (info.mFragment != null) {
			// isDetached分离状态
			if (!info.mFragment.isDetached()) {
				FragmentTransaction ft = fm.beginTransaction();
				ft.hide(info.mFragment);
				ft.commit();
			}
		}
		mTabs.put(tag, info);
		mTabHost.addTab(tabSpec);
	}
	@Override
	public int getLayoutId() {
		return R.layout.base_tab_main;
	}
     @Override
    public void onTabChanged(String tabId) {
         Logger.d(TAG, "----------->onTabChanged tabId: " + tabId);
         TabInfo newTab = mTabs.get(tabId);
         if (mLastTab != newTab)
         {
             FragmentManager fragmentManager = getSupportFragmentManager();
             FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
             // 脱离之前的tab
             if (mLastTab != null && mLastTab.mFragment != null)
             {
                 fragmentTransaction.hide(mLastTab.mFragment);
             }
             if (newTab != null)
             {
                 if (newTab.mFragment == null)
                 {
                     newTab.mFragment = Fragment.instantiate(this,
                             newTab.mClss.getName(),
                             newTab.mArgs);
                     fragmentTransaction.add(R.id.realcontent,
                             newTab.mFragment,
                             newTab.mTag);
                 }
                 else
                 {
                     fragmentTransaction.show(newTab.mFragment);
                 }
             }
             mLastTab = newTab;
             fragmentTransaction.commit();
             setMiddleTitle(newTab.mTitileId);
             // 会在进程的主线程中，用异步的方式来执行,如果想要立即执行这个等待中的操作，就要调用这个方法
             // 所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
             fragmentManager.executePendingTransactions();
         }
     }
     protected TabHost getTabHost(){
    	 return mTabHost;
     }
	/**
	 * 获取tab配置消息
	 * 
	 * @return
	 */
	protected abstract TabInfo[] getTabInfos();

	/**
	 * @ClassName: TabInfo
	 * @Descritpion: [用一句话描述作用]
	 * @author evil
	 * @date May 5, 2014
	 */
	protected  class TabInfo {
		/**
		 * 每一个Tab的Tag
		 */
		private final String mTag;
		/**
		 * Tab标签名
		 */
		private final int mTitileId;
		/**
		 * Tab的icon
		 */
		private final int mIconId;

		/**
		 * 每个tab页签要展示的view的类的引用
		 */
		private final Class<?> mClss;

		/**
		 * 传入的参数
		 */
		private final Bundle mArgs;

		/**
		 * 添加的fragment
		 */
		private Fragment mFragment;

		/**
		 * 构造方法
		 * 
		 * @param tabTag
		 *            标签
		 * @param clazz
		 *            Class
		 * @param bundle
		 *            参数
		 */
		public TabInfo(String tag, int titleId, int icon, Class<?> clss, Bundle args) {
			mTag = tag;
			mTitileId = titleId;
			mClss = clss;
			mArgs = args;
			mIconId = icon;
		}
	}

	/**
	 * @ClassName: TabFactory
	 * @Descritpion: Tab工厂类
	 * @author evil
	 * @date May 5, 2014
	 */
	static class TabFactory implements TabHost.TabContentFactory {
		private static TabFactory sInstance;

		private Context mContext;

		private TabFactory(Context context) {
			mContext = context;
		}

		public static TabFactory getInstance(Context context) {
			if (sInstance == null) {
				sInstance = new TabFactory(context);
			}
			return sInstance;
		}

		@Override
		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumHeight(0);
			v.setMinimumWidth(0);
			return v;
		}
	}

	/**
	 * @ClassName: CommTabIndictorView
	 * @Descritpion: tab标签样式
	 * @author evil
	 * @date May 5, 2014
	 */
	private class CommTabIndictorView extends RelativeLayout {
		/**
		 * tab名
		 */
		private int mTabTitleId;
		/**
		 * 图标名
		 */
		private int mTabIconId;
		/**
		 * Tab名view
		 */
		private TextView mTitleView;
		/**
		 * 提示消息
		 */
		private TextView mMessageTips;

		public CommTabIndictorView(Context context) {
			super(context);
			initTabHost();
		}

		private void initTabHost() {
			mTabHost = (TabHost) findViewById(android.R.id.tabhost);
			mTabHost.setup();
            initTabHost();
		}

		/**
		 * CommTabIndictorView构造函数
		 * 
		 * @param context
		 * @param titleId
		 * @param iconId
		 */
		public CommTabIndictorView(Context context, int titleId, int iconId) {
			super(context);
			this.mTabTitleId = titleId;
			this.mTabIconId = iconId;
			initView(context);
		}

		private void initView(Context context) {
			View view = inflate(context, R.layout.base_tab_indictor, null);
			mTitleView = (TextView) view.findViewById(R.id.tab_title);
			mMessageTips = (TextView) view.findViewById(R.id.tab_unread_msg);
			mTitleView.setText(mTabTitleId);
			mTitleView.setCompoundDrawablesWithIntrinsicBounds(0, mTabIconId,
					0, 0);
			addView(view, new RelativeLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}

		/**
		 * 设置求读条数<BR>
		 * 
		 * @param count
		 *            数量
		 */
		public void setUnreadCount(int count) {
			mMessageTips.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
			mMessageTips.setText(count >= 100 ? "99+" : String.valueOf(count));
		}

	}
}
