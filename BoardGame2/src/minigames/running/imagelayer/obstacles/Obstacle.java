package minigames.running.imagelayer.obstacles;

import base.Updatable;
import base.panes.ImagePane;
import javafx.scene.image.Image;
import minigames.running.Running;
import minigames.running.imagelayer.Alignable;
import players.PlayerNumbered;

public abstract class Obstacle extends ImagePane implements Updatable, PlayerNumbered, Alignable {

	private final int number;
	private double velocity;
	
	protected Obstacle(Image image, int number) {
		super(image);
		this.number = number;
		setVelocity(Running.STARTING_GAME_VELOCITY);
	}

	@Override
	public void update(long diff) {
		double sec = diff / 1e9;
		setIdealX(getIdealX() + sec * velocity());
	}
	
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	
	public double velocity() {
		return velocity;
	}
	
	@Override
	public int number() {
		return number;
	}
	
}
