package polyEngine;

import java.awt.Graphics2D;
import java.util.LinkedList;

public abstract class Scene implements Drawable, Stepable {
	
	/**
	 * Called each frame to draw scene-specific elements in addition to Objects placed into the PolyEngine.
	 * @param g
	 */
	public abstract void draw(Graphics2D g);
	
	public abstract LinkedList<Object> getObjectList();
	
	public double getZDepth() {
		return 0.0;
	}
	
	/**
	 * Called when this scene is passed into PolyEngine.loadScene(scene).
	 */
	public abstract void load();
	
	/**
	 * Called each frame to do scene-specific calculations in addition to Objects placed into the PolyEngine.
	 * @param g
	 */
	public abstract void step(double tpf);
	
}