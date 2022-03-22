package minigames.hurdles.imagelayer;

import java.util.*;

import base.Updatable;
import base.panes.ImagePane;
import fxutils.Images;
import utils.Intersections;

public final class Hurdle implements Updatable {

	private static final double SCALE_FACTOR = .5;
	
	public static final double
		HEAD_WIDTH = Images.HURDLE_HEAD.getWidth() * SCALE_FACTOR,
		HEAD_HEIGHT = Images.HURDLE_HEAD.getHeight() * SCALE_FACTOR,
		LEGS_WIDTH = Images.HURDLE_LEGS.getWidth() * SCALE_FACTOR,
		LEGS_HEIGHT = Images.HURDLE_LEGS.getHeight() * SCALE_FACTOR,
		BASE_WIDTH = Images.HURDLE_BASE.getWidth() * SCALE_FACTOR,
		BASE_HEIGHT = Images.HURDLE_BASE.getHeight() * SCALE_FACTOR,
		FULL_WIDTH = BASE_WIDTH,
		VELOCITY = -250;
	
	private static final double BOTTOM_GROUND_OFFSET = 40, LEG_OVEREXTED = 10;
	
	private final ImagePane head, legs, base;
	private final double velocity;
	private final int index;
	
	public Hurdle(int index, double height) {
		this(index, height, 0);
	}
	
	public Hurdle(int index, double height, double x) {
		if(height <= 0)
			throw new IllegalArgumentException(String.format("Height: %f", height));
		this.index = index;
		head = new ImagePane(Images.HURDLE_HEAD, HEAD_WIDTH, HEAD_HEIGHT);
		legs = new ImagePane(Images.HURDLE_LEGS, LEGS_WIDTH, LEGS_HEIGHT);
		base = new ImagePane(Images.HURDLE_BASE, BASE_WIDTH, BASE_HEIGHT);
		base.setIdealBottomY(Coords.get().groundY() + BOTTOM_GROUND_OFFSET);
		head.setIdealY(base.getIdealBottomY() - height);
		legs.setIdealHeight(height - BASE_HEIGHT + LEG_OVEREXTED);
		legs.setIdealY(base.getIdealBottomY() - height);
		setBaseX(x);
		velocity = VELOCITY;
	}
	
	public void setBaseX(double idealX) {
		base().setIdealX(idealX);
		head().setIdealRightX(base().getIdealRightX());
		legs().setIdealRightX(base().getIdealRightX());
	}
	
	/** Equivalent to {@code head().getIdealX()}. */
	public double getBaseX() {
		return base().getIdealX();
	}
	
	/** Equivalent to {@code head().getIdealRightX()}. */
	public double getRightX() {
		return base().getIdealRightX();
	}
	
	public boolean intersects(ImagePane ip) {
		return Intersections.test(ip, head()) || Intersections.test(ip, legs());
	}
	
	@Override
	public void update(long diff) {
		double sec = diff / 1e9;
		double nx = getBaseX() + sec * velocity;
		setBaseX(nx);
	}
	
	public ImagePane head() {
		return head;
	}
	
	public ImagePane legs() {
		return legs;
	}
	
	public ImagePane base() {
		return base;
	}
	
	public int index() {
		return index;
	}
	
	public List<ImagePane> imagePanes() {
		return Arrays.asList(base(), legs(), head());
	}
}
