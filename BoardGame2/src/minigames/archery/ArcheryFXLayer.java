package minigames.archery;

import base.panes.*;

public class ArcheryFXLayer extends FXLayer {
	
	public ArcheryFXLayer() {
		super();
	}

	@Override
	public ArcheryMinigame gamePane() {
		return (ArcheryMinigame) super.gamePane();
	}
	
}
