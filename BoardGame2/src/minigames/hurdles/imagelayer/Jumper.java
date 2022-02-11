package minigames.hurdles.imagelayer;

import java.util.*;

import base.*;
import base.panes.ImagePane;
import fxutils.Images;
import javafx.scene.input.KeyCode;
import minigames.*;
import minigames.hurdles.Hurdles;
import players.Player;
import utils.Intersections;

public class Jumper extends ImagePane implements Updatable, AcceptsInput, SpriteAnimated {

	private static final double ACCEL = 850, JUMP_VEL = -1000; //positive is down.
	
	public static final List<Jumper> LIST = Collections.unmodifiableList(Arrays.asList(
			new Jumper(1), new Jumper(2), new Jumper(3), new Jumper(4)));
	
	public static Jumper get(int player) {
		return LIST.get(Player.validate(player) - 1);
	}
	
	private final int number;
	private final SpriteAnimator animator;
	
	private double yvel;
	private boolean onGround = true;
	
	private Jumper(int number) {
		super(Images.stillSprite(3));
		this.number = number;
		animator = new SpriteAnimator(this, number);
		setIdealCenterX(Coords.get().xCenter(number));
		fixToGroundLevel();
	}
	
	@Override
	public void update(long diff) {
		if(onGround) {
			animator().update(diff);
		}
		else {
			double sec = diff / 1e9;
			yvel += sec * ACCEL;
			setIdealY(getIdealY() + yvel * sec);
			if(Intersections.test(this, Hurdles.il().ground())) {
				fixToGroundLevel();
				onGround = true;
				yvel = 0;
				notifyLanded();
			}
		}
	}
	
	/** Called when this {@link Jumper} lands. {@link #onGround()} is {@code true} before this method is called. */
	private void notifyLanded() {
		animator().playFromAirSprite();
	}
	
	/** Called when this {@link Jumper} lands. {@link #onGround()} is {@code false} before this method is called. */
	private void notifyJumped() {
		animator().pauseToAir();
	}
	
	@Override
	public void keyPressed(KeyCode kc) {
		if(kc == KeyCode.J)
			tryJump(1);
	}
	
	/** Tries to jump. Not that this {@link Jumper} cannot jump if it is not on the ground. Returns silently if cannot
	 * jump.
	 * @param charge {@code 0.0} to {@code 1.0}.*/
	public void tryJump(double charge) {
		if(!onGround)
			return;
		double p = JumpBar.get(number).optimalCharge();
		double mult = (-Math.abs(charge - p) + p) / p;
		onGround = false;
		yvel = JUMP_VEL * mult;
		notifyJumped();
	}
	
	public int number() {
		return number;
	}
	
	public void fixToGroundLevel() {
		setIdealY(HurdlesImageLayer.GROUND_Y - getIdealHeight());
	}
	
	public boolean onGround() {
		return onGround;
	}

	@Override
	public SpriteAnimator animator() {
		return animator;
	}
	
}
