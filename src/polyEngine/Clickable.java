package polyEngine;

import java.awt.geom.Point2D;

public interface Clickable {
	
	public abstract boolean containsPoint(Point2D point);
}