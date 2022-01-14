package minigames.archery;

import base.*;
import base.input.*;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import utils.Intersections;

public class Archer extends ImagePane implements Updatable {
	
	private static final double SPEED = 250;
	
	private double xvel, yvel;
	private boolean active;
	
	public Archer(Image image) {
		super(image);
		this.xvel = this.yvel = 0;
		this.active = true;
	}
	
	public void keyPressed(KeyCode code) {
		if(code == controls().up())
			yvel -= SPEED;
		else if(code == controls().down())
			yvel += SPEED;
		else if(code == controls().left())
			xvel -= SPEED;
		else if(code == controls().right())
			xvel += SPEED;
	}
	
	public void keyReleased(KeyCode code) {
		if(code == controls().up())
			yvel += SPEED;
		else if(code == controls().down())
			yvel -= SPEED;
		else if(code == controls().left())
			xvel += SPEED;
		else if(code == controls().right())
			xvel -= SPEED;
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
		double sec = diff / 1e9;
		double oldX = getIdealX();
		double newX = oldX + xvel * sec;
		double oldY = getIdealY();
		double newY = oldY + yvel * sec;
		setIdealX(newX);
		setIdealY(newY);
//		Fence fence = ArcheryMinigame.get().fence();
//		if(newY <= fence.getIdealY() + fence.getIdealHeight())
//			setIdealY(oldY);
		if(Intersections.test(this, ArcheryMinigame.get().fence()))
			setIdealY(oldY);
	}
	
	public void mouseClicked(MouseEvent me) {
		if(me.getButton() == MouseButton.PRIMARY) {
			Point2D local = new Point2D(me.getSceneX(), me.getSceneY());
			Point2D archerCenter = getIdealCenter();
			Arrow arrow = new Arrow(archerCenter, ArcheryMinigame.get().localToIdeal(local));
			arrow.setIdealCenter(archerCenter);
			ArcheryMinigame.get().addArrow(arrow);
		}
	}
	
	private DirectionalControls controls() {
		if(GameInput.isSingle())
			return GameInputMode.SINGLE.controls();
		throw new UnsupportedOperationException(String.format("Unsupported input mode: %s", GameInput.mode()));
	}
	
}
