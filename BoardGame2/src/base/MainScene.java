package base;

import game.Board;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import mainmenu.*;
import minigames.Minigame;

public class MainScene extends Scene {

	private static final MainScene INSTANCE = new MainScene();
	
	private final StackPane root;
	private final FadeLayer fadeLayer;
	
	public static MainScene get() {
		return INSTANCE;
	}
	
	private MainScene() {
		super(new StackPane());
		root = (StackPane) getRoot();
		fadeLayer = new FadeLayer();
		setContent(MainMenuPane.get());
	}
	
	private void setContent(Pane p) {
		clearContent();
		root.getChildren().add(p);
	}
	
	public void startGame() {
		setContent(Board.get());
	}

	public void startMinigame(Minigame mg) {
		root.getChildren().addAll(fadeLayer);
		fadeLayer.fadeIn(mg);
	}
	
	public void setRootBase(Pane p) {
		if(root.getChildren().isEmpty())
			root.getChildren().add(p);
		else
			root.getChildren().set(0, p);
	}
	
	private void clearContent() {
		root.getChildren().clear();
	}
	
	public boolean isPlayingMinigame() {
		return !root.getChildren().isEmpty() && root.getChildren().get(0) instanceof Minigame;
	}
	
}
