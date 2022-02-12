package utils;

import base.panes.ImagePane;
import javafx.geometry.Point2D;

public final class Intersections {
	
	private Intersections() {
		
	}
	
	/** Tests if the ideal bounds of the given {@link ImagePane ImagePanes} overlap. */
	public static boolean test(ImagePane ip1, ImagePane ip2) {
		return rect(
				ip1.getIdealX(), ip1.getIdealY(),
				ip1.getIdealRightX(), ip1.getIdealBottomY(),
				ip2.getIdealX(), ip2.getIdealY(),
				ip2.getIdealRightX(), ip2.getIdealBottomY()
		);
	}
	
	public static boolean rect(Point2D topLeft1, Point2D bottomRight1, Point2D topLeft2, Point2D bottomRight2) {
		return rect(topLeft1.getX(), topLeft1.getY(), bottomRight1.getX(), bottomRight1.getX(),
				topLeft2.getX(), topLeft2.getY(), bottomRight2.getX(), bottomRight2.getY());
	}
	
	/** Tests if the two given rectangles overlap. Intersections at the edges only do not count. Assumes that the x-axis
	 * increases right and the y-axis increases down. */
	public static boolean rect(double l1x, double l1y, double r1x, double r1y,
			double l2x, double l2y, double r2x, double r2y) {
		if(l1x == r1x || l1y == r1y || l2x == r2x || l2y == r2y) 
            return false;
		//If one rectangle is on left side of the other 
	    if(l1x >= r2x || l2x >= r1x)
	        return false;
	    //If one rectangle is above the other 
	    if(r1y <= l2y || r2y <= l1y)
	        return false;
	    return true;
	}
	
}
