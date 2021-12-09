package minigames;

import base.*;

public abstract class Minigame extends AbstractScaledPane {
	
	public Minigame() {
		
	}
	
	/** Assumes this {@link Minigame} has already been set as the
	 * {@link MainScene#setRootBase(javafx.scene.layout.Pane) base of the MainScene root}.*/
	public abstract void start();
	
}
