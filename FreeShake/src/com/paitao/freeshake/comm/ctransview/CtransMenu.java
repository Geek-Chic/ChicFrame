package com.paitao.freeshake.comm.ctransview;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.paitao.comm.GlobalUtil;
import com.paitao.freeshake.R;
import com.paitao.freeshake.module.particle.ParticleView;

public class CtransMenu extends FrameLayout
{
    public enum CTRANS_ANIMI_MODE
    {
        CTRANS_ITEM_IN, CTRANS_ITEM_OUT, CTRANS_MAIN_CLICK, CTRANS_ITEM_CLICK, CTRANS_ITEM_CIRCLE
    }
    
    private Animation mMainAnimation;
    
    private ImageView mMainImageView;
    
    private ParticleView mParticleView;
    
    private boolean mRotated = false;
    
    private int mMeasureDiff = 0;
    
    private CtransAdapter mCtransItemAdapter;
    
    private Context mContext;
    
    private AtomicBoolean mAnimiAtomicBoolean;
    
    private Point mMainPoint;
    
    private List<CtransMenuItem> mMenuViewLists;
    
    public CtransMenu(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
        init(context, null, 0);
    }
    
    public CtransMenu(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init(context, attrs, 0);
    }
    
    @SuppressLint("NewApi")
    public CtransMenu(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init(context, attrs, defStyle);
    }
    
    private void init(Context context, AttributeSet attrs, int defStyle)
    {
        // TODO Auto-generated method stub
        this.mContext = context;
        View view = LayoutInflater.from(context)
                .inflate(R.layout.ctrans_center_view, this, true);
        mMainImageView = (ImageView) view.findViewById(R.id.ctran_cetner_iv);
        mMainImageView.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                //                Toast.makeText(mContext, "点击", Toast.LENGTH_SHORT).show();
                mMainImageView.startAnimation(mMainAnimation);
                CtransMenu.this.circleItems();
                log();
            }
        });
        if (attrs != null)
        {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.SatelliteMenu,
                    defStyle,
                    0);
            typedArray.recycle();
        }
        mMainAnimation = CtransAnimationCreator.createMainRotateAnimation(context,
                500,
                10);
        mAnimiAtomicBoolean = new AtomicBoolean(false);
        mMenuViewLists = new ArrayList<CtransMenuItem>();
        
    }
    
    private static FrameLayout.LayoutParams getLayoutParams(View view, int x,
            int y)
    {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.leftMargin = x;
        layoutParams.bottomMargin = y;
        return layoutParams;
    }
    
    public void setAdapter(CtransAdapter adapter)
    {
        this.mCtransItemAdapter = adapter;
        this.mMenuViewLists = mCtransItemAdapter.getCtransMenuItems();
        notifityDateSet();
        registerItemAnimation();
    }
    
    public void notifityDateSet()
    {
        int index = 0;
        for (CtransMenuItem item : mMenuViewLists)
        {
            View itemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.ctrans_item, this, false);
            View cloneView = LayoutInflater.from(getContext())
                    .inflate(R.layout.ctrans_item, this, false);
            itemView.setTag(item.getIndex());
            FrameLayout.LayoutParams itemLayoutParams = getLayoutParams(cloneView,
                    item.getFinalX(),
                    item.getFinalY());
            cloneView.setLayoutParams(itemLayoutParams);
            this.addView(itemView);
            this.addView(cloneView);
            cloneView.setOnClickListener(ctransItemClickListener);
            cloneView.setVisibility(View.GONE);
            itemView.setVisibility(View.INVISIBLE);
            mMenuViewLists.get(index).setItemView(itemView);
            mMenuViewLists.get(index).setCloneView(cloneView);
            index++;
        }
    }
    
    private void registerItemAnimation()
    {
        int index = 0;
        for (CtransMenuItem menuItem : mMenuViewLists)
        {
            CtransMenuItem item = mMenuViewLists.get(index);
            Animation inAnimation = item.getItemAnimation(CTRANS_ANIMI_MODE.CTRANS_ITEM_IN);
            Animation outAnimation = item.getItemAnimation(CTRANS_ANIMI_MODE.CTRANS_ITEM_OUT);
            Animation circleAnimation = item.getItemAnimation(CTRANS_ANIMI_MODE.CTRANS_ITEM_CIRCLE);
            inAnimation.setAnimationListener(new CtransItemShowAnimationListener(
                    item, true, mMenuViewLists));
            outAnimation.setAnimationListener(new CtransItemShowAnimationListener(
                    item, false, mMenuViewLists));
            circleAnimation.setAnimationListener(new CtransItemRotateAnimationListener(
                    item, mMenuViewLists));
            index++;
        }
    }
    
    //    @Override
    //    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    //    {
    //        // TODO Auto-generated method stub
    //        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //        recalculateMeasureDiff();
    //        int totalHeight=mMainImageView.getHeight()+satelliteDistance*2+mMeasureDiff*2;
    //        int totalWidth=mMainImageView.getWidth()+satelliteDistance*2+mMeasureDiff*2;
    //        setMeasuredDimension(totalWidth, totalHeight);
    //    }
    //    private void recalculateMeasureDiff() {
    //        int itemWidth = 0;
    //        if (mMenuViewLists.size() > 0) {
    //            itemWidth = mMenuViewLists.get(0).getItemView().getWidth();
    //        }
    //        mMeasureDiff = Float.valueOf(satelliteDistance * 0.2f).intValue()
    //                + itemWidth;
    //    }
    
    public void showDismissItem()
    {
        if (!mRotated)
        {
            openItems();
        }
        else
        {
            closeItems();
        }
        
    }
    
    private void openItems()
    {
        if (mAnimiAtomicBoolean.compareAndSet(false, true))
        {
            if (!mRotated)
            {
                for (CtransMenuItem item : mMenuViewLists)
                {
                    Animation outAnimation = item.getItemAnimation(CTRANS_ANIMI_MODE.CTRANS_ITEM_OUT);
                    item.getItemView().startAnimation(outAnimation);
                }
            }
            mRotated = !mRotated;
        }
    }
    
    private void closeItems()
    {
        
        if (mAnimiAtomicBoolean.compareAndSet(true, false))
        {
            if (mRotated)
            {
                for (CtransMenuItem item : mMenuViewLists)
                {
                    Animation inAnimation = item.getItemAnimation(CTRANS_ANIMI_MODE.CTRANS_ITEM_IN);
                    item.getItemView().startAnimation(inAnimation);
                }
            }
            mRotated = !mRotated;
        }
        
    }
    
    public void circleItems()
    {
        if (mAnimiAtomicBoolean.compareAndSet(true, true)
                || mAnimiAtomicBoolean.compareAndSet(false, true))
        {
            for (CtransMenuItem item : mMenuViewLists)
            {
                Animation circleAnimation = item.getItemAnimation(CTRANS_ANIMI_MODE.CTRANS_ITEM_CIRCLE);
                item.getItemView().startAnimation(circleAnimation);
            }
            mRotated = true;
        }
    }
    
    private static class CtransItemShowAnimationListener implements
            AnimationListener
    {
        private WeakReference<CtransMenuItem> mViewReference;
        
        private boolean isInAnimation;
        
        private List<CtransMenuItem> mViewToItemMap;
        
        public CtransItemShowAnimationListener(CtransMenuItem itemMap,
                boolean isIn, List<CtransMenuItem> menuMapLists)
        {
            this.mViewReference = new WeakReference<CtransMenuItem>(itemMap);
            this.isInAnimation = isIn;
            this.mViewToItemMap = menuMapLists;
        }
        
        @Override
        public void onAnimationStart(Animation animation)
        {
            // TODO Auto-generated method stub
            if (mViewReference != null)
            {
                CtransMenuItem itemMap = mViewReference.get();
                if (itemMap != null)
                {
                    itemMap.getItemView().setVisibility(View.VISIBLE);
                    itemMap.getCloneView().setVisibility(View.GONE);
                }
            }
        }
        
        @Override
        public void onAnimationEnd(Animation animation)
        {
            // TODO Auto-generated method stub
            if (mViewReference != null)
            {
                CtransMenuItem itemMap = mViewReference.get();
                if (itemMap != null)
                {
                    if (isInAnimation)
                    {
                        itemMap.getItemView().setVisibility(View.GONE);
                        itemMap.getCloneView().setVisibility(View.GONE);
                    }
                    else
                    {
                        itemMap.getItemView().setVisibility(View.GONE);
                        itemMap.getCloneView().setVisibility(View.VISIBLE);
                    }
                }
            }
            
        }
        
        @Override
        public void onAnimationRepeat(Animation animation)
        {
            // TODO Auto-generated method stub
            
        }
    }
    
    //旋转动画
    private static class CtransItemRotateAnimationListener implements
            AnimationListener
    {
        private WeakReference<CtransMenuItem> mViewReference;
        
        private List<CtransMenuItem> mViewToItemMap;
        
        public CtransItemRotateAnimationListener(CtransMenuItem itemMap,
                List<CtransMenuItem> menuMapLists)
        {
            mViewReference = new WeakReference<CtransMenuItem>(itemMap);
            this.mViewToItemMap = menuMapLists;
        }
        
        @Override
        public void onAnimationStart(Animation animation)
        {
            // TODO Auto-generated method stub
            if (mViewReference != null)
            {
                CtransMenuItem itemMap = mViewReference.get();
                if (itemMap != null)
                {
                    itemMap.getItemView().setVisibility(View.VISIBLE);
                    itemMap.getCloneView().setVisibility(View.GONE);
                }
            }
        }
        
        @Override
        public void onAnimationEnd(Animation animation)
        {
            // TODO Auto-generated method stub
            if (mViewReference != null)
            {
                CtransMenuItem itemMap = mViewReference.get();
                if (itemMap != null)
                {
                    itemMap.getItemView().setVisibility(View.GONE);
                    itemMap.getCloneView().setVisibility(View.VISIBLE);
                }
            }
            
        }
        
        @Override
        public void onAnimationRepeat(Animation animation)
        {
            // TODO Auto-generated method stub
            
        }
    }
    
    private OnClickListener ctransItemClickListener = new OnClickListener()
    {
        
        @Override
        public void onClick(View v)
        {
            // TODO Auto-generated method stub
            for (CtransMenuItem item : mMenuViewLists)
            {
                if (item.getCloneView() == v)
                {
                    Toast.makeText(mContext,
                            "你点击了" + item.getIndex(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    
    public interface CtransClickListener
    {
        public void mEventOccured(int id);
        
        public void onRotateOccured();
    }
    
    private void log()
    {
        for (CtransMenuItem item : mMenuViewLists)
        {
            int[] location = new int[2];
            item.getCloneView().getLocationOnScreen(location);
            mMainPoint = new Point(location[0], location[1]);
            System.out.println(mMainPoint.toString());
            DisplayMetrics dm = GlobalUtil.getScreenResolution(mContext);
            System.out.println(dm.widthPixels + ":" + dm.heightPixels);
        }
    }
    
}
