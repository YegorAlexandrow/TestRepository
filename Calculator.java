package com.iceberg.scara;

public class Calculator {
	private static double Y = 250;
	public static Point AXIS1 = new Point(50, Y), AXIS2 = new Point(50+110, Y);
	public static double ARM1 = 125, ARM2 = 160, PEN = 16, PEN_ANGLE = 180;
	public static double RAD = 57.2958; 
	
	public static double PEN_ANGLE2 = 0, hypot2 = 0;
	
	static {
		hypot2 = Math.sqrt(ARM2*ARM2 + PEN*PEN - 2*ARM2*PEN*Math.cos(PEN_ANGLE/RAD));
		PEN_ANGLE2 = acos(ARM2, PEN, hypot2);
	}
	
	public static Point calc(Point p) {
		return calc(p.getX(), p.getY());
	}
	
	private static double acos(double a, double b, double c) {
		return Math.acos((b*b + c*c - a*a)/(2*b*c))*RAD;
	}
	
	public static Point calc(double x, double y) {
		
		double alpha  = Math.atan2(Math.abs(AXIS1.getY()-y), x-AXIS1.getX()) * RAD;
		double hypot1 = Math.hypot(x-AXIS1.getX(), AXIS1.getY()-y);
		double beta	  = acos(hypot2, hypot1, ARM1);
		double angle1 = alpha + beta;
		double gamma  = acos(hypot1, ARM1, hypot2) - acos(PEN, hypot2, ARM2) - (180-angle1); 
		Point pt1 = new Point(AXIS1.getX() + Math.cos(angle1/RAD)*ARM1, AXIS1.getY() + Math.sin(angle1/RAD)*ARM1);
		Point pen_pt  = new Point(pt1.getX()+Math.cos(gamma/RAD)*ARM2, pt1.getY()+Math.sin(gamma/RAD)*ARM2);
		double hypot3 = Math.hypot(pen_pt.getX()-AXIS2.getX(), pen_pt.getY()-AXIS2.getY());
		double angle2 = 180 - acos(ARM2, hypot3, ARM1) - Math.asin((pen_pt.getY()-AXIS2.getY())/hypot3)*RAD;

		System.out.println(angle1 + " " + angle2);
		return new Point(angle1, angle2);
	}
	
	private Calculator() {
		
	}
	
	public static Point[] countDots(double x1, double y1, double x2, double y2, double step) {
		Point[] pts = new Point[(int) (Math.hypot(Math.abs(x1-x2), Math.abs(y1-y2))/step)+1];
		
		for(int i=0; i<pts.length-1; i++) {
			pts[i] = new Point(x1 + (x2-x1)*i/pts.length, y1 + (y2-y1)*i/pts.length);
			//System.out.println(pts[i]);
		}
		
		pts[pts.length-1] = new Point(x2, y2);
		//System.out.println(pts[pts.length-1]);
		
		return pts;
	}
	/*
	public static void main(String[] args) {
		Point[] pts = Calculator.countDots(100, 0, 0, 50, 0.5);
		for(int i=0; i<pts.length;++i) {
			Calculator.calc(pts[i]);
		}

		long time = System.currentTimeMillis();
		try {
			HpglReader.read("D:\\putin.png", null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(System.currentTimeMillis() - time);
	}
	*/
}
