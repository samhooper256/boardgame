package minigames.archery;

import base.*;
import base.panes.ImagePane;
import fxutils.Images;
import javafx.geometry.Point2D;

public class Arrow extends ImagePane implements Updatable {
	
	private static final double SPEED = 500;
	
	private double xvel, yvel;
	
	public Arrow(Point2D start, Point2D dest) {
		super(Images.ARROW);
		setTrajectory(start, dest);
	}
	
	public Arrow(double x1, double y1, double x2, double y2) {
		super(Images.ARROW);
		setTrajectory(x1, y1, x2, y2);
	}
	
	public Arrow() {
		super(Images.ARROW);
		xvel = 0;
		yvel = 0;
	}
	
	public void setTrajectory(Point2D start, Point2D dest) {
		setTrajectory(start.getX(), start.getY(), dest.getX(), dest.getY());
	}
	
	public void setTrajectory(double x1, double y1, double x2, double y2) {
		double x = x2 - x1, y = y2 - y1;
		double angrad = Math.atan2(y, x);
		xvel = Math.cos(angrad) * SPEED;
		yvel = Math.sin(angrad) * SPEED;
		setRotate(Math.toDegrees(angrad) + 90);
	}

	@Override
	public void update(long diff) {
		double sec = diff / 1e9;
		double newX = getIdealX() + xvel * sec;
		double newY = getIdealY() + yvel * sec;
		setIdealX(newX);
		setIdealY(newY);
		if(isOffscreen()) {
			ArcheryMinigame.sp().trash(this);
			ArcheryMinigame.sp().addEndOfUpdateAction(() -> ArcheryMinigame.get().arrowLeftScreen(this));
		}
	}
	
}
