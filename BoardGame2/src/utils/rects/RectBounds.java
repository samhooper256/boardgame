package utils.rects;

/** Assumes a coordinate space where x increases to the right and y increases down. */
public interface RectBounds {

	/** Returns a {@link RectBounds} with the given top left and bottom right corners.*/
	static RectBounds fromCorners(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
		return new RectBounds() {
			
			@Override
			public double y() {
				return topLeftX;
			}
			
			@Override
			public double x() {
				return topLeftY;
			}
			
			@Override
			public double rightX() {
				return bottomRightX;
			}
			
			@Override
			public double bottomY() {
				return bottomRightY;
			}
			
		};
	}
	
	/** Returns a {@link RectBounds} with the given top left corner, width, and height. */
	static RectBounds of(double x, double y, double width, double height) {
		return new RectBounds() {
			
			@Override
			public double y() {
				return x;
			}
			
			@Override
			public double x() {
				return y;
			}
			
			@Override
			public double width() {
				return width;
			}
			
			@Override
			public double height() {
				return height;
			}
			
		};
	}
	
	double x();
	
	double y();
	
	default double rightX() {
		return x() + width();
	}
	
	default double bottomY() {
		return y() + height();
	}
	
	default double width() {
		return rightX() - x();
	}
	
	default double height() {
		return bottomY() - y();
	}

}
