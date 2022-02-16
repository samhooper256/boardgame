package minigames.archery.imagelayer;

import base.*;
import base.input.*;
import base.panes.*;
import fxutils.Images;
import game.MainScene;
import javafx.geometry.Point2D;
import javafx.scene.input.*;
import minigames.archery.Archery;
import minigames.sprites.*;
import players.PlayerNumbered;

public class Archer extends ImagePane implements Updatable, AcceptsInput, SpriteAnimated, PlayerNumbered {
	
	private static final double SPEED = 250;
	
	private final SpriteAnimator animator;
	private final int number;
	
	private double xvel, yvel;
	private boolean active;
	
	public Archer(int number) {
		super(Images.stillSprite(number));
		this.number = number;
		xvel = yvel = 0;
		active = true;
		animator = new SpriteAnimator(this);
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
			setImage(Images.stillSprite(number));
			return;
		}
		if(xvel == 0 && yvel == 0)
			animator().pauseToStill();
		else
			animator().playAndUpdate(diff);
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
				getIdealY() < ArcheryImageLayer.FENCE_BOTTOM_Y) {
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
	
	@Override
	public SpriteAnimator animator() {
		return animator;
	}

	@Override
	public int number() {
		return number;
	}
	
	
}
