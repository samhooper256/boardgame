package mainmenu;

import base.*;
import base.panes.GamePane;

public class MainMenuPane extends GamePane {

	private static final MainMenuPane INSTANCE = new MainMenuPane();
	
	public static MainMenuPane get() {
		return INSTANCE;
	}
	
	public MainMenuPane() {
		super(new MainMenuScaledPane());
		imageLayer().setGamePane(this);
	}
	
	@Override
	public MainMenuScaledPane imageLayer() {
		return (MainMenuScaledPane) super.imageLayer();
	}
	
}
