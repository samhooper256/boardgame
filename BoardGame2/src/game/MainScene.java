package game;

import base.*;
import base.input.GameInput;
import base.panes.GamePane;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import mainmenu.*;
import medals.MedalReward;
import minigames.*;

public class MainScene extends Scene implements Updatable {

	private static final MainScene INSTANCE = new MainScene();
	
	private final StackPane root;
	private final FadeLayer fadeLayer;
	private final Timer timer;
	
	public static MainScene get() {
		return INSTANCE;
	}
	
	private MainScene() {
		super(new StackPane());
		root = (StackPane) getRoot();
		fadeLayer = new FadeLayer();
		setContent(MainMenuPane.get());
		this.setOnKeyPressed(this::keyPressed);
		this.setOnKeyReleased(this::keyReleased);
		this.setOnMouseClicked(this::mouseClicked);
		this.setOnMousePressed(this::mousePressed);
		this.setOnMouseReleased(this::mouseReleased);
		timer = new Timer(this::update);
		timer.start();
	}
	
	private void setContent(GamePane p) {
		clearContent();
		root.getChildren().add(p);
	}
	
	private void keyPressed(KeyEvent ke) {
		KeyCode kc = ke.getCode();
		if(GameInput.isPressed(kc))
			return;
		GameInput.keysPressed().add(kc);
		if(isPlayingMinigame())
			currentMinigame().keyPressed(kc);
	}
	
	private void keyReleased(KeyEvent ke) {
		KeyCode kc = ke.getCode();
		GameInput.keysPressed().remove(kc);
		if(isPlayingMinigame())
			currentMinigame().keyReleased(kc);
	}
	
	private void mouseClicked(MouseEvent me) {
		if(isPlayingMinigame())
			currentMinigame().mouseClicked(me);
	}
	
	private void mousePressed(MouseEvent me) {
		if(isPlayingMinigame())
			currentMinigame().mousePressed(me);
	}
	
	private void mouseReleased(MouseEvent me) {
		if(isPlayingMinigame())
			currentMinigame().mouseReleased(me);
	}
	
	@Override
	public void update(long diff) {
		if(isPlayingMinigame())
			currentMinigame().update(diff);
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
		for(MedalReward reward : mr.rewards())
			reward.apply();
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
