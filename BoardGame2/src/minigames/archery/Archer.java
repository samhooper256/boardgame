package minigames.archery;

import base.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Archer extends ImagePane implements Updatable {
	
	private static final double SPEED = 250;
	
	private final ArcheryControls controls;
	
	private double xvel, yvel;
	
	public Archer(Image image, ArcheryControls controls) {
		super(image);
		this.controls = controls;
		this.xvel = this.yvel = 0;
	}
	
	public void keyPressed(KeyCode code) {
		if(code == controls.up())
			yvel -= SPEED;
		else if(code == controls.down())
			yvel += SPEED;
		else if(code == controls.left())
			xvel -= SPEED;
		else if(code == controls.right())
			xvel += SPEED;
	}
	
	public void keyReleased(KeyCode code) {
		if(code == controls.up())
			yvel += SPEED;
		else if(code == controls.down())
			yvel -= SPEED;
		else if(code == controls.left())
			xvel += SPEED;
		else if(code == controls.right())
			xvel -= SPEED;
	}

	@Override
	public void update(long diff) {
		double sec = diff / 1e9;
		setIdealX(getIdealX() + xvel * sec);
		setIdealY(getIdealY() + yvel * sec);
	}
	
}
