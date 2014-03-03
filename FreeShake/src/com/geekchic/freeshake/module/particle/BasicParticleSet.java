package com.geekchic.freeshake.module.particle;

import java.util.List;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;

public abstract class BasicParticleSet
{
    List<Particle> particles;
    public BasicParticleSet()
    {
        // TODO Auto-generated constructor stub
        particles=new Vector<Particle>();
    }
    public int getColor(int i)
    {
        // TODO Auto-generated method stub
        int color=Color.RED;
        if(i%4==1){
            color=Color.BLUE;
        }else if(i%4==2){
            color=Color.YELLOW;
        }else if(i%4==3){
            color=Color.GRAY;
        }
        return color;
    }
    static int rndInt(int min, int max) {
              return (int) (min + Math.random() * (max - min + 1));
         }
     static double rndDbl(double min, double max) {
              return min + (max - min) * Math.random();
      }
    public abstract void addParticle(int count,double startTime);
    public abstract void update(int addCount,double startTime);
    public abstract void doDraw(Canvas canvas);
}
