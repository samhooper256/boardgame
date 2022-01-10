package minigames.archery;

import base.*;
import fxutils.Images;
import javafx.geometry.Point2D;

public class Target extends ImagePane implements Updatable {

	private static final double TOLERANCE = 5;
	
	private final TargetPath path;
	
	private double xvel, yvel;
	/** If {@code -1}, this {@link Target} is in motion. */
	private double sinceLastStop;
	private int pathIndex;
	
	public Target(TargetPath path) {
		super(Images.TARGET);
		this.path = path;
		this.pathIndex = 1;
		this.sinceLastStop = -1;
		setIdealCenter(path.point(0));
		directVelocityTowards(path.point(1));
	}

	@Override
	public void update(long diff) {
		double sec = diff / 1e9;
		if(sinceLastStop < 0) {
			setIdealX(getIdealX() + xvel * sec);
			setIdealY(getIdealY() + yvel * sec);
			Point2D dest = path.point(pathIndex);
			if(Math.abs(getIdealX() - dest.getX()) < TOLERANCE && Math.abs(getIdealY() - dest.getY()) < TOLERANCE) {
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
			sinceLastStop = -1;
		}
		else {
			sinceLastStop += sec;
		}
	}

	public void directVelocityTowards(Point2D dest) {
		double x = dest.getX() - getIdealX(), y = dest.getY() - getIdealY();
		double angrad = Math.atan2(y, x);
		xvel = Math.cos(angrad) * path.speed();
		yvel = Math.sin(angrad) * path.speed();
	}
	
}
