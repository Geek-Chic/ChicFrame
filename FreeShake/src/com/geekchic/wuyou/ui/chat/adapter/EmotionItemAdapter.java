package com.geekchic.wuyou.ui.chat.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.geekchic.constant.AppConfig;
import com.geekchic.constant.AppConstants;
import com.geekchic.wuyou.R;
/**
 * @ClassName: FaceAdapter
 * @Descritpion: 表示每一页GridView的Adapter
 * @author evil
 * @date May 9, 2014
 */
public class EmotionItemAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;
	private int currentPage = 0;
	private Map<String, Integer> mFaceMap;
	private List<Integer> faceList = new ArrayList<Integer>();

	public EmotionItemAdapter(Context context, int currentPage) {
		// TODO Auto-generated constructor stub
		this.inflater = LayoutInflater.from(context);
		this.currentPage = currentPage;
		mFaceMap = AppConfig.getInstance().getFaceMap();
		initData();
	}

	private void initData() {
		for(Map.Entry<String, Integer> entry:mFaceMap.entrySet()){
			faceList.add(entry.getValue());
		}
	}

	@Override
	public int getCount() {
		return AppConstants.NUM + 1;
	}

	@Override
	public Object getItem(int position) {
		return faceList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.chat_emotion_item, null, false);
			viewHolder.faceIV = (ImageView) convertView
					.findViewById(R.id.chat_face_iv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (position == AppConstants.NUM) {
			viewHolder.faceIV.setImageResource(R.drawable.icon_emotion_del_selector);
			viewHolder.faceIV.setBackgroundDrawable(null);
		} else {
			int count = AppConstants.NUM * currentPage + position;
			if (count < 107) {
				viewHolder.faceIV.setImageResource(faceList.get(count));
			} else {
				viewHolder.faceIV.setImageDrawable(null);
			}
		}
		return convertView;
	}

	public static class ViewHolder {
		ImageView faceIV;
	}
}
