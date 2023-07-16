package polyEngine;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import maths.M;

public class Poly2D {
	private final Line2D.Double[] edgeList;
	private final Point2D.Double centre = new Point2D.Double(0, 0);
	private double rMax;

	private static Line2D.Double[] toEdgeList(double[] xList, double[] yList) {
		Line2D.Double[] edgeList = new Line2D.Double[xList.length];
		for(int i = 1; i < xList.length; i ++) {
			edgeList[i - 1] = new Line2D.Double(xList[i - 1], yList[i - 1], xList[i], yList[i]);
		}
		edgeList[xList.length - 1] = new Line2D.Double(xList[xList.length - 1], yList[xList.length - 1], xList[0], yList[0]);
		return edgeList;
	}

	private static Line2D.Double[] toEdgeList(double x1, double y1, double x2, double y2) {
		return new Line2D.Double[] {
				new Line2D.Double(x1, y1, x2, y2)
		};
	}

	public Poly2D(Line2D.Double[] edgeList) {
		this.edgeList = edgeList;
		recalculate();
	}

	public Poly2D(double[] xList, double[] yList) {
		this(toEdgeList(xList, yList));
	}

	public Poly2D(double x1, double y1, double x2, double y2) {
		this(toEdgeList(x1, y1, x2, y2));
	}

	public Poly2D clone() {
		Line2D.Double[] edgeList = new Line2D.Double[this.edgeList.length];
		for(int i = 0; i < edgeList.length; i ++) {
			edgeList[i] = new Line2D.Double(this.edgeList[i].x1, this.edgeList[i].y1, this.edgeList[i].x2, this.edgeList[i].y2);
		}
		return new Poly2D(edgeList);
	}

	public void draw(Graphics2D g) {
		for (Line2D.Double edge : edgeList) {
			g.drawLine((int) edge.x1, (int) edge.y1, (int) edge.x2, (int) edge.y2);
		}
		g.drawOval((int) (centre.x - rMax), (int) (centre.y - rMax), (int) (rMax * 2), (int) (rMax * 2));
	}

	public Point2D getCentre() {
		return new Point2D.Double(centre.x, centre.y);
	}

	/**
	 * Returns the radius for a circle which bounds this polygon.
	 */
	public double getRadius() {
		return rMax;
	}

	public boolean isCollidingWith(Poly2D mesh) {
		if(centre.distanceSq(mesh.centre) < (rMax + mesh.rMax)*(rMax + mesh.rMax)) {
			for(Line2D edge: edgeList) {
				for(Line2D line: mesh.edgeList) {
					//TODO : improve this if it isn't good enough
					if(edge.intersectsLine(line)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void morph(PointMorph... morphs) {
		for(PointMorph morph : morphs)
			morph.apply(this);
		recalculate();
	}

	public void obscureView(double viewX, double viewY, double distance, Graphics2D g) {
		int[] xList = new int[4];
		int[] yList = new int[4];
		for (Line2D.Double edge : edgeList) {
			if (Math.min(edge.getP1().distanceSq(viewX, viewY), edge.getP2().distanceSq(viewX, viewY)) < distance * distance) {
				xList[0] = (int) edge.x1;
				yList[0] = (int) edge.y1;
				xList[1] = (int) edge.x2;
				yList[1] = (int) edge.y2;

				double dx = edge.x2 - viewX;
				double dy = edge.y2 - viewY;
				double d = Math.sqrt(dx * dx + dy * dy);
				dx = distance * dx / d;
				dy = distance * dy / d;
				xList[2] = (int) (edge.x2 + dx);
				yList[2] = (int) (edge.y2 + dy);

				dx = edge.x1 - viewX;
				dy = edge.y1 - viewY;
				d = Math.sqrt(dx * dx + dy * dy);
				dx = distance * dx / d;
				dy = distance * dy / d;
				xList[3] = (int) (edge.x1 + dx);
				yList[3] = (int) (edge.y1 + dy);

				g.fillPolygon(xList, yList, 4);
			}
		}
	}

	private void recalculate() {
		{ // Recalculate centre
			double[] xList = new double[edgeList.length];
			double[] yList = new double[edgeList.length];
			for (int i = 0; i < edgeList.length; i ++) {
				// Edge midpoints, except ...
				xList[i] = edgeList[i].x1 + edgeList[i].x2;
				yList[i] = edgeList[i].y1 + edgeList[i].y2;
			}
			centre.x = M.mean(xList) / 2;
			centre.y = M.mean(yList) / 2;
		}
		{ // Recalculate radius
			double[] distances = new double[edgeList.length + 1];
			distances[0] = centre.distance(edgeList[0].getP1());
			distances[1] = centre.distance(edgeList[0].getP2());
			for(int i = 1; i < edgeList.length; i ++)
				distances[i+1] = centre.distance(edgeList[i].getP2());
			rMax = M.max(distances);
		}
	}

	public void rotate(double angle) {
		for (Line2D.Double edge : edgeList) {
			double x = edge.x1 - centre.x;
			double y = edge.y1 - centre.y;
			double r = Math.sqrt(x * x + y * y);
			double angle1 = Math.atan2(y, x);
			angle1 += angle;
			edge.x1 = (r * Math.cos(angle1)) + centre.x;
			edge.y1 = (r * Math.sin(angle1)) + centre.y;

			x = edge.x2 - centre.x;
			y = edge.y2 - centre.y;

			r = Math.sqrt(x * x + y * y);
			angle1 = Math.atan2(y, x);
			angle1 += angle;
			edge.x2 = (float) (r * Math.cos(angle1)) + centre.x;
			edge.y2 = (float) (r * Math.sin(angle1)) + centre.y;
		}
	}

	public void translate(double dx, double dy) {
		for (Line2D.Double edge : edgeList) {
			edge.x1 += dx;
			edge.y1 += dy;
			edge.x2 += dx;
			edge.y2 += dy;
		}
		centre.x += dx;
		centre.y += dy;
	}

	public static class PointMorph {
		int index;
		double x, y;

		public PointMorph(int index) {
			this.index = index;
		}

		public void setLocation(double x, double y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * Call {@code calculateRadius()} after applying a morph.
		 */
		private void apply(Poly2D poly) {
			if(index != 0) {
				poly.edgeList[index - 1].x2 = x;
				poly.edgeList[index - 1].y2 = y;
			}
			if(index != poly.edgeList.length) {
				poly.edgeList[index].x1 = x;
				poly.edgeList[index].y1 = y;
			}
		}
	}
}