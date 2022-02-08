package minigames.hurdles.imagelayer;

import base.*;
import base.panes.ImagePane;
import fxutils.Images;
import javafx.scene.input.KeyCode;
import minigames.hurdles.Hurdles;
import utils.Intersections;

public class Jumper extends ImagePane implements Updatable, AcceptsInput {

	private static final double ACCEL = 100, JUMP_VEL = -1000; //positive is down.
	
	private final int number;
	
	private double yvel;
	
	public Jumper(int number) {
		super(Images.stillSprite(3));
		this.number = number;
	}
	
	@Override
	public void update(long diff) {
		System.out.printf("[jumper update]%n");
		double sec = diff / 1e9;
		yvel += sec * ACCEL;
		setIdealY(getIdealY() + yvel * sec);
		if(Intersections.test(this, Hurdles.il().ground()))
			yvel = 0;
	}
	
	@Override
	public void keyPressed(KeyCode kc) {
		if(kc == KeyCode.SPACE) {
			System.out.printf("JUMPED%n");
			yvel = JUMP_VEL;
		}
	}
	
	public int number() {
		return number;
	}
	
}
