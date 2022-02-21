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
		for(int i = 0, j = 0; i < bounds.length; i++, j += 4) {
			RectBounds b = bounds[i];
			regions[j] = b.x();
			regions[j + 1] = b.y();
			regions[j + 2] = b.rightX();
			regions[j + 3] = b.bottomY();
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
	
	/** Returns an array whose length is {@code (regions().length / 4)}. The {@link RectBounds} in the returned array
	 * are in the same order as in {@link #regions()}. The returned array may be freely modified without affecting this
	 * {@link HitRegion}; a new {@code RectBounds[]} is created and returned every time this method is called. */
	public RectBounds[] rectBounds() {
		RectBounds[] arr = new RectBounds[regions.length / 4];
		for(int i = 0; i < regions.length; i += 4)
			arr[i / 4] = RectBounds.fromCorners(regions[i], regions[i + 1], regions[i + 2], regions[i + 3]);
		return arr;
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
