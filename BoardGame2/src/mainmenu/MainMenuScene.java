package mainmenu;

import javafx.scene.Scene;

public class MainMenuScene extends Scene {

	private static final MainMenuScene INSTANCE = new MainMenuScene();
	
	public static MainMenuScene get() {
		return INSTANCE;
	}
	
	private MainMenuScene() {
		super(MainMenuPane.get(), 640, 360);
	}

}
