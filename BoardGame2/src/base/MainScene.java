package base;

import game.Board;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import mainmenu.*;
import minigames.Minigame;

public class MainScene extends Scene {

	private static final MainScene INSTANCE = new MainScene();
	
	private final StackPane root;
	
	public static MainScene get() {
		return INSTANCE;
	}
	
	private MainScene() {
		super(new StackPane());
		root = (StackPane) getRoot();
		setContent(MainMenuPane.get());
	}
	
	private void setContent(Pane p) {
		root.getChildren().clear();
		root.getChildren().add(p);
	}
	
	public void startGame() {
		setContent(Board.get());
	}

	public void startMinigame(Minigame mg) {
		setContent(mg);
	}
	
}
