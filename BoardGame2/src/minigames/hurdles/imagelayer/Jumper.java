package minigames.hurdles.imagelayer;

import base.*;
import base.panes.ImagePane;
import fxutils.Images;
import javafx.scene.input.KeyCode;
import minigames.hurdles.Hurdles;
import utils.Intersections;

public class Jumper extends ImagePane implements Updatable, AcceptsInput {

	private static final double ACCEL = 850, JUMP_VEL = -1000; //positive is down.
	
	private final int number;
	
	private double yvel;
	private boolean onGround = true;
	
	public Jumper(int number) {
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
		if(kc == KeyCode.SPACE && onGround) {
			yvel = JUMP_VEL;
			onGround = false;
		}
	}
	
	public int number() {
		return number;
	}
	
	public void fixToGroundLevel() {
		setIdealY(Hurdles.il().ground().getIdealY() - getIdealHeight());
	}
	
}
