package minigames.archery.imagelayer;

import base.*;
import base.input.*;
import base.panes.*;
import fxutils.Images;
import game.MainScene;
import javafx.geometry.Point2D;
import javafx.scene.input.*;
import minigames.archery.Archery;
import utils.Intersections;

public class Archer extends ImagePane implements Updatable, AcceptsInput {
	
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
	
	@Override
	public void keyPressed(KeyCode code) {
		updateMovement();
		updateOrientation();
	}
	
	@Override
	public void keyReleased(KeyCode code) {
		updateMovement();
		updateOrientation();
	}
	
	private void updateMovement() {
		boolean up = GameInput.isPressed(GameInput.controls().up()),
				down = GameInput.isPressed(GameInput.controls().down()),
				left = GameInput.isPressed(GameInput.controls().left()),
				right = GameInput.isPressed(GameInput.controls().right());
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
		boolean left = GameInput.isPressed(GameInput.controls().left()),
				right = GameInput.isPressed(GameInput.controls().right());
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
				Intersections.test(this, Archery.il().fence())) {
			setIdealY(oldY);
			yvel = 0;
		}
	}
	
	/** Assumes the given {@link MouseEvent} is a left-click. */
	public void click(MouseEvent me) {
		if(me.getButton() == MouseButton.PRIMARY) {
			Point2D local = new Point2D(me.getSceneX(), me.getSceneY());
			Point2D archerCenter = getIdealCenter();
			Arrow arrow = new Arrow(archerCenter, Archery.il().localToIdeal(local));
			arrow.setIdealCenter(archerCenter);
			Archery.il().addArrow(arrow);
		}
	}
	
	public boolean isMobile() {
		return Archery.get().isMobile(this);
	}
	
}
