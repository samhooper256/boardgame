package game.mainmenu;

import base.panes.*;
import fxutils.Backgrounds;
import javafx.scene.paint.Color;

public class MainMenu extends GamePane {

	private static final MainMenu INSTANCE = new MainMenu();
	
	public static MainMenu get() {
		return INSTANCE;
	}
	
	public MainMenu() {
		super(new MainMenuImageLayer(), new MainMenuFXLayer());
		setBackground(Backgrounds.of(Color.BURLYWOOD));
	}
	
	@Override
	public MainMenuImageLayer imageLayer() {
		return (MainMenuImageLayer) super.imageLayer();
	}
	
	@Override
	public MainMenuFXLayer fxLayer() {
		return (MainMenuFXLayer) super.fxLayer();
	}
	
}
