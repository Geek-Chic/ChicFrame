/**
 * @Title: BasicListDialog.java
 * @Package com.geekchic.framework.ui.dialog
 * @Description: 通用列表类对语框 
 * @author: evil
 * @date: May 30, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geekchic.wuyou.R;

/**
 * @ClassName: BasicListDialog
 * @Descritpion: 通用列表类对语框 
 * @author evil
 * @date May 30, 2014
 */
public class BasicListDialog {
  private Context mContext;
  private BasicDialog mBasicDialog;
  private String mTitle;
  private int[] mIcons;
  private String[] mItemTitleSequences;
  private String[] mSubItemTitleCharSequences;
  private LayoutInflater mInflater;
  /**
   * 条目的可用属性
   */
  private boolean[] mEnable;
  /**
   * 弹起方式
   */
  private BounceMode mBounceMode;
  /**
   * ListView点击监听器
   */
  private DialogInterface.OnClickListener mClickListener;
  public BasicListDialog(Context context){
	  this.mContext=context;
  }
  /**
   * 创建对话框
   * @return
   */
  public BasicListDialog create(){
	  mInflater =(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  BasicListDialogAdapter mBasicListDialogAdapter=new BasicListDialogAdapter();
	  mBasicDialog=new BasicDialog.Builder(mContext).setTitle(mTitle)
			  .setAdapter(mBasicListDialogAdapter, mClickListener)
			  .create();
	  if(BounceMode.TOP==mBounceMode){
		  Window window = mBasicDialog.getWindow();
          window.setGravity(Gravity.TOP);
          window.setWindowAnimations(R.style.dialog_animn_from_top_sytle);
	  }else if(BounceMode.BOTTOM==mBounceMode){
		  Window window=mBasicDialog.getWindow();
		  window.setGravity(Gravity.BOTTOM);
		     window.setWindowAnimations(R.style.dialog_animn_from_top_sytle);
	  }
	  return this;
	  
  }
  /**
   * 设置对话选项
   * @param itemTitles
   * @param icons
   * @param itemSubTitles
   * @param onClickListener
   * @return
   */
  public BasicListDialog setItems(String[] itemTitles, int[] icons,
          String[] itemSubTitles,
          DialogInterface.OnClickListener onClickListener)
  {
      mItemTitleSequences = itemTitles;
      mIcons = icons;
      mSubItemTitleCharSequences = itemSubTitles;
      mClickListener = onClickListener;
      return this;
  }
  public BasicListDialog setTitle(String title)
  {
      mTitle = title;
      return this;
  }
  /**
   * 设置弹起样式
   * @param bouceMode
   * @return
   */
  public BasicListDialog setBounceMode(BounceMode bouceMode){
	  mBounceMode=bouceMode;
	  return this;
  }
  /**
   * showdialog<BR>
   */
  public void show()
  {
      mBasicDialog.show();
  }
  
  /**
   * 关闭dialog<BR>
   */
  public void dismiss()
  {
	  mBasicDialog.dismiss();
  }
  public void resizeDialogSize(int width,int height){
	  mBasicDialog.show();
	  Window window = mBasicDialog.getWindow();
      //重新设置内容面板的宽度，替代默认宽度256dp
      LinearLayout contentPanel = (LinearLayout) window.findViewById(R.id.contentPanel);
      LayoutParams vlp = contentPanel.getLayoutParams();
      vlp.width = width;
      contentPanel.setLayoutParams(vlp);
  }
  /**
   * 是否显示对话框
   * @return
   */
  public boolean isShowing(){
	  return mBasicDialog.isShowing();
  }
  public enum BounceMode{
	  /**
       * 顶部弹起
       */
      TOP,
      /**
       * 中间弹起
       */
      CENTER,
      
      /**
       * 底部弹起
       */
      BOTTOM,
  }
   class BasicListDialogAdapter extends BaseAdapter{
    private Context mContext;
	@Override
	public int getCount() {
		return mItemTitleSequences.length;
	}

	@Override
	public Object getItem(int position) {
		return mItemTitleSequences[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=mInflater.inflate(R.layout.dialog_list_item, null);
		TextView titleTextView=(TextView) convertView.findViewById(R.id.dialog_item_text);
		titleTextView.setText(mItemTitleSequences[position]);
		if(null!=mEnable){
			titleTextView.setEnabled(mEnable[position]);
		}
		if(null!=mIcons){
			ImageView icon=(ImageView) convertView.findViewById(R.id.dialog_item_icon);
			icon.setVisibility(View.VISIBLE);
			icon.setImageResource(mIcons[position]);
		}
		if(null!=mSubItemTitleCharSequences){
			TextView mSubTextView=(TextView) convertView.findViewById(R.id.dialog_item_sub_text);
			mSubTextView.setText(mSubItemTitleCharSequences[position]);
			if(null!=mEnable){
				mSubTextView.setEnabled(mEnable[position]);
			}
		}
		return convertView;
	}
	   
   }  
}
