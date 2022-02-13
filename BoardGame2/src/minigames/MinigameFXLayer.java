package minigames;

import base.panes.*;

/** <p>{@link #initRewardsDisplay()} must be called after construction.</p> */
public abstract class MinigameFXLayer extends FXLayer {

	public MinigameFXLayer() {
		
	}
	
	final void initRewardsDisplay() {
		getChildren().addAll(gamePane().rewardsDisplay().fxNodes());
	}
	
	@Override
	public Minigame gamePane() {
		return (Minigame) super.gamePane();
	}
	
}
