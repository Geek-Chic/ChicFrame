package com.geekchic.freeshake.comm.ctransview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.geekchic.freeshake.R;
import com.geekchic.freeshake.comm.ctransview.CtransMenu.CTRANS_ANIMI_MODE;

public class CtransAdapter
{
    private static final int DEFAULT_SATELLITE_DISTANCE = 180;
    private static final boolean DEFAULT_CLOSE_ON_CLICK = true;
    private static final int DEFAULT_EXPAND_DURATION = 300;
    private int mCtransDistance = DEFAULT_SATELLITE_DISTANCE; 
    private int expandDuration = DEFAULT_EXPAND_DURATION;
    private boolean closeItemsOnClick = DEFAULT_CLOSE_ON_CLICK;
    private List<CtransMenuItem> mCtransMenuItems;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    
    public CtransAdapter(Context pContext,ArrayList<CtransMenuItem> pCtransMenuItems)
    {
        this.mContext=pContext;
        this.mCtransMenuItems=pCtransMenuItems;
        mCtransMenuItems=new ArrayList<CtransMenuItem>();
        mLayoutInflater=LayoutInflater.from(mContext);
        addItems(pCtransMenuItems);
    } 
    public void addItems(List<CtransMenuItem> items){
        this.mCtransMenuItems.addAll(items);
        float[] degrees=getDegrees(mCtransMenuItems.size(),360);
        int index=0;
        for(CtransMenuItem menuItem:mCtransMenuItems){
            int finalX=CtransAnimationCreator.getTranslateX(degrees[index], mCtransDistance);
            int finalY=CtransAnimationCreator.getTranslateY(degrees[index], mCtransDistance);
            View itemView =mLayoutInflater
                    .inflate(R.layout.ctrans_item,null, false);
            View cloneView = mLayoutInflater
                    .inflate(R.layout.ctrans_item, null, false);
            itemView.setTag(menuItem.getIndex());
            cloneView.setVisibility(View.GONE);
            itemView.setVisibility(View.GONE);
            cloneView.setTag(Integer.valueOf(menuItem.getIndex()));
            FrameLayout.LayoutParams layoutParams=getLayoutParams(cloneView);
            menuItem.setFinalX(finalX);
            menuItem.setFinalY(finalY);
            
            addInOutAnimation(menuItem,menuItem.getIndex(),finalX, finalY);
            index++;
        }
    }
    private void addInOutAnimation(CtransMenuItem item,int index,int finalx,int finaly){
        Animation inAnimation=CtransAnimationCreator.createItemInAnimation(mContext, index, expandDuration,finalx, finaly);
        Animation outAnimation=CtransAnimationCreator.createItemOutAnimation(mContext, index, expandDuration,finalx, finaly);
        Animation circleAnimation=CtransAnimationCreator.createCircleRotateAnimation(mContext, expandDuration, 1,finalx, finaly);
        item.addItemAnimation(CTRANS_ANIMI_MODE.CTRANS_ITEM_IN, inAnimation);
        item.addItemAnimation(CTRANS_ANIMI_MODE.CTRANS_ITEM_OUT, outAnimation);
        item.addItemAnimation(CTRANS_ANIMI_MODE.CTRANS_ITEM_CIRCLE,circleAnimation);
        
    }
    private float[] getDegrees(int count,int totalDegrees)
    {
        // TODO Auto-generated method stub
        if(count<1){
            return new float[]{};
        }
        float[] result=new float[count];
        float delta=totalDegrees/count;
        for(int index=0;index<count;index++){
            result[index]=index*delta;
        }
        
        return result;
    }
    private static FrameLayout.LayoutParams getLayoutParams(View view){
        return (FrameLayout.LayoutParams) view.getLayoutParams();
    }
    public int getCount()
    {
        return mCtransMenuItems.size();
    }

    public Object getItem(int index)
    {
        return mCtransMenuItems.get(index);
    }
    
    public long getItemId(int id)
    {
        return id;
    }
    public List<CtransMenuItem> getCtransMenuItems(){
        return mCtransMenuItems;
    }
//    public View getView(int index, View contentView, ViewGroup viewGroup)
//    {
//        Holder holder;
//        if(contentView==null){
//            holder=new Holder();
//            contentView=mLayoutInflater.inflate(R.layout.ctrans_item, null);
//            holder.imageView=(ImageView) contentView.findViewById(R.id.ctrans_item_iv);
//            holder.imageView=(ImageView) contentView.findViewById(R.id.ctrans_item_iv);
//            contentView.setTag(holder);
//        }else{
//           holder=(Holder) contentView.getTag();
//        }
//        return contentView;
//    }
//    class Holder{
//        public ImageView imageView;
//    }
    
}
