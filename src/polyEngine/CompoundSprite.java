package polyEngine;

import java.awt.Graphics2D;

public class CompoundSprite extends Sprite {
	public Sprite[] spriteList;
	
	public CompoundSprite(Sprite sprite1, Sprite sprite2){
		super(sprite1.imageList, sprite1.timePerFrame);
		this.spriteList = new Sprite[] {sprite1, sprite2};
	}
	
	public CompoundSprite(Sprite[] spriteList){
		super(spriteList[0].imageList, spriteList[0].timePerFrame);
		this.spriteList = new Sprite[spriteList.length];
		for(int i = 0; i < spriteList.length; i ++){
			this.spriteList[i] = spriteList[i];
		}
	}
	
	@Override
	public void step(double tpf){
		super.step(tpf);
		for(Sprite sprite: spriteList){
			if(sprite != null){
				sprite.step(tpf);
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		for(Sprite sprite: spriteList){
			if(sprite != null){
				sprite.draw(g);
			}
		}
	}
	
	public CompoundSprite clone(){
		return new CompoundSprite(spriteList);
	}
}