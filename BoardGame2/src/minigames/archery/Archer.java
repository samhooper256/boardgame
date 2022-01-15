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
		updateMovement();
	}
	
	public void keyReleased(KeyCode code) {
		updateMovement();
	}
	
	private void updateMovement() {
		boolean up = GameInput.isPressed(controls().up()), down = GameInput.isPressed(controls().down()),
				left = GameInput.isPressed(controls().left()), right = GameInput.isPressed(controls().right());
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
			return;
		}
		double sec = diff / 1e9;
		double oldX = getIdealX();
		double newX = oldX + xvel * sec;
		double oldY = getIdealY();
		double newY = oldY + yvel * sec;
		if(newX < 0 || newX + getIdealWidth() > ScaledPane.DEFAULT_WIDTH)
			xvel = 0;
		else
			setIdealX(newX);
		setIdealY(newY);
		if(newY > ScaledPane.DEFAULT_HEIGHT - getIdealWidth() ||
				Intersections.test(this, ArcheryMinigame.sp().fence())) {
			setIdealY(oldY);
			yvel = 0;
		}
	}
	
	public void mouseClicked(MouseEvent me) {
		if(me.getButton() == MouseButton.PRIMARY) {
			Point2D local = new Point2D(me.getSceneX(), me.getSceneY());
			Point2D archerCenter = getIdealCenter();
			Arrow arrow = new Arrow(archerCenter, ArcheryMinigame.sp().localToIdeal(local));
			arrow.setIdealCenter(archerCenter);
			ArcheryMinigame.sp().addArrow(arrow);
		}
	}
	
	private DirectionalControls controls() {
		if(GameInput.isSingle())
			return GameInputMode.SINGLE.controls();
		throw new UnsupportedOperationException(String.format("Unsupported input mode: %s", GameInput.mode()));
	}
	
	public boolean isMobile() {
		return ArcheryMinigame.get().isMobile(this);
	}
	
}
