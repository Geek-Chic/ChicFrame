package com.geekchic.wuyou.ui.chat.adapter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.geekchic.common.log.Logger;
import com.geekchic.common.utils.DateUtil;
import com.geekchic.constant.AppConfig;
import com.geekchic.wuyou.R;
import com.geekchic.wuyou.bean.MessageItem;

public class MessageAdapter extends BaseAdapter {
	/**
	 * TAG
	 */
	private static final String TAG = "MessageAdapter";
	public static final Pattern EMOTION_URL = Pattern.compile("\\[(\\S+?)\\]");

	private Context mContext;
	private LayoutInflater mInflater;
	private List<MessageItem> mMsgList;

	public MessageAdapter(Context context, List<MessageItem> msgList) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		mMsgList = msgList;
		mInflater = LayoutInflater.from(context);
	}

	public void removeHeadMsg() {
		Logger.i(TAG, "before remove mMsgList.size() = " + mMsgList.size());
		if (mMsgList.size() - 10 > 10) {
			for (int i = 0; i < 10; i++) {
				mMsgList.remove(i);
			}
			notifyDataSetChanged();
		}
		Logger.i(TAG, "after remove mMsgList.size() = " + mMsgList.size());
	}

	public void setMessageList(List<MessageItem> msgList) {
		mMsgList = msgList;
		notifyDataSetChanged();
	}

	public void upDateMsg(MessageItem msg) {
		mMsgList.add(msg);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mMsgList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mMsgList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MessageItem item = mMsgList.get(position);
		boolean isComMsg = item.isComMeg();
		ViewHolder holder;
		if (convertView == null
				|| convertView.getTag(R.drawable.ic_launcher + position) == null) {
			holder = new ViewHolder();
			if (isComMsg) {
				convertView = mInflater.inflate(R.layout.chat_left_item, null);
			} else {
				convertView = mInflater.inflate(R.layout.chat_right_item, null);
			}
			holder.head = (ImageView) convertView.findViewById(R.id.chat_item_avator_iv);
			holder.time = (TextView) convertView.findViewById(R.id.chat_item_datetime);
			holder.msg = (TextView) convertView.findViewById(R.id.chat_item_message_content);
			holder.progressBar = (ProgressBar) convertView
					.findViewById(R.id.chat_item_upload_progress);
			convertView.setTag(R.drawable.ic_launcher + position);
		} else {
			holder = (ViewHolder) convertView.getTag(R.drawable.ic_launcher
					+ position);
		}
		holder.time.setText(DateUtil.getChatTime(item.getDate()));
		// L.i("time: " + item.getDate());
		holder.time.setVisibility(View.VISIBLE);
		// holder.head.setBackgroundResource(PushApplication.heads[item
		// .getHeadImg()]);

		holder.msg.setText(
				convertNormalStringToSpannableString(item.getMessage()),
				BufferType.SPANNABLE);
		holder.progressBar.setVisibility(View.GONE);
		holder.progressBar.setProgress(50);
		return convertView;
	}

	/**
	 * 另外一种方法解析表情
	 * 
	 * @param message
	 *            传入的需要处理的String
	 * @return
	 */
	private CharSequence convertNormalStringToSpannableString(String message) {
		// TODO Auto-generated method stub
		String hackTxt;
		if (message.startsWith("[") && message.endsWith("]")) {
			hackTxt = message + " ";
		} else {
			hackTxt = message;
		}
		SpannableString value = SpannableString.valueOf(hackTxt);

		Matcher localMatcher = EMOTION_URL.matcher(value);
		while (localMatcher.find()) {
			String str2 = localMatcher.group(0);
			int k = localMatcher.start();
			int m = localMatcher.end();
			// k = str2.lastIndexOf("[");
			// Log.i("way", "str2.length = "+str2.length()+", k = " + k);
			// str2 = str2.substring(k, m);
			if (m - k < 8) {
				if (AppConfig.getInstance().getFaceMap().containsKey(str2)) {
					int face = AppConfig.getInstance().getFaceMap().get(str2);
					Bitmap bitmap = BitmapFactory.decodeResource(
							mContext.getResources(), face);
					if (bitmap != null) {
						ImageSpan localImageSpan = new ImageSpan(mContext,
								bitmap, ImageSpan.ALIGN_BASELINE);
						value.setSpan(localImageSpan, k, m,
								Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					}
				}
			}
		}
		return value;
	}

	/**
	 * @ClassName: ViewHolder
	 * @Descritpion: ViewHolder
	 * @author evil
	 * @date May 10, 2014
	 */
	static class ViewHolder {
		/**
		 * 头像
		 */
		ImageView head;
		/**
		 * 时间
		 */
		TextView time;
		/**
		 * 消息内容
		 */
		TextView msg;
		/**
		 * 图片
		 */
		ImageView imageView;
		/**
		 * 进度条
		 */
		ProgressBar progressBar;

	}
}