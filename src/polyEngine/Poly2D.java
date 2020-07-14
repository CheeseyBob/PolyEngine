package polyEngine;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import maths.M;

public class Poly2D {
	private Line2D.Double[] edgeList;
	private Point2D.Double centre = new Point2D.Double(0, 0);
	private double rMax;
	
	private static Line2D.Double[] toEdgeList(double[] xList, double[] yList){
		Line2D.Double[] edgeList = new Line2D.Double[xList.length];
		for(int i = 1; i < xList.length; i ++){
			edgeList[i - 1] = new Line2D.Double(xList[i - 1], yList[i - 1], xList[i], yList[i]);
		}
		edgeList[xList.length - 1] = new Line2D.Double(xList[xList.length - 1], yList[xList.length - 1], xList[0], yList[0]);
		return edgeList;
	}
	
	public Poly2D(Line2D.Double[] edgeList, double xCentre, double yCentre){
		centre.x = xCentre;
		centre.y = yCentre;
		this.edgeList = new Line2D.Double[edgeList.length];
		for(int i = 0; i < edgeList.length; i ++){
			this.edgeList[i] = edgeList[i];

			double dx1 = edgeList[i].x1 - xCentre;
			double dy1 = edgeList[i].y1 - yCentre;
			double dx2 = edgeList[i].x1 - xCentre;
			double dy2 = edgeList[i].y1 - yCentre;
			rMax = Math.max(rMax, Math.max(Math.sqrt(dx1*dx1 + dy1*dy1), Math.sqrt(dx2*dx2 + dy2*dy2)));
		}
	}
	
	public Poly2D(double[] xList, double[] yList){
		this(xList, yList, M.mean(xList), M.mean(yList));
	}
	
	public Poly2D(double[] xList, double[] yList, double xCentre, double yCentre){
		this(toEdgeList(xList, yList), xCentre, yCentre);
	}
	
	public Poly2D clone(){
		Line2D.Double[] edgeList = new Line2D.Double[this.edgeList.length];
		for(int i = 0; i < edgeList.length; i ++){
			edgeList[i] = new Line2D.Double(this.edgeList[i].x1, this.edgeList[i].y1, this.edgeList[i].x2, this.edgeList[i].y2);
		}
		return new Poly2D(edgeList, centre.x, centre.y);
	}
	
	public void draw(Graphics2D g){
		for(int i = 0; i < edgeList.length; i ++){
			g.drawLine((int)edgeList[i].x1, (int)edgeList[i].y1, (int)edgeList[i].x2, (int)edgeList[i].y2);
		}
	}
	
	public Point2D getCentre(){
		return new Point2D.Double(centre.x, centre.y);
	}
	
	/**
	 * Returns the radius for a circle which bounds this polygon. 
	 * @return
	 */
	public double getRadius(){
		return rMax;
	}
	
	public boolean isCollidingWith(Poly2D mesh){
		if(centre.distanceSq(mesh.centre) < (rMax + mesh.rMax)*(rMax + mesh.rMax)){
			for(Line2D edge: edgeList){
				for(Line2D line: mesh.edgeList){
					//TODO : improve this if it isn't good enough
					if(edge.intersectsLine(line)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void obscureView(double viewX, double viewY, double distance, Graphics2D g){
		int[] xList = new int[4];
		int[] yList = new int[4];
		for(int i = 0; i < edgeList.length; i ++){
			if(Math.min(edgeList[i].getP1().distanceSq(viewX, viewY), edgeList[i].getP2().distanceSq(viewX, viewY)) < distance*distance){
				xList[0] = (int)edgeList[i].x1;
				yList[0] = (int)edgeList[i].y1;
				xList[1] = (int)edgeList[i].x2;
				yList[1] = (int)edgeList[i].y2;
				
				double dx = edgeList[i].x2 - viewX;
				double dy = edgeList[i].y2 - viewY;
				double d = Math.sqrt(dx*dx + dy*dy);
				dx = distance*dx/d;
				dy = distance*dy/d;
				xList[2] = (int)(edgeList[i].x2 + dx);
				yList[2] = (int)(edgeList[i].y2 + dy);
				
				dx = edgeList[i].x1 - viewX;
				dy = edgeList[i].y1 - viewY;
				d = Math.sqrt(dx*dx + dy*dy);
				dx = distance*dx/d;
				dy = distance*dy/d;
				xList[3] = (int)(edgeList[i].x1 + dx);
				yList[3] = (int)(edgeList[i].y1 + dy);
				
				g.fillPolygon(xList, yList, 4);
			}
		}
	}
	
	public void translate(double dx, double dy){
		for(int i = 0; i < edgeList.length; i ++){
			edgeList[i].x1 += dx;
			edgeList[i].y1 += dy;
			edgeList[i].x2 += dx;
			edgeList[i].y2 += dy;
		}
		centre.x += dx;
		centre.y += dy;
	}
	
	public void rotate(double angle){
		for(int i = 0; i < edgeList.length; i ++){
			double x = edgeList[i].x1 - centre.x;
			double y = edgeList[i].y1 - centre.y;
			double r = Math.sqrt(x*x + y*y);
			double angle1 = Math.atan2(y, x);
			angle1 += angle;
			edgeList[i].x1 = (r*Math.cos(angle1)) + centre.x;
			edgeList[i].y1 = (r*Math.sin(angle1)) + centre.y;
			
			x = edgeList[i].x2 - centre.x;
			y = edgeList[i].y2 - centre.y;
			
			r = Math.sqrt(x*x + y*y);
			angle1 = Math.atan2(y, x);
			angle1 += angle;
			edgeList[i].x2 = (float)(r*Math.cos(angle1)) + centre.x;
			edgeList[i].y2 = (float)(r*Math.sin(angle1)) + centre.y;
		}
	}
	
	public void setCentre(Point2D p){
		centre.setLocation(p);
	}
}