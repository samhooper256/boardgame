package game.playerselect;

import base.Updatable;
import base.panes.*;
import fxutils.Backgrounds;
import javafx.scene.paint.Color;

public class PlayerSelect extends GamePane implements Updatable {

	private static final PlayerSelect INSTANCE = new PlayerSelect();
	
	public static PlayerSelect get() {
		return INSTANCE;
	}
	
	public PlayerSelect() {
		super(new PlayerSelectImageLayer(), new PlayerSelectFXLayer());
		setBackground(Backgrounds.of(Color.BURLYWOOD));
	}
	
	public void notifyAnimationToPlayerSelectStarted() {
		imageLayer().notifyAnimationToPlayerSelectStarted();
	}
	
	@Override
	public void update(long diff) {
		imageLayer().update(diff);
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
