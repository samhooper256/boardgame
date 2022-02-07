package minigames;

import base.*;
import base.panes.*;
import game.*;

public abstract class Minigame extends GamePane implements Updatable {
	
	private final MiniTag tag;
	
	public Minigame(MiniTag tag, ImageLayer imageLayer, FXLayer fxLayer) {
		super(imageLayer, fxLayer);
		this.tag = tag;
		imageLayer.setGamePane(this);
		fxLayer.setGamePane(this);
	}
	
	/** Assumes this {@link Minigame} has already been set as the {@link MainScene#baseContent()}. This method is
	 * called at the "peak" of the {@link BoardFade BoardFadeLayer's} transition (that is, when the player cannot
	 * see the minigame or the board). */
	public abstract void start();
	
	public MiniTag tag() {
		return tag;
	}
	
}
