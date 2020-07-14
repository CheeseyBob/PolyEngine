package polyEngine;


public class Meshes {
	private static final double halfRoot2 = Math.sqrt(2)/2;
	
	public static Poly2D box(double x1, double y1, double x2, double y2){
		double[] xList = {x1, x1, x2, x2};
		double[] yList = {y1, y2, y2, y1};
		return new Poly2D(xList, yList);
	}
	
	public static Poly2D line(double x1, double y1, double x2, double y2){
		double[] xList = {x1, x2};
		double[] yList = {y1, y2};
		return new Poly2D(xList, yList);
	}
	
	public static Poly2D octagon(double r){
		r = r/2;
		return new Poly2D(new double[]{-r, -r*halfRoot2, 0, r*halfRoot2, r, r*halfRoot2, 0, -r*halfRoot2}, new double[]{0, -r*halfRoot2, -r, -r*halfRoot2, 0, r*halfRoot2, r, r*halfRoot2});
	}
	
	public static Poly2D octoid(double w, double h){
		double wDiag = w*halfRoot2;
		double hDiag = h*halfRoot2;
		double[] xList = {-w, -wDiag, 0, wDiag, w, wDiag, 0, -wDiag};
		double[] yList = {0, -hDiag, -h, -hDiag, 0, hDiag, h, hDiag};
		return new Poly2D(xList, yList);
	}
	
	public static Poly2D square(double w){
		w = w/2;
		return new Poly2D(new double[]{-w, w, w, -w}, new double[]{-w, -w, w, w});
	}
}