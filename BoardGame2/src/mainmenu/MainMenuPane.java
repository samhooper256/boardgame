package mainmenu;

import base.panes.*;

public class MainMenuPane extends GamePane {

	private static final MainMenuPane INSTANCE = new MainMenuPane();
	
	public static MainMenuPane get() {
		return INSTANCE;
	}
	
	public MainMenuPane() {
		super(new MainMenuScaledPane(), new MainMenuFXLayer());
		imageLayer().setGamePane(this);
	}
	
	@Override
	public MainMenuScaledPane imageLayer() {
		return (MainMenuScaledPane) super.imageLayer();
	}
	
	@Override
	public MainMenuFXLayer fxLayer() {
		return (MainMenuFXLayer) super.fxLayer();
	}
	
}
