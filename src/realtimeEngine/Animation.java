package realtimeEngine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Animation {
   AnimationSequence sequence;
   BufferedImage currentImage = null;
   double cycleTime;
   double frameTimer = 0;
   
   public Animation(AnimationSequence sequence, double cycleTime){
      this.sequence = sequence;
      this.cycleTime = cycleTime;
      currentImage = sequence.animFrameList[0];
   }
   
   public void step(double tpf){
      frameTimer += tpf;
      while(frameTimer > cycleTime){
         frameTimer -= cycleTime;
      }
      int frame = (int)(sequence.frameCount*frameTimer/cycleTime);
      currentImage = sequence.getFrameImage(frame);
   }
   
   public void draw(Graphics2D g, int x, int y){
      g.drawImage(currentImage, x, y, null);
   }
}