package utils;

import static game.MainScene.DEFAULT_HEIGHT;
import static game.MainScene.DEFAULT_WIDTH;

import javafx.geometry.Point2D;

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
