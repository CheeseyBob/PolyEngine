package polyEngine;

import java.awt.Graphics2D;

public interface InterfaceElement {
	
	public abstract void draw(Graphics2D g);
	
	public abstract float getZDepth();
	
}