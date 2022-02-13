package minigames.hurdles.imagelayer;

import java.util.*;

import base.Updatable;
import base.panes.ImagePane;
import fxutils.Images;
import utils.Intersections;

public final class Hurdle implements Updatable {

	public static final double
		WIDTH = Images.HURDLE_HEAD.getWidth(),
		VELOCITY = -100;
	
	private final ImagePane head, legs;
	private final double velocity;
	private final int index;
	
	public Hurdle(int index, double height) {
		this(index, height, 0);
	}
	
	public Hurdle(int index, double height, double x) {
		if(height <= 0)
			throw new IllegalArgumentException(String.format("Height: %f", height));
		this.index = index;
		head = new ImagePane(Images.HURDLE_HEAD);
		legs = new ImagePane(Images.HURDLE_LEGS);
		head.setIdealY(Coords.get().groundY() - height);
		legs.setIdealHeight(height);
		legs.setIdealY(Coords.get().groundY() - height);
		setX(x);
		velocity = VELOCITY;
	}
	
	public void setX(double idealX) {
		head().setIdealX(idealX);
		legs().setIdealX(idealX);
	}
	
	/** Equivalent to {@code head().getIdealX()}. */
	public double getX() {
		return head().getIdealX();
	}
	
	/** Equivalent to {@code head().getIdealRightX()}. */
	public double getRightX() {
		return head().getIdealRightX();
	}
	
	public boolean intersects(ImagePane ip) {
		return Intersections.test(ip, head()) || Intersections.test(ip, legs());
	}
	
	@Override
	public void update(long diff) {
		double sec = diff / 1e9;
		double nx = head().getIdealX() + sec * velocity;
		head().setIdealX(nx);
		legs().setIdealX(nx);
	}
	
	public ImagePane head() {
		return head;
	}
	
	public ImagePane legs() {
		return legs;
	}
	
	public int index() {
		return index;
	}
	
	public List<ImagePane> imagePanes() {
		return Arrays.asList(legs(), head());
	}
}
