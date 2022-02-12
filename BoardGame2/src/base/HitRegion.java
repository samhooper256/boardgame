package base;

import base.panes.ImagePane;
import utils.rects.RectBounds;

public final class HitRegion {

	private final double[] regions;
	private final ImagePane imagePane;
	
	public HitRegion(ImagePane imagePane, double... regions) {
		if(regions.length == 0 || regions.length % 4 != 0)
			throw new IllegalArgumentException(
					String.format("Length of array must be a multiple of 4. Was: %d", regions.length));
		this.imagePane = imagePane;
		this.regions = regions;
	}
	
	public HitRegion(ImagePane imagePane, RectBounds... bounds) {
		this.imagePane = imagePane;
		regions = new double[bounds.length * 4];
		for(int i = 0; i < bounds.length; i++) {
			RectBounds b = bounds[i];
			regions[i] = b.x();
			regions[i + 1] = b.y();
			regions[i + 2] = b.rightX();
			regions[i + 3] = b.bottomY();
		}
	}
	
	/** Returns the raw {@code double} values used to represent this {@link HitRegion}.
	 * Every group of four elements defines the (x, y) of the top-left and (x, y) of the bottom-right corner of one
	 * of the rectangles that make up this {@link HitRegion}. The given coordinates are in the <strong>local</strong>
	 * and <strong>ideal</strong> coordinate space of the {@link #imagePane()}. For example, the region
	 * specified by the for doubles {@code 0, 0, 10, 10} represents a 10x10 square at the top left of the
	 * {@link ImagePane} content. The returned {@code double[]} <em><strong>must not be modified.</strong></em>*/
	public double[] regions() {
		return regions;
	}
	
	public double xToIdealParentSpace(double x) {
		return x + imagePane().getIdealX();
	}
	
	public double yToIdealParentSpace(double y) {
		return y + imagePane().getIdealY();
	}
	
	public ImagePane imagePane() {
		return imagePane;
	}
}
