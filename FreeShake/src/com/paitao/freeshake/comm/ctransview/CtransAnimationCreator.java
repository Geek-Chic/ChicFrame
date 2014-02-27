package com.paitao.freeshake.comm.ctransview;

import com.paitao.freeshake.R;
import com.paitao.freeshake.R.drawable;

import android.Manifest.permission;
import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

public class CtransAnimationCreator
{
    /**
     * 创建Ctrans控件子菜单出现动画
     * @param pContext 上下文
     * @param pIndex 子菜单编号
     * @param pExpandDuration 动画持续时间
     * @param x
     * @param y
     * @return
     */
    public static Animation createItemInAnimation(Context context,int index,long expandDuration,int x,int y){
        RotateAnimation rotateAnimation=new RotateAnimation(720, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(context,R.anim.ctrans_item_in_rotate_interpolator);
        rotateAnimation.setDuration(expandDuration);
        
        TranslateAnimation translateAnimation=new TranslateAnimation(x, 0,y,0);
        long delay=250;
        if(expandDuration<=250){
            delay=expandDuration/3;
        }
        long duration=400;
        if((expandDuration-delay)>duration){
            duration=expandDuration-delay;
        }
        translateAnimation.setDuration(expandDuration);
        translateAnimation.setStartOffset(delay);
        translateAnimation.setInterpolator(context, R.anim.ctrans_item_anticipate_interpolator);
        
        AlphaAnimation alphaAnimation=new AlphaAnimation(1f, 0f);
        long alphaDuration=10;
        if(expandDuration<10){
            alphaDuration=expandDuration/10;
        }
        alphaAnimation.setDuration(alphaDuration);
        alphaAnimation.setStartOffset((delay+duration)-alphaDuration);
        
        AnimationSet animationSet=new AnimationSet(false);
        animationSet.setFillAfter(false);
        animationSet.setFillBefore(true);
        animationSet.setFillEnabled(true);
        
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(translateAnimation);
        
        animationSet.setStartOffset(30*index);
        animationSet.start();
        animationSet.startNow();
        return animationSet;
    }
    public static Animation createItemOutAnimation(Context pContext,int pIndex,long pExpandDuration,int x,int y){
        AlphaAnimation alphaAnimation=new AlphaAnimation(0f, 1f);
        long alphaDuration=60;
        if(pExpandDuration<60){
            alphaDuration=pExpandDuration/4;
        }
        alphaAnimation.setDuration(alphaDuration);
        alphaAnimation.setStartOffset(0);
        
        TranslateAnimation translateAnimation=new TranslateAnimation(0,x,0,y);
        translateAnimation.setStartOffset(0);
        translateAnimation.setDuration(pExpandDuration);        
        translateAnimation.setInterpolator(pContext, R.anim.ctrans_item_out_overshoot_interpolator);
       
        RotateAnimation rotateAnimation=new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        
        rotateAnimation.setInterpolator(pContext, R.anim.ctrans_item_out_rotate_interpolator);
        
        long duration = 100;
        if(pExpandDuration <= 150){
            duration = pExpandDuration / 3;
        }
        rotateAnimation.setDuration(pExpandDuration-duration);
        rotateAnimation.setStartOffset(duration);  
        
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setFillAfter(false);
        animationSet.setFillBefore(true);
        animationSet.setFillEnabled(true);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
//        animationSet.addAnimation(rotateAnimation);
        animationSet.setStartOffset(30*pIndex);
        return animationSet;
    }
    public static Animation createMainRotateAnimation(Context pContext,long pDuration,int pRepeatCount){
        RotateAnimation rotateAnimation=new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(pDuration);
        rotateAnimation.setFillAfter(false);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(pRepeatCount);
        return rotateAnimation;
        
    }
    public static Animation createCircleRotateAnimation(Context context,long duration,int mode,int x,int y){
        TranslateAnimation translateAnimation=new TranslateAnimation(0,x,0,y);
        translateAnimation.setStartOffset(0);
        translateAnimation.setDuration(100);    
        RotateAnimation rotateAnimation=new RotateAnimation(0, 360*8, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setStartOffset(100);
        rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        rotateAnimation.setDuration(duration*6-100);
        AnimationSet animationSet=new AnimationSet(false);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(rotateAnimation);
        return animationSet;
    }
    public static int getTranslateX(float degree, int distance){
        return Double.valueOf(distance * Math.cos(Math.toRadians(degree))).intValue();
    }
    
    public static int getTranslateY(float degree, int distance){
        return Double.valueOf(-1 * distance * Math.sin(Math.toRadians(degree))).intValue();
    }
}
