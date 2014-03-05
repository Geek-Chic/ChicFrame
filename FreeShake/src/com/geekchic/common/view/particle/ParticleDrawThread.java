package com.geekchic.common.view.particle;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class ParticleDrawThread extends Thread
{
    private static final String TAG="ParticleDrawThread";
    ParticleView particleView;
    SurfaceHolder surfaceHolder;
    boolean isRunning;
    int sleepSpan=15;
    long start=System.nanoTime();
    int count=0;
    public ParticleDrawThread(ParticleView particleView,SurfaceHolder surfaceHolder)
    {
        // TODO Auto-generated constructor stub
        this.isRunning=true;
        this.particleView=particleView;
        this.surfaceHolder=surfaceHolder;
    }
    @Override
    public void run()
    {
        // TODO Auto-generated method stub
        super.run();
        Canvas canvas=null;
        while(isRunning){
            try
            {
                canvas=surfaceHolder.lockCanvas();
                synchronized (surfaceHolder)
                {
                  particleView.doDraw(canvas);
                }
            }
            catch (Exception e)
            {
                // TODO: handle exception
                Log.e(TAG, e.toString());
            }finally{
                if(canvas!=null){
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            this.count++;
            if(count==20){
                count=0;
                long tempStamp=System.nanoTime();
                long span=tempStamp-start;
                start=tempStamp;
                double fps = Math.round(100000000000.0/span*20)/100.0;
            }
            try
            {
                Thread.sleep(sleepSpan);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}
