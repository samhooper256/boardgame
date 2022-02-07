package minigames.archery;

import base.*;
import base.input.*;
import base.panes.*;
import fxutils.Images;
import game.MainScene;
import javafx.geometry.Point2D;
import javafx.scene.input.*;
import utils.Intersections;

public class Archer extends ImagePane implements Updatable {
	
	/** In nanoseconds. */
	private static final long CYCLE_TIME = (long) .5e9;
	private static final double SPEED = 250;
	
	private final int player;
	
	private double xvel, yvel;
	private boolean active;
	private long cycleProgress;
	
	public Archer(int player) {
		super(Images.stillSprite(player));
		this.player = player;
		xvel = yvel = 0;
		active = true;
		cycleProgress = 0;
	}
	
	public void keyPressed(KeyCode code) {
		updateMovement();
		updateOrientation();
	}
	
	public void keyReleased(KeyCode code) {
		updateMovement();
		updateOrientation();
	}
	
	private void updateMovement() {
		boolean up = GameInput.isPressed(controls().up()), down = GameInput.isPressed(controls().down()),
				left = GameInput.isPressed(controls().left()), right = GameInput.isPressed(controls().right());
		if(up == down)
			yvel = 0;
		else if(up)
			yvel = -SPEED;
		else
			yvel = SPEED;
		if(left == right)
			xvel = 0;
		else if(left)
			xvel = -SPEED;
		else
			xvel = SPEED;
	}
	
	private void updateOrientation() {
		boolean left = GameInput.isPressed(controls().left()), right = GameInput.isPressed(controls().right());
		if(left)
			setScaleX(-1);
		else if(right)
			setScaleX(1);
	}

	/** All {@link Archer Archers} are active by default. */
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public void update(long diff) {
		if(!isMobile()) {
			xvel = 0;
			yvel = 0;
			setImage(Images.stillSprite(player));
			return;
		}
		cycleProgress += diff;
		if(cycleProgress >= CYCLE_TIME)
			cycleProgress %= (long) CYCLE_TIME;
		if(xvel == 0 && yvel == 0)
			setImage(Images.stillSprite(player));
		else
			setImage(Images.sprite(player, (int) (cycleProgress / (CYCLE_TIME / 4))));
		double sec = diff / 1e9;
		double oldX = getIdealX();
		double newX = oldX + xvel * sec;
		double oldY = getIdealY();
		double newY = oldY + yvel * sec;
		if(newX < 0 || newX + getIdealWidth() > MainScene.DEFAULT_WIDTH)
			xvel = 0;
		else
			setIdealX(newX);
		setIdealY(newY);
		if(newY > MainScene.DEFAULT_HEIGHT - getIdealHeight() ||
				Intersections.test(this, Archery.sp().fence())) {
			setIdealY(oldY);
			yvel = 0;
		}
	}
	
	/** Assumes the given {@link MouseEvent} is a left-click. */
	public void click(MouseEvent me) {
		if(me.getButton() == MouseButton.PRIMARY) {
			Point2D local = new Point2D(me.getSceneX(), me.getSceneY());
			Point2D archerCenter = getIdealCenter();
			Arrow arrow = new Arrow(archerCenter, Archery.sp().localToIdeal(local));
			arrow.setIdealCenter(archerCenter);
			Archery.sp().addArrow(arrow);
		}
	}
	
	private DirectionalControls controls() {
		return GameInput.controls();
	}
	
	public boolean isMobile() {
		return Archery.get().isMobile(this);
	}
	
}
