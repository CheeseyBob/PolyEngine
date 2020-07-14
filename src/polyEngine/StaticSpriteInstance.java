package polyEngine;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class StaticSpriteInstance implements Drawable, Location {
	
	protected double x, y;
	protected double absoluteZDepth;
	protected boolean hasDynamicZDepth;
	protected Sprite sprite;
	
	public StaticSpriteInstance(double x, double y, double absoluteZDepth, boolean hasDynamicZDepth, Sprite sprite){
		setSprite(sprite);
		setLocation(x, y);
		this.absoluteZDepth = absoluteZDepth;
		this.hasDynamicZDepth = hasDynamicZDepth;
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(sprite != null){
			int xInt = (int)x;
			int yInt = (int)y;
			g.translate(xInt, yInt);
			sprite.draw(g);
			g.translate(-xInt, -yInt);
		}
	}
	
	@Override
	public Point2D getLocation() {
		return new Point2D.Double(x, y);
	}
	
	@Override
	public double getX() {
		return x;
	}
	
	@Override
	public double getY() {
		return y;
	}
	
	@Override
	public double getZDepth() {
		if(hasDynamicZDepth){
			return y - PolyEngine.viewY + absoluteZDepth;
		} else {
			return absoluteZDepth;
		}
	}
	
	public void setLocation(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void setSprite(Sprite sprite){
		if(sprite != null){
			this.sprite = sprite.clone();
		} else {
			this.sprite = null;
		}
	}
	
	public void setZDepth(double absoluteZDepth, boolean hasDynamicZDepth){
		this.absoluteZDepth = absoluteZDepth;
		this.hasDynamicZDepth = hasDynamicZDepth;
	}
}