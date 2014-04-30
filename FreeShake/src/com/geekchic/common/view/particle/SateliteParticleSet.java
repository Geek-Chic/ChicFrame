package com.geekchic.common.view.particle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

public class SateliteParticleSet extends BasicParticleSet
{
    Point occPoint=new Point(0,0);
    public SateliteParticleSet(Point occPoint){
        this.occPoint=occPoint;
    }
    public void addParticle(int count,double startTime){
        for(int i=0;i<count;i++){
            int color=getColor(i);
            int radius=1;
            double velocity_v=-30+10*(Math.random());
            double velocity_h=10-20*(Math.random());

            int x=occPoint.x;
            int y=(int)(occPoint.y-10*(Math.random()));
            Particle particle=new Particle(color, radius, velocity_v, velocity_h, x, y, startTime);
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
            //����X���  
            int tempx = (int)(particle.startX+particle.velocity_h*timeSpan);  
            //����Y���  
            int tempy = (int)(particle.startY+particle.velocity_v*timeSpan+4.9*timeSpan*timeSpan);
            if(tempy>occPoint.y+300){
                particles.remove(particle);
                particle.state=Particle.STATE_DEAD;
                count=particles.size();
            }
            particle.x=tempx;
            particle.y=tempy;
        }
    }
    @Override
    public void doDraw(Canvas canvas)
    {
        // TODO Auto-generated method stub
        canvas.drawColor(Color.BLACK);
        Paint paint=new Paint();
        for(Particle particle:particles){
            paint.setColor(particle.color);
            int tempx=particle.x;
            int tempy=particle.y;
            int tempr=particle.radius;
             particle.state=Particle.STATE_ALIVE;
             RectF ovalF=new RectF(tempx, tempy, tempx+2*tempr, tempy+2*tempr);
             canvas.drawOval(ovalF, paint);
        }
    
        
    }


    
}
