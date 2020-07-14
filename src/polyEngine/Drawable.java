package polyEngine;

import java.awt.Graphics2D;

public interface Drawable {
	
	public abstract void draw(Graphics2D g);
	
	public abstract double getZDepth();
	
}