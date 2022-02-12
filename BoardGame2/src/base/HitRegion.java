package base;

import utils.rects.RectBounds;

public final class HitRegion {

	private final double[] regions;
	
	public HitRegion(double... regions) {
		if(regions.length == 0 || regions.length % 4 != 0)
			throw new IllegalArgumentException(
					String.format("Length of array must be a multiple of 4. Was: %d", regions.length));
		this.regions = regions;
	}
	
	public HitRegion(RectBounds... bounds) {
		regions = new double[bounds.length * 4];
		for(int i = 0; i < bounds.length; i++) {
			RectBounds b = bounds[i];
			regions[i] = b.x();
			regions[i + 1] = b.y();
			regions[i + 2] = b.rightX();
			regions[i + 3] = b.bottomY();
		}
	}
	
	/** Every group of four elements defines the (x, y) of the top-left and (x, y) of the bottom-right corner of one
	 * of the rectangles that make up this {@link HitRegion}. The returned {@code double[]}
	 * <em>must not be modified.</em>*/
	public double[] regions() {
		return regions;
	}
	
}
