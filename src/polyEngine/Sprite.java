package polyEngine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import files.ImageHandler;

/**
 * NOTE : Any class extending Sprite should override the clone method!
 */
public class Sprite {
	protected BufferedImage[] imageList;
	private int imOffsetW, imOffsetH;
	protected int currentFrame = 0;
	protected double frameTimeRemaining;
	protected double timePerFrame;
	
	protected Sprite(){
		
	}
	
	public Sprite(BufferedImage image){
		this(new BufferedImage[] {image}, 0);
	}
	
	public Sprite(String imageFile){
		this(new BufferedImage[] {ImageHandler.loadImage(imageFile)}, 0);
	}
	
	public Sprite(BufferedImage frame1, BufferedImage frame2, double timePerFrame){
		this(new BufferedImage[] {frame1, frame2}, timePerFrame);
	}
	
	public Sprite(BufferedImage[] imageList, double timePerFrame){
		this.imageList = imageList;
		this.timePerFrame = this.frameTimeRemaining = timePerFrame;
		this.imOffsetW = -imageList[0].getWidth()/2;
		this.imOffsetH = -imageList[0].getHeight()/2;
	}
	
	public Sprite(BufferedImage spriteSheet, int hFrames, int vFrames, double timePerFrame){
		this(spriteSheetToAnimation(spriteSheet, spriteSheet.getWidth()/hFrames, spriteSheet.getHeight()/vFrames, hFrames, vFrames), timePerFrame);
	}
	
	public Sprite(BufferedImage spriteSheet, int frameWidth, int frameHeight, int hFrames, int vFrames, double timePerFrame){
		this(spriteSheetToAnimation(spriteSheet, frameWidth, frameHeight, hFrames, vFrames), timePerFrame);
	}
	
	protected static BufferedImage[] spriteSheetToAnimation(BufferedImage spriteSheet, int frameWidth, int frameHeight, int hFrames, int vFrames){
		BufferedImage[] anim = new BufferedImage[hFrames*vFrames];
		for(int y = 0; y < vFrames; y ++){
			for(int x = 0; x < hFrames; x ++){
				anim[y*hFrames + x] = spriteSheet.getSubimage(x*frameWidth, y*frameHeight, frameWidth, frameHeight);
			}
		}
		return anim;
	}
	
	public static Sprite[] loadSpriteList(String filename, int xTiles, int yTiles){
		BufferedImage[] imageList = ImageHandler.loadTiledImage(filename, xTiles, yTiles);
		Sprite[] spriteList = new Sprite[imageList.length];
		for(int i = 0; i < imageList.length; i ++){
			spriteList[i] = new Sprite(imageList[i]);
		}
		return spriteList;
	}
	
	public void step(double tpf) {
		if(timePerFrame > 0){
			frameTimeRemaining -= tpf;
			while(frameTimeRemaining < 0){
				frameTimeRemaining += timePerFrame;
				currentFrame = (currentFrame + 1) % imageList.length;
			}
		}
	}
	
	@Override
	public Sprite clone(){
		return new Sprite(imageList, timePerFrame);
	}
	
	public void draw(double x, double y, Graphics2D g) {
		g.translate(x, y);
		draw(g);
		g.translate(-x, -y);
	}
	
	public void draw(int x, int y, Graphics2D g) {
		g.translate(x, y);
		draw(g);
		g.translate(-x, -y);
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(imageList[currentFrame], null, imOffsetW, imOffsetH);
	}
	
	public int getWidth(){
		return imageList[0].getWidth();
	}
	
	public int getHeight(){
		return imageList[0].getHeight();
	}
	
	public double getTimePerFrame(){
		return timePerFrame;
	}
	
	public int getNumberOfFrames(){
		return imageList.length;
	}
	
	public double getAnimationLength(){
		return getTimePerFrame()*getNumberOfFrames();
	}
	
	/**
	 *  Adjusts the offset for this sprite and returns itself.
	 */
	public Sprite setSpriteCentreLocation(int x, int y){
		imOffsetW = x - imageList[0].getWidth()/2;
		imOffsetH = y - imageList[0].getHeight()/2;
		return this;
	}
}