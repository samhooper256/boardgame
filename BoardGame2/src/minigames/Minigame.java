package minigames;

import base.*;
import game.MainScene;
import javafx.scene.input.*;

public abstract class Minigame extends AbstractScaledPane implements Updatable {
	
	public Minigame() {
		
	}
	
	/** Assumes this {@link Minigame} has already been set as the
	 * {@link MainScene#setRootBase(javafx.scene.layout.Pane) base of the MainScene root}.*/
	public abstract void start();
	
	/** no-op by default. */
	public void keyPressed(KeyCode kc) {
		
	}
	
	/** no-op by default. */
	public void keyReleased(KeyCode kc) {
		
	}
	
	
	
}
