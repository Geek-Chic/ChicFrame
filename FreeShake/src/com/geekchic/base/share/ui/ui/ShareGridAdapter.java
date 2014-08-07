package com.geekchic.base.share.ui.ui;



import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekchic.base.share.ShareService;
import com.geekchic.common.utils.StringUtils;
import com.geekchic.wuyou.R;

/**
 * GridView适配器，用于ViewPagerPopup中的GridView
 * @author youtui
 * @since 2014/3/25
 */
public class ShareGridAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<ShareService> list;
	private int showStyle;

	public ShareGridAdapter(Context context, ArrayList<ShareService> list, int showStyle) {
		this.mContext = context;
		this.list = list;
		this.showStyle = showStyle;
	}
	/**
	 * GridView的网格数
	 */
	@Override
	public int getCount() {
		return list.size();
	}
	/**
	 * 父类需要重写的方法
	 */
	@Override
	public Object getItem(int arg0) {
		return null;
	}
	/**
	 * 父类需要重写的方法
	 */
	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	/**
	 * 定义GridView显示的内容
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {	
		//使用convertView优化listview
		if (convertView == null) {
			View view = null;
			if(showStyle==ShareViewType.BLACK_POPUP){
				view = LayoutInflater.from(mContext).inflate(R.layout.yt_pagergrid_item,null);			
			}else if(showStyle==ShareViewType.WHITE_LIST){
				view = LayoutInflater.from(mContext).inflate(R.layout.yt_sharelist_item,null);
			}else if(showStyle==ShareViewType.WHITE_GRID){
				view = LayoutInflater.from(mContext).inflate(R.layout.yt_whiteviewpager_grid_item,null);
			}
			convertView = view;
		}
		
		TextView pointText = null;
		ShareService service=list.get(position);
		if(showStyle==ShareViewType.BLACK_POPUP){
			ImageView imageView = (ImageView) convertView.findViewById(R.id.logo_imageview);
			TextView textView = (TextView) convertView.findViewById(R.id.logo_textview);
			// 设置社交平台logo 
			imageView.setImageResource(service.getIconId());
			// 积分textview
			textView.setText(ShareList.getTitle(list.get(position).getName()));
			pointText = (TextView) convertView.findViewById(R.id.griditem_point_tv);
		}else if(showStyle==ShareViewType.WHITE_LIST){
			ImageView imageView = (ImageView) convertView.findViewById(R.id.sharelistitem_logo_image);
			TextView textView = (TextView) convertView.findViewById(R.id.sharelistitem_platform_text);
			// 设置社交平台logo
			imageView.setImageResource(service.getIconId());
			String showName=list.get(position).getShowName(mContext);
			if(!StringUtils.isNullOrEmpty(showName)){
			    textView.setText(showName);
			}
			// 积分textview
			pointText = (TextView) convertView.findViewById(R.id.sharelistitem_point_text);
		}else if(showStyle==ShareViewType.WHITE_GRID){
			ImageView imageView = (ImageView) convertView.findViewById(R.id.whitepagergrid_logo_imageview);
			TextView textView = (TextView) convertView.findViewById(R.id.whitepagergrid_logo_textview);
			// 设置社交平台logo 
			imageView.setImageResource(service.getIconId());
			// 积分textview
			textView.setText(service.getShowName(mContext));
			textView.setTextColor(0xffa8a8a8);
			pointText = (TextView) convertView.findViewById(R.id.whitepagergrid_point_tv);
		}
        return convertView;


	}

}
