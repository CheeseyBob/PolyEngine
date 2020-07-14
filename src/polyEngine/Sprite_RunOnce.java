package polyEngine;

import java.awt.image.BufferedImage;

public class Sprite_RunOnce extends Sprite {
	
	public Sprite_RunOnce(BufferedImage[] imageList, double timePerFrame){
		super(imageList, timePerFrame);
	}
	
	public Sprite_RunOnce(BufferedImage spriteSheet, int hFrames, int vFrames, double timePerFrame){
		this(spriteSheetToAnimation(spriteSheet, spriteSheet.getWidth()/hFrames, spriteSheet.getHeight()/vFrames, hFrames, vFrames), timePerFrame);
	}
	
	public Sprite_RunOnce(BufferedImage spriteSheet, int frameWidth, int frameHeight, int hFrames, int vFrames, double timePerFrame){
		this(spriteSheetToAnimation(spriteSheet, frameWidth, frameHeight, hFrames, vFrames), timePerFrame);
	}
	
	@Override
	public void step(double tpf) {
		if(currentFrame + 1 < imageList.length){
			super.step(tpf);
		}
	}
	
	@Override
	public Sprite_RunOnce clone(){
		return new Sprite_RunOnce(imageList, timePerFrame);
	}
}