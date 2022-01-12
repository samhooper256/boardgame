package minigames.archery;

import base.*;
import fxutils.Images;
import javafx.geometry.Point2D;

public class Target extends ImagePane implements Updatable {

	private static final double TOLERANCE = 5;
	
	private final TargetPath path;
	private final Runnable hitAction;
	
	private double xvel, yvel;
	/** If {@code -1}, this {@link Target} is in motion. */
	private double sinceLastStop;
	private int pathIndex;
	private boolean leftOfTarget, aboveTarget;
	
	public Target(TargetPath path, Runnable hitAction) {
		super(Images.TARGET);
		this.path = path;
		this.hitAction = hitAction;
		this.pathIndex = 1;
		this.sinceLastStop = -1;
		leftOfTarget = aboveTarget = false;
		setIdealCenter(path.point(0));
		directVelocityTowards(path.point(1));
		updateQuadrants(path.point(1));
	}

	@Override
	public void update(long diff) {
		double sec = diff / 1e9;
		if(sinceLastStop < 0) {
			setIdealX(getIdealX() + xvel * sec);
			setIdealY(getIdealY() + yvel * sec);
			Point2D dest = path.point(pathIndex);
			if(withinTolerance(dest) || passedTarget(dest)) {
				if(pathIndex == path.stops() - 1)
					pathIndex = 0;
				else
					pathIndex++;
				sinceLastStop = 0;
			}
		}
		else if(sinceLastStop >= path.delay().toSeconds()) {
			Point2D dest = path.point(pathIndex);
			directVelocityTowards(dest);
			updateQuadrants(dest);
			sinceLastStop = -1;
		}
		else {
			sinceLastStop += sec;
		}
	}

	public void updateQuadrants(Point2D dest) {
		leftOfTarget = dest.getX() > getIdealX();
		aboveTarget = dest.getY() > getIdealY();
	}

	public boolean withinTolerance(Point2D dest) {
		return Math.abs(getIdealX() - dest.getX()) < TOLERANCE && Math.abs(getIdealY() - dest.getY()) < TOLERANCE;
	}
	
	public boolean passedTarget(Point2D dest) {
		boolean nowLeft = dest.getX() > getIdealX();
		boolean nowAbove = dest.getY() > getIdealY();
		return nowLeft != leftOfTarget || nowAbove != aboveTarget;
	}
	
	private void runHitAction() {
		if(hitAction != null)
			hitAction.run();
	}
	
	
	public void directVelocityTowards(Point2D dest) {
		double x = dest.getX() - getIdealX(), y = dest.getY() - getIdealY();
		double angrad = Math.atan2(y, x);
		xvel = Math.cos(angrad) * path.speed();
		yvel = Math.sin(angrad) * path.speed();
	}
	
}
