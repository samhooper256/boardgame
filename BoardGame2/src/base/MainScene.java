package base;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import mainmenu.*;

public class MainScene extends Scene {

	private static final MainScene INSTANCE = new MainScene();
	
	private final StackPane root;
	
	public static MainScene get() {
		return INSTANCE;
	}
	
	private MainScene() {
		super(new StackPane());
		root = (StackPane) getRoot();
		root.getChildren().add(MainMenuPane.get());
	}
	
	public void startGame() {
		root.getChildren().clear();
		root.getChildren().add(Board.get());
	}
	
}
