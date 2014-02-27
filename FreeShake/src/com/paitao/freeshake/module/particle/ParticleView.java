package com.paitao.freeshake.module.particle;

import java.util.List;

import com.paitao.freeshake.comm.GlobalUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ParticleView extends SurfaceView implements SurfaceHolder.Callback
{
    public final static int DIE_OUT_LINE=300;
    BasicParticleSet particleSet;
    ParticleThread particleThread;
    ParticleDrawThread particleDrawThread;
    Point particleOccPoint;
    public ParticleView(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public ParticleView(Context context,AttributeSet style){
        super(context, style);
        init(context);
    }
   private void init(Context context){
       this.getHolder().addCallback(this);
       particleDrawThread=new ParticleDrawThread(this, getHolder());
       DisplayMetrics dm=GlobalUtil.getScreenResolution(context);
       particleOccPoint=new Point(dm.widthPixels/2,dm.heightPixels/2);
        particleSet=new ExplosionParticleSet(particleOccPoint);
        particleThread=new ParticleThread(this);
   }
    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        // TODO Auto-generated method stub
        if(!particleDrawThread.isAlive()){
            particleDrawThread.start();
        }
        if(!particleThread.isAlive()){
            particleThread.start();
        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height)
    {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        // TODO Auto-generated method stub
        particleDrawThread.isRunning=false;
        particleDrawThread=null;
    }
    public void doDraw(Canvas canvas){
        particleSet.doDraw(canvas);
    }
    
}
