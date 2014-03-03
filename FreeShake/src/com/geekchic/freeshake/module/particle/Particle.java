package com.geekchic.freeshake.module.particle;

public class Particle
{
    public static final int STATE_ALIVE = 0;    // particle is alive
    public static final int STATE_DEAD = 1;     // particle is dead
    int color;
    int radius;
    int state;
    double velocity_v;
    double velocity_h;
    int startX;
    int startY;
    int x;
    int y;
    double startTime;
    public Particle(int color,int radius,double velocity_v,double velocity_h,int x,int y,double startTime){
        this.color=color;
        this.radius=radius;
        this.velocity_h=velocity_h;
        this.velocity_v=velocity_v;
        this.startX=x;
        this.startY=y;
        this.x=x;
        this.y=y;
        this.startTime=startTime;
        state=STATE_DEAD;
    }
    
}
