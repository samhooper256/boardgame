package minigames.hurdles.imagelayer;

import java.util.*;

import base.*;
import base.input.GameInput;
import base.panes.ImagePane;
import fxutils.Images;
import javafx.scene.input.KeyCode;
import minigames.hurdles.Hurdles;
import players.Player;
import utils.Intersections;

public class Jumper extends ImagePane implements Updatable, AcceptsInput {

	private static final double ACCEL = 850, JUMP_VEL = -1000; //positive is down.
	
	public static final List<Jumper> LIST = Collections.unmodifiableList(Arrays.asList(
			new Jumper(1), new Jumper(2), new Jumper(3), new Jumper(4)));
	
	public static Jumper get(int player) {
		return LIST.get(Player.validate(player) - 1);
	}
	
	private final int number;
	
	private double yvel;
	private boolean onGround = true;
	
	private Jumper(int number) {
		super(Images.stillSprite(3));
		this.number = number;
	}
	
	@Override
	public void update(long diff) {
		if(!onGround) {
			double sec = diff / 1e9;
			yvel += sec * ACCEL;
			setIdealY(getIdealY() + yvel * sec);
			if(Intersections.test(this, Hurdles.il().ground())) {
				fixToGroundLevel();
				onGround = true;
				yvel = 0;
			}
		}
	}
	
	@Override
	public void keyPressed(KeyCode kc) {
		if(kc == GameInput.controls().single(number) && onGround) {
//			yvel = JUMP_VEL;
//			onGround = false;
			JumpBar.get(number).startCharging();
		}
	}
	
	public int number() {
		return number;
	}
	
	public void fixToGroundLevel() {
		setIdealY(Hurdles.il().ground().getIdealY() - getIdealHeight());
	}
	
}
