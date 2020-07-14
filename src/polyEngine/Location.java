package polyEngine;

import java.awt.geom.Point2D;

public interface Location {
	
	public Point2D getLocation();
	
	public double getX();
	
	public double getY();
	
	public void setLocation(double x, double y);
	
}