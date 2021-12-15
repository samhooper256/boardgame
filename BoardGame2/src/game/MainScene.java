package game;

import base.FadeLayer;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import mainmenu.*;
import minigames.*;

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
		this.setOnKeyPressed(this::keyPressed);
	}
	
	private void setContent(Pane p) {
		clearContent();
		root.getChildren().add(p);
	}
	
	private void keyPressed(KeyEvent ke) {
		if(isPlayingMinigame())
			currentMinigame().keyPressed(ke);
	}
	
	public void startGame() {
		Board.get().start();
		setContent(Board.get());
	}

	public void startMinigame(Minigame mg) {
		root.getChildren().add(fadeLayer);
		fadeLayer.fadeIn(mg, mg::start, null);
	}
	
	public void fadeBackFromMinigame(MinigameResult mr) {
		root.getChildren().add(fadeLayer);
		fadeLayer.fadeIn(Board.get(), null, () -> Board.get().minigameFinished(mr));
	}
	
	public void removeFadeLayer() {
		root.getChildren().remove(fadeLayer);
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
	
	/** If a {@link Minigame} is not {@link #isPlayingMinigame() playing}, returns {@code null}. */
	public Minigame currentMinigame() {
		if(isPlayingMinigame())
			return (Minigame) root.getChildren().get(0);
		return null;
	}
	
}
