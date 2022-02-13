package utils.rects;

abstract class RectBoundsImpl implements RectBounds {

	static RectBounds fromCorners(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
		return new RectBoundsImpl() {
			
			@Override
			public double x() {
				return topLeftX;
			}
			
			@Override
			public double y() {
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
	
	static RectBounds of(double x, double y, double width, double height) {
		return new RectBoundsImpl() {
			
			@Override
			public double x() {
				return x;
			}
			
			@Override
			public double y() {
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
	
	@Override
	public String toString() {
		return String.format("[x=%.1f, y=%.1f, width=%.1f, height=%.1f]", x(), y(), width(), height());
	}
	
}
