/**
 * @Title: HttpManager.java
 * @Package com.geekchic.framework.network
 * @Description: Http管理
 * @author: evil
 * @date: May 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.service;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.support.v4.util.LruCache;

import com.geekchic.common.log.Logger;
import com.geekchic.framework.bean.Request;
import com.geekchic.framework.network.RequestListener;
import com.geekchic.framework.service.core.BaseRequestService;

/**
 * @ClassName: HttpManager
 * @Descritpion: Http管理
 * @author evil
 * @date May 2, 2014
 */
public class BaseRequestManager {
	/**
	 * TAG
	 */
	private static final String TAG = "HttpManager";
	/**
	 * 错误Code Key
	 */
	public static final String RECEIVER_EXTRA_ERROR_TYPE = "com.geekchic.extra.error";
	/**
	 * HTTP错误状态码key
	 * */
	public static final String RECEIVER_EXTRA_CONNECTION_ERROR_STATUS_CODE = "com.geekchic.extra.connectionErrorStatusCode";

	private final Context mContext;
	/**
	 * 工作服务
	 */
	private final Class<? extends BaseRequestService> mRequestService;
	/**
	 * 缓存请求监听器
	 */
	private final HashMap<Request, RequestReceiver> mRequestReceiverMap;
	/**
	 * 请求缓存
	 */
	private final LruCache<Request, Bundle> mMemoryCache;

	/**
	 * HttpManager构造函数
	 * 
	 * @param context
	 * @param requestService
	 */
	protected BaseRequestManager(Context context,
			Class<? extends BaseRequestService> requestService) {
		this.mContext = context;
		this.mRequestService = requestService;
		mMemoryCache = new LruCache<Request, Bundle>(30);
		mRequestReceiverMap = new HashMap<Request, BaseRequestManager.RequestReceiver>();
	}

	public final void addRequestListener(RequestListener listener,
			Request request) {
		if (null == listener) {
			return;
		}
		if (null == request) {
			throw new IllegalArgumentException("Request cannot be null.");
		}
		RequestReceiver receiver = mRequestReceiverMap.get(request);
		if (null == receiver) {
			Logger.e(TAG, "request non-existing");
			return;
		}
		receiver.addListenerHolder(new ListenerHolder(listener));
	}

	/**
	 * 称除Listener
	 * 
	 * @param listener
	 */
	public final void removeRequestListener(RequestListener listener) {
		removerRequestListener(listener, null);
	}

	public final void removerRequestListener(RequestListener listener,
			Request request) {
		if (null == listener) {
			return;
		}
		ListenerHolder holder = new ListenerHolder(listener);
		if (request != null) {
			RequestReceiver requestReceiver = mRequestReceiverMap.get(request);
			if (requestReceiver != null) {
				requestReceiver.removeListener(holder);
			}
		} else {
			for (RequestReceiver requestReceiver : mRequestReceiverMap.values()) {
				requestReceiver.removeListener(holder);
			}
		}
	}

	/**
	 * 请求是否在处理
	 * 
	 * @param request
	 * @return
	 */
	public final boolean isRequestInProgress(Request request) {
		return mRequestReceiverMap.containsKey(request);
	}

	/**
	 * 从缓存获取请求
	 * 
	 * @param listener
	 * @param request
	 */
	public final void callListenerWithCachedData(RequestListener listener,
			Request request) {
		if (request == null) {
			throw new IllegalArgumentException("Request cannot be null.");
		}
		if (listener == null) {
			return;
		}

		if (request.isMemoryCacheDataEnabled()) {
			Bundle bundle = mMemoryCache.get(request);
			if (bundle != null) {
				listener.onRequestFinished(request, bundle);
			} else {
				listener.onRequestConnectionError(request, -1);
			}
		}
	}

	public final void execute(Request request, RequestListener listener) {
		if (request == null) {
			throw new IllegalArgumentException("Request cannot be null.");
		}
		if (mRequestReceiverMap.containsKey(request)) {
			Logger.d(TAG,
					"This request is already in progress. Adding the new listener to it.");

			// This exact request is already in progress. Adding the new
			// listener.
			addRequestListener(listener, request);
			// Just check if the new request has the memory cache enabled.
			if (request.isMemoryCacheDataEnabled()) {
				// If true, enable it in the RequestReceiver (if it's not the
				// case already)
				mRequestReceiverMap.get(request).enableMemoryCache();
			}
			return;
		}
		Logger.d(TAG, "Creating a new request and adding the listener to it.");

		RequestReceiver requestReceiver = new RequestReceiver(request);
		mRequestReceiverMap.put(request, requestReceiver);

		addRequestListener(listener, request);

		Intent intent = new Intent(mContext, mRequestService);
		intent.putExtra(BaseRequestService.INTENT_EXTRA_RECEIVER,
				requestReceiver);
		intent.putExtra(BaseRequestService.INTENT_EXTRA_REQUEST, request);
		mContext.startService(intent);
	}

	private final class RequestReceiver extends ResultReceiver {
		/**
		 * 请求Request
		 */
		private final Request mRequest;
		/**
		 * 是否支持缓存
		 */
		private boolean mMemoryCacheEnabled;
		/**
		 * 缓存活动的监听器
		 */
		private final Set<ListenerHolder> mListenerHolderSet;

		/**
		 * RequestReceiver构造函数
		 * 
		 * @param request
		 */
		public RequestReceiver(Request request) {
			super(new Handler(Looper.getMainLooper()));
			this.mRequest = request;
			this.mMemoryCacheEnabled = request.isMemoryCacheDataEnabled();
			mListenerHolderSet = Collections
					.synchronizedSet(new HashSet<ListenerHolder>());
			// 清除旧缓存
			mMemoryCache.remove(request);

		}

		void enableMemoryCache() {
			mMemoryCacheEnabled = true;
		}

		void addListenerHolder(ListenerHolder listenerHolder) {
			synchronized (mListenerHolderSet) {
				mListenerHolderSet.add(listenerHolder);
			}
		}

		void removeListener(ListenerHolder listenerHolder) {
			synchronized (mListenerHolderSet) {
				mListenerHolderSet.remove(listenerHolder);
			}
		}

		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			super.onReceiveResult(resultCode, resultData);
			if (mMemoryCacheEnabled) {
				mMemoryCache.put(mRequest, resultData);
			}
			mRequestReceiverMap.remove(mRequest);

			synchronized (mListenerHolderSet) {
				for (ListenerHolder listenerHolder : mListenerHolderSet) {
					listenerHolder.onRequestFinished(mRequest, resultCode,
							resultData);
				}
			}
		}
	}

	/**
	 * @ClassName: ListenerHolder
	 * @Descritpion: 活动监听器Holder，使用弱引用表示，
	 * @author evil
	 * @date May 2, 2014
	 */
	private final class ListenerHolder {
		/**
		 * 活动监听器
		 */
		private final WeakReference<RequestListener> mReferenceReference;
		/**
		 * 哈希值
		 */
		private final int mHashCode;

		/**
		 * ListenerHolder构造函数
		 * 
		 * @param listener
		 */
		public ListenerHolder(RequestListener listener) {
			mReferenceReference = new WeakReference<RequestListener>(listener);
			mHashCode = 31 + listener.hashCode();
		}

		/**
		 * 请求回调
		 * 
		 * @param request
		 * @param resultCode
		 * @param data
		 */
		public void onRequestFinished(Request request, int resultCode,
				Bundle data) {
			mRequestReceiverMap.remove(request);
			RequestListener listener = mReferenceReference.get();
			if (null != listener) {
				switch (resultCode) {
				case BaseRequestService.CODE_TYPE_SUCCESS:
					listener.onRequestFinished(request, data);
					break;
				case BaseRequestService.CODE_ERROR_DATA:
					listener.onRequestDataError(request);
					break;
				case BaseRequestService.CODE_ERROR_CONNEXION:
					int statusCode = data
							.getInt(RECEIVER_EXTRA_CONNECTION_ERROR_STATUS_CODE);
					listener.onRequestConnectionError(request, statusCode);
					break;
				case BaseRequestService.CODE_ERROR_CUSTOM:
					listener.onRequestCustomError(request, data);
					break;
				default:
					break;
				}
			}
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof ListenerHolder) {
				ListenerHolder oHolder = (ListenerHolder) o;
				return mReferenceReference != null
						&& oHolder.mReferenceReference != null
						&& mHashCode == oHolder.mHashCode;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return mHashCode;
		}
	}
}
