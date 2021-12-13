package minigames;

import base.*;
import game.MainScene;
import javafx.scene.input.KeyEvent;

public abstract class Minigame extends AbstractScaledPane {
	
	public Minigame() {
		
	}
	
	/** Assumes this {@link Minigame} has already been set as the
	 * {@link MainScene#setRootBase(javafx.scene.layout.Pane) base of the MainScene root}.*/
	public abstract void start();
	
	/** no-op by default. */
	public void keyPressed(KeyEvent ke) {
		
	}
	
}
