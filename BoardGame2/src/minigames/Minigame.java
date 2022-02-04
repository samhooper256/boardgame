package minigames;

import base.*;
import base.panes.*;
import game.*;

public abstract class Minigame extends GamePane implements Updatable {
	
	public Minigame(ImageLayer imageLayer, FXLayer fxLayer) {
		super(imageLayer, fxLayer);
		imageLayer.setGamePane(this);
		fxLayer.setGamePane(this);
	}
	
	/** Assumes this {@link Minigame} has already been set as the {@link MainScene#baseContent()}. This method is
	 * called at the "peak" of the {@link BoardFadeLayer BoardFadeLayer's} transition (that is, when the player cannot
	 * see the minigame or the board). */
	public abstract void start();
	
}
