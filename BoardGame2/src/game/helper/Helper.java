package game.helper;

import java.util.*;

import base.*;
import base.panes.ImagePane;
import fxutils.Images;
import utils.rects.RectBounds;

/** invisible by default. */
public class Helper extends ImagePane implements Updatable {

	/** Vertical movement of the helper (distance from max y position to min y position). */
	private static final double VERTICAL = 20;
	/** The rate at which it moves up and down. */
	private static final double SPEED = 2e-9;
	/** The distance the y pivot of the helper is above an {@link ImagePane} it's pointing to. */
	private static final double ABOVE_DIST = 32;
	
	private long elapsed;
	private double pivotY;
	private ImagePane to;
	private RectBounds toRelevantBounds;
	
	public Helper() {
		super(Images.HELPER);
		setVisible(false);
		elapsed = 0;
		pivotY = 0;
		to = null;
	}

	@Override
	public void update(long diff) {
		elapsed += diff;
		if(to != null) {
			pivotY = to.getIdealY() - ABOVE_DIST;
			if(toRelevantBounds != null) {
				setIdealCenterX(to.getIdealX() + toRelevantBounds.x() + toRelevantBounds.width() * .5);
			}
			else {
				setIdealCenterX(to.getIdealCenterX());
			}
		}
		setIdealCenterY(getY(elapsed));
	}

	private double getY(long nanos) {
		return pivotY + VERTICAL * .5 * (1 + Math.sin(SPEED * nanos));
	}
	
	public void setPivotY(double pivotY) {
		this.pivotY = pivotY;
	}
	
	public double getPivotY() {
		return pivotY;
	}
	
	/** Does <em>not</em> make this {@link Helper} {@link #isVisible() visible}.*/
	public void pointTo(ImagePane ip) {
		to = ip;
		if(to instanceof HitRegioned) {
			RectBounds[] bounds = ((HitRegioned) ip).hitRegion().rectBounds();
			if(bounds.length == 1) {
				toRelevantBounds = bounds[0];
			}
			else {
				Arrays.sort(bounds, Comparator.comparing(RectBounds::y));
				if(bounds[0].y() == bounds[1].y())
					//if there's a tie, just point to the middle as if 'to' were not HitRegioned.
					toRelevantBounds = null;
				else
					toRelevantBounds = bounds[0];
			}
		}
	}
	
	public void detachAndHide() {
		to = null;
		setVisible(false);
	}
	
	public boolean isPointingTo(ImagePane ip) {
		return Objects.equals(to, ip);
	}
	
}
