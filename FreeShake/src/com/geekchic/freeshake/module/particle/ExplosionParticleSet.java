package com.geekchic.freeshake.module.particle;

import android.R.integer;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

public class ExplosionParticleSet extends BasicParticleSet
{
    private final static int MAX_DISTANCE=200;
    private final static int MAX_SPEED=10;
    Point occPoint= new Point(0, 0);
    public ExplosionParticleSet(Point occPoint){
        this.occPoint=occPoint;
    }
    @Override
    public void addParticle(int count, double startTime)
    {
        // TODO Auto-generated method stub
        for(int i=0;i<count;i++){
            int color=getColor(i);
            int radius=1;
            double velocity_v=rndDbl(0,MAX_SPEED*2)-MAX_SPEED;
            double velocity_h=rndDbl(0, MAX_SPEED*2)-MAX_SPEED;
            if(velocity_v*velocity_v+velocity_h*velocity_h>MAX_SPEED*MAX_SPEED){
                velocity_v*=0.7;
                velocity_h*=0.7;
            }
            Particle particle=new Particle(color, radius, velocity_v, velocity_h, occPoint.x, occPoint.y, startTime);
            particles.add(particle);
        }
      
    }

    @Override
    public void update(int addCount, double curTime)
    {
        // TODO Auto-generated method stub
        addParticle(addCount, curTime);
        int count=particles.size();
        for(int i=0;i<count;i++){
            Particle particle=particles.get(i);
            double timeSpan=curTime-particle.startTime;
            particle.x+=particle.velocity_h;
            particle.y+=particle.velocity_v;
            if(particle.state==Particle.STATE_DEAD){
                particles.remove(particle);
                count=particles.size();
            }
        }
    }

    @Override
    public void doDraw(Canvas canvas)
    {
        // TODO Auto-generated method stub
        canvas.drawColor(Color.BLACK);
        Paint paint=new Paint();
        int count=particles.size();
        for(int i=0;i<count;i++){
            Particle particle=particles.get(i);
            paint.setColor(particle.color);
            int tempx=particle.x;
            int tempy=particle.y;
            int tempr=particle.radius;
            int color=particle.color>>24;
            color-=2;
            if(color<0){
                particle.state=Particle.STATE_DEAD;
            }else{
                particle.color = (particle.color & 0x00ffffff) + (color << 24);
                paint.setAlpha(color);
            }
            particle.state=Particle.STATE_ALIVE;
             RectF ovalF=new RectF(tempx, tempy, tempx+2*tempr, tempy+2*tempr);
             canvas.drawOval(ovalF, paint);
        
    }
   }
    
}
