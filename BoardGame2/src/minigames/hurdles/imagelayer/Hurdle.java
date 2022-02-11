package minigames.hurdles.imagelayer;

import java.util.*;

import base.Updatable;
import base.panes.ImagePane;
import fxutils.Images;

public final class Hurdle implements Updatable {

	public static final double
		WIDTH = Images.HURDLE_HEAD.getWidth(),
		MAX_HEIGHT = 540,
		DEFAULT_VELOCITY = -100;
	
	private final ImagePane head, legs;
	private final double velocity;
	
	public Hurdle(double height) {
		if(height <= 0 || height > MAX_HEIGHT)
			throw new IllegalArgumentException(String.format("Height: %f", height));
		head = new ImagePane(Images.HURDLE_HEAD);
		legs = new ImagePane(Images.HURDLE_LEGS);
		head.setIdealY(Coords.get().groundY() - height);
		legs.setIdealHeight(height);
		legs.setIdealY(Coords.get().groundY() - height);
		velocity = DEFAULT_VELOCITY;
	}
	
	public ImagePane head() {
		return head;
	}
	
	public ImagePane legs() {
		return legs;
	}
	
	public List<ImagePane> imagePanes() {
		return Arrays.asList(legs(), head());
	}
	
	public void setX(double idealX) {
		head().setIdealX(idealX);
		legs().setIdealX(idealX);
	}
	
	@Override
	public void update(long diff) {
		double sec = diff / 1e9;
		double nx = head().getIdealX() + sec * velocity;
		head().setIdealX(nx);
		legs().setIdealX(nx);
	}
	
}
