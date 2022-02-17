package minigames.running.imagelayer;

import java.util.*;

import base.*;
import base.input.GameInput;
import base.panes.ImagePane;
import fxutils.Images;
import javafx.scene.input.*;
import minigames.sprites.*;
import players.Player;

public class Runner extends ImagePane implements Updatable, AcceptsInput, SpriteAnimated, Alignable {

	public static final List<Runner> LIST =
			Collections.unmodifiableList(Arrays.asList(new Runner(1), new Runner(2), new Runner(3), new Runner(4)));
	
	private static final double
		X = 192,
		MAX_JUMP_CHARGE_TIME = .1e9, // 1/10 of a second
		MAX_JUMP_VELOCITY = -300, //positive is down.
		ACCEL = 750; //due to gravity.

	public static Runner get(int number) {
		return LIST.get(Player.validate(number) - 1);
	}
	
	private final int number;
	private final SpriteAnimator animator;
	
	/** {@code -1} if jump key not pressed. */
	private long jumpChargeElapsed;
	private double yvel;
	private boolean onGround;
	
	private Runner(int number) {
		super(Images.stillSprite(number));
		this.number = number;
		this.animator = new SpriteAnimator(this);
		jumpChargeElapsed = -1;
		yvel = 0;
		onGround = true;
		setIdealX(X);
	}
	
	@Override
	public void update(long diff) {
		if(onGround()) {
			if(jumpChargeElapsed >= 0)
				jumpChargeElapsed += diff;
			if(jumpChargeElapsed >= MAX_JUMP_CHARGE_TIME)
				jump();
			else
				animator().update(diff);
		}
		else {
			double sec = diff / 1e9, newY = getIdealY() + sec * yvel;
			if(newY + getHeight() >= ground().getIdealY()) {
				fixToGroundLevel();
				onGround = true;
				yvel = 0;
			}
			else {
				setIdealY(newY);
				yvel += sec * ACCEL;
			}
		}
	}
	
	@Override
	public void keyPressed(KeyCode kc) {
		if(kc != GameInput.controls().single(number()) || !onGround())
			return;
		jumpChargeElapsed = 0;
	}

	@Override
	public void keyReleased(KeyCode kc) {
		if(kc != GameInput.controls().single(number()) || !onGround())
			return;
		if(jumpChargeElapsed >= 0)
			jump();
	}

	/** Assumes {@code (jumpChargeElapsed >= 0 && onGround())}. */
	private void jump() {
		yvel = MAX_JUMP_VELOCITY * jumpChargeElapsed / MAX_JUMP_CHARGE_TIME;
		onGround = false;
		jumpChargeElapsed = -1;
	}

	public Ground ground() {
		return Ground.get(number());
	}
	
	public boolean onGround() {
		return onGround;
	}
	
	@Override
	public void alignFor(int playerCount) {
		if(number() > playerCount)
			throw new IllegalArgumentException(String.format("number() > playerCount (%d > %d)", number(), playerCount));
		alignForTrusted(playerCount);
	}
	
	private void alignForTrusted(int playerCount) {
		Coords c = Coords.p(playerCount);
		setIdealBottomY(c.playerBottomY(number));
	}

	private void fixToGroundLevel() {
		setIdealBottomY(ground().getIdealY());
	}
	
	@Override
	public int number() {
		return number;
	}
	
	@Override
	public SpriteAnimator animator() {
		return animator;
	}
}
