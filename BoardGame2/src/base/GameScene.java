package base;

import javafx.scene.Scene;

public class GameScene extends Scene {

	private static final GameScene INSTANCE = new GameScene();
	
	public static GameScene get() {
		return INSTANCE;
	}
	
	private GameScene() {
		super(Board.get(), 640, 360);
	}
	
}
