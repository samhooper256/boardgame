package minigames.hurdles.imagelayer;

import java.util.*;

import base.*;
import base.panes.ImagePane;
import fxutils.Images;
import javafx.scene.input.KeyCode;
import minigames.hurdles.Hurdles;
import minigames.sprites.*;
import players.*;
import utils.Intersections;

public final class Jumper extends ImagePane implements Updatable, AcceptsInput, SpriteAnimated, HitRegioned {

	private static final double ACCEL = 850, JUMP_VEL = -1000; //positive is down.
	
	private static final long DEATH_FLASH_CYCLE = (long) .2e9; //in nanoseconds.
	
	public static final List<Jumper> LIST = Collections.unmodifiableList(Arrays.asList(
			new Jumper(1), new Jumper(2), new Jumper(3), new Jumper(4)));
	
	public static Jumper get(int player) {
		return LIST.get(Player.validate(player) - 1);
	}
	
	private final int number;
	private final SpriteAnimator animator;
	private final HitRegion hitRegion;
	
	private double yvel;
	private long deathSequenceElapsed;
	private boolean onGround;
	private Hurdle lethalHurdle;
	
	private Jumper(int number) {
		super(Images.stillSprite(number));
		this.number = number;
		animator = new SpriteAnimator(this);
		hitRegion = SpriteRegions.forImagePane(this);
		lethalHurdle = null;
	}
	
	public void start() {
		setImage(Images.stillSprite(number));
		onGround = true;
		deathSequenceElapsed = -1;
		setVisible(true);
		setIdealCenterX(Coords.get().xCenter(number));		
		fixToGroundLevel();
		animator.restart();
	}
	
	@Override
	public void update(long diff) {
		double sec = diff / 1e9;
		if(inDeathSequence()) {
			deathSequenceElapsed += diff;
			setIdealX(getIdealX() + Hurdle.VELOCITY * sec);
			if(getIdealRightX() < 0)
				Hurdles.get().kill(this);
			else
				setVisible(deathSequenceElapsed / DEATH_FLASH_CYCLE % 2 != 0);
		}
		else {
			if(onGround()) {
				animator().update(diff);
			}
			else {
				yvel += sec * ACCEL;
				setIdealY(getIdealY() + yvel * sec);
				if(Intersections.test(this, Hurdles.il().ground())) {
					fixToGroundLevel();
					onGround = true;
					yvel = 0;
					notifyLanded();
				}
			}
			for(Hurdle h : Hurdles.il().hurdles()) {
				if(h.intersects(this)) {
					deathSequenceElapsed = 0;
					lethalHurdle = h;
					break;
				}
			}
		}
	}
	
	/** Called when this {@link Jumper} lands. {@link #onGround()} is {@code true} before this method is called. */
	private void notifyLanded() {
		animator().playFromSprite(Images.nextSpriteIndex(animator().airSpriteIndex()));
	}
	
	/** Called when this {@link Jumper} lands. {@link #onGround()} is {@code false} before this method is called. */
	private void notifyJumped() {
		animator().pauseToAir();
	}
	
	@Override
	public void keyPressed(KeyCode kc) {
		if(inDeathSequence())
			return;
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
	
	@Override
	public int number() {
		return number;
	}
	
	public void fixToGroundLevel() {
		setIdealY(Coords.get().groundY() - getIdealHeight());
	}
	
	public boolean onGround() {
		return onGround;
	}
	
	public boolean inDeathSequence() {
		return deathSequenceElapsed >= 0;
	}
	
	/** Returns the {@link Hurdle} that this {@link Jumper} hit or {@code null} if it has not yet hit one. */
	public Hurdle lethalHurdle() {
		return lethalHurdle;
	}

	@Override
	public String toString() {
		return String.format("Jumper[%d]", number());
	}
	
	@Override
	public HitRegion hitRegion() {
		return hitRegion;
	}

	@Override
	public SpriteAnimator animator() {
		return animator;
	}
	
}
