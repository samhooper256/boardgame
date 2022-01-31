package utils;

import javafx.geometry.Point2D;

import static base.panes.ScaledImageLayer.DEFAULT_WIDTH;
import static base.panes.ScaledImageLayer.DEFAULT_HEIGHT;

public final class Points {

	private Points() {
		
	}
	
	/** Zoomed about the center of the screen.*/
	public static Point2D zoom(Point2D p, double factor) {
		return zoom(p.getX(), p.getY(), factor);
	}
	
	/** Zoomed about the center of the screen.*/
	public static Point2D zoom(double x, double y, double factor) {
		double nx = DEFAULT_WIDTH * .5 + (x - (DEFAULT_WIDTH * .5)) * factor;
		double ny = DEFAULT_HEIGHT * .5 + (y - (DEFAULT_HEIGHT * .5)) * factor;
		return new Point2D(nx, ny);
	}
	
}
