package realtimeEngine;

import java.awt.image.BufferedImage;

public class AnimationSequence {
   BufferedImage[] animFrameList;
   int frameCount;
   
   public AnimationSequence(BufferedImage sourceImage, int xTiles, int yTiles){
      frameCount = xTiles*yTiles;
      animFrameList = new BufferedImage[frameCount];
      int tileWidth = sourceImage.getWidth()/xTiles;
      int tileHeight = sourceImage.getHeight()/yTiles;
      for(int x = 0; x < xTiles; x ++){
         for(int y = 0; y < yTiles; y ++){
            animFrameList[x+y*yTiles] = sourceImage.getSubimage(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
         }
      }
   }
   
   BufferedImage getFrameImage(int frame){
      return animFrameList[frame%frameCount];
   }
}