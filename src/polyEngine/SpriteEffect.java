package polyEngine;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class SpriteEffect implements Drawable, Location, Stepable {
	
	protected double x, y, rotation;
	protected double absoluteZDepth;
	protected boolean hasDynamicZDepth;
	protected Sprite sprite;
	protected double duration = Double.NaN;
	
	public SpriteEffect(double x, double y, double absoluteZDepth, boolean hasDynamicZDepth, double rotation, Sprite sprite, double duration){
		setSprite(sprite);
		setLocation(x, y);
		this.absoluteZDepth = absoluteZDepth;
		this.hasDynamicZDepth = hasDynamicZDepth;
		this.rotation = rotation;
		this.duration = duration;
	}
	
	public SpriteEffect(double x, double y, double absoluteZDepth, boolean hasDynamicZDepth, Sprite sprite, double duration){
		this(x, y, absoluteZDepth, hasDynamicZDepth, 0.0, sprite, duration);
	}
	
	/**
	 * This creates a sprite effect with unlimited duration.
	 */
	public SpriteEffect(double x, double y, double absoluteZDepth, boolean hasDynamicZDepth, double rotation, Sprite sprite){
		this(x, y, absoluteZDepth, hasDynamicZDepth, rotation, sprite, Double.NaN);
	}
	
	/**
	 * This creates a sprite effect with unlimited duration.
	 */
	public SpriteEffect(double x, double y, double absoluteZDepth, boolean hasDynamicZDepth, Sprite sprite){
		this(x, y, absoluteZDepth, hasDynamicZDepth, 0.0, sprite, Double.NaN);
	}

	@Override
	public void draw(Graphics2D g) {
		if(sprite != null){
			int xInt = (int)x;
			int yInt = (int)y;
			g.translate(xInt, yInt);
			g.rotate(rotation);
			sprite.draw(g);
			g.rotate(-rotation);
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

	@Override
	public void setLocation(double x, double y) {
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

	@Override
	public void step(double tpf) {
		if(duration != Double.NaN) {
			duration -= tpf;
			if(duration <= 0) {
				PolyEngine.remove(this);
			}
		}
		if(sprite != null) {
			sprite.step(tpf);
		}
	}
}