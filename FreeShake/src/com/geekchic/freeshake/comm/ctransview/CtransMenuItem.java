package com.geekchic.freeshake.comm.ctransview;

import java.util.HashMap;
import java.util.Map;

import android.view.View;
import android.view.animation.Animation;

public class CtransMenuItem
{
    private int index;
    private View itemView;
    private View cloneView;
    private Map<CtransMenu.CTRANS_ANIMI_MODE,Animation> itemAnimationMap;
    private int finalX;
    private int finalY;
    public CtransMenuItem(int index){
        this.index=index;
        itemAnimationMap=new HashMap<CtransMenu.CTRANS_ANIMI_MODE, Animation>();
    }
    public CtransMenuItem(int index,View itemView,View cloneView){
        this.index=index;
        this.itemView=itemView;
        this.cloneView=cloneView;
        itemAnimationMap=new HashMap<CtransMenu.CTRANS_ANIMI_MODE, Animation>();
    }
    public View getItemView()
    {
        return itemView;
    }
    public View getCloneView()
    {
        return cloneView;
    }
    public int getFinalX()
    {
        return finalX;
    }
    public void setFinalX(int finalX)
    {
        this.finalX = finalX;
    }
    public int getFinalY()
    {
        return finalY;
    }
    public void setFinalY(int finalY)
    {
        this.finalY = finalY;
    }

    public void setItemView(View itemView)
    {
        this.itemView = itemView;
    }
    public void setCloneView(View cloneView)
    {
        this.cloneView = cloneView;
    }
    public int getIndex()
    {
        return index;
    }
    public void setIndex(int index)
    {
        this.index = index;
    }
    public void addItemAnimation(CtransMenu.CTRANS_ANIMI_MODE mode,Animation animation){
        this.itemAnimationMap.put(mode, animation);
    }
    public Animation getItemAnimation(CtransMenu.CTRANS_ANIMI_MODE mode){
       return itemAnimationMap.get(mode);
    }
    
}
