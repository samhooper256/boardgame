package minigames;

import base.*;
import base.panes.*;
import game.MainScene;

public abstract class Minigame extends GamePane implements Updatable {
	
	public Minigame(ScaledPane imageLayer, FXLayer fxLayer) {
		super(imageLayer, fxLayer);
		imageLayer.setGamePane(this);
		fxLayer.setGamePane(this);
	}
	
	/** Assumes this {@link Minigame} has already been set as the
	 * {@link MainScene#setRootBase(javafx.scene.layout.Pane) base of the MainScene root}.*/
	public abstract void start();
	
}
