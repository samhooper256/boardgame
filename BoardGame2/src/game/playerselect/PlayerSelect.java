package game.playerselect;

import base.panes.*;

public class PlayerSelect extends GamePane {

	public PlayerSelect() {
		super(new PlayerSelectImageLayer(), new PlayerSelectFXLayer());
	}
	
	@Override
	public PlayerSelectImageLayer imageLayer() {
		return (PlayerSelectImageLayer) super.imageLayer();
	}
	
	@Override
	public PlayerSelectFXLayer fxLayer() {
		return (PlayerSelectFXLayer) super.fxLayer();
	}
	
}
