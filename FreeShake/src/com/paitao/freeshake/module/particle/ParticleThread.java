package com.paitao.freeshake.module.particle;


public class ParticleThread extends Thread
{
    boolean isRunning;
    ParticleView particleView;
    int sleepSpan=80;
    double time=0;
    double span=0.15;
    public ParticleThread(ParticleView particleView){
        this.isRunning=true;
        this.particleView=particleView;
    }
    @Override
    public void run()
    {
        // TODO Auto-generated method stub
        while (isRunning)
        {
//        particleView.particleSet.addParticle(5, time); 
//        List<Particle> tempParticles = particleView.particleSet.particles;
//        int count=tempParticles.size();
//        for(int i=0;i<count;i++){
//            Particle particle=tempParticles.get(i);
//            double timeSpan=time-particle.startTime;
//            //计算X坐标  
//            int tempx = (int)(particle.startX+particle.velocity_h*timeSpan);  
//            //计算Y坐标  
////            int tempy = (int)(particle.startY+particle.velocity_v*timeSpan+4.9*timeSpan*timeSpan);
//              int tempy=(int) (particle.startX+particle.velocity_v*timeSpan);
//            if(tempy>300){
//                tempParticles.remove(particle);
//                count=tempParticles.size();
//            }
//            particle.x=tempx;
//            particle.y=tempy;
//        }
          particleView.particleSet.update(5, time);
          time+=span;
       try
    {
        Thread.sleep(sleepSpan);
    }
    catch (InterruptedException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
       super.run();
    }
    }
    
}
