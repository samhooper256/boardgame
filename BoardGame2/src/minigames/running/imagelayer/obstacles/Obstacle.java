package minigames.running.imagelayer.obstacles;

import base.Updatable;
import base.panes.ImagePane;
import javafx.scene.image.Image;
import minigames.running.Running;
import minigames.running.imagelayer.Alignable;
import players.PlayerNumbered;

public abstract class Obstacle extends ImagePane implements Updatable, PlayerNumbered, Alignable {

	private final int number, index;
	
	protected Obstacle(Image image, int number, int index) {
		super(image);
		this.number = number;
		this.index = index;
	}

	@Override
	public void update(long diff) {
		double sec = diff / 1e9;
		setIdealX(getIdealX() + sec * Running.get().velocity());
	}
	
	@Override
	public int number() {
		return number;
	}
	
	public int index() {
		return index;
	}
	
}
