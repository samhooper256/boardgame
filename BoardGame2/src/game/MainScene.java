package game;

import base.*;
import base.input.GameInput;
import base.panes.*;
import fxutils.Nodes;
import game.pause.PauseLayer;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import mainmenu.*;
import minigames.*;

public class MainScene extends Scene implements Updatable {

	public static final double
			DEFAULT_WIDTH = 1920,
			DEFAULT_HEIGHT = 1080,
			CENTER_X = DEFAULT_WIDTH * .5,
			CENTER_Y = DEFAULT_HEIGHT * .5;
	
	private static final MainScene INSTANCE = new MainScene();
	
	static {
		INSTANCE.init();
	}
	
	private final DoubleBinding hscaleBinding, wscaleBinding;
	//The root contains the glassLayer (top) and the contentLayer (bottom).
	//The contentLayer contains the content and (at times) the FadeLayer.
	private final Pane root;
	private final StackPane contentLayer;
	private final Timer timer;
	private final PauseLayer pauseLayer;
	private final BoardFadeLayer boardFadeLayer; //can't be final for initialization reasons...
	
	private UnaffiliatedFXLayer glassLayer; //can't be final for initialization reasons...
	private boolean paused;
	
	public static MainScene get() {
		return INSTANCE;
	}
	
	private MainScene() {
		super(new Pane(), DEFAULT_WIDTH * .5, DEFAULT_HEIGHT * .5);
		pauseLayer = new PauseLayer();
		Nodes.setPrefSize(pauseLayer, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		paused = false;
		root = (Pane) getRoot();
		boardFadeLayer = new BoardFadeLayer();
		contentLayer = new StackPane();
		root.getChildren().addAll(contentLayer);
		contentLayer.prefWidthProperty().bind(widthProperty());
		contentLayer.prefHeightProperty().bind(heightProperty());
		hscaleBinding = heightProperty().divide(DEFAULT_HEIGHT);
		wscaleBinding = widthProperty().divide(DEFAULT_WIDTH);
		timer = new Timer(this::update);
		getStylesheets().add(Main.class.getResource(Main.RESOURCES_PREFIX + "style.css").toExternalForm());
	}
	
	private void init() {
		glassLayer = new UnaffiliatedFXLayer();
		glassLayer.getChildren().add(pauseLayer);
		root.getChildren().add(glassLayer);
		contentLayer.getChildren().addAll(MainMenuPane.get(), boardFadeLayer);
		this.setOnKeyPressed(this::keyPressed);
		this.setOnKeyReleased(this::keyReleased);
		this.setOnMouseClicked(this::mouseClicked);
		this.setOnMousePressed(this::mousePressed);
		this.setOnMouseReleased(this::mouseReleased);
		timer.start();
	}
	
	public void setBaseContent(GamePane gp) {
		contentLayer.getChildren().set(0, gp);
	}
	
	private void keyPressed(KeyEvent ke) {
		KeyCode kc = ke.getCode();
		if(GameInput.isPressed(kc))
			return;
		GameInput.keysPressed().add(kc);
		baseContent().keyPressed(kc);
	}
	
	private void keyReleased(KeyEvent ke) {
		KeyCode kc = ke.getCode();
		GameInput.keysPressed().remove(kc);
		if(kc == KeyCode.F11) {
			Main.stage().setFullScreen(!Main.stage().isFullScreen());
		}
		else if(kc == GameInput.controls().pause()) {
			if(ingame()) {
				if(paused)
					unpause();
				else
					pause();
				paused = !paused;
			}
		}
		else {
			baseContent().keyReleased(kc);
		}
	}
	
	private void mouseClicked(MouseEvent me) {
		if(paused)
			return;
		if(isPlayingMinigame())
			currentMinigame().mouseClicked(me);
	}
	
	private void mousePressed(MouseEvent me) {
		if(paused)
			return;
		if(isPlayingMinigame())
			currentMinigame().mousePressed(me);
	}
	
	private void mouseReleased(MouseEvent me) {
		if(paused)
			return;
		if(isPlayingMinigame())
			currentMinigame().mouseReleased(me);
	}
	
	@Override
	public void update(long diff) {
		if(!paused) {
			if(isPlayingMinigame())
				currentMinigame().update(diff);
			else
				Board.get().update(diff);
		}
	}
	
	public void startGame(int playerCount) {
		Board.get().start(playerCount);
		setBaseContent(Board.get());
	}

	public void startMinigame(Minigame minigame) {
		boardFadeLayer.toMinigame(minigame);
	}
	
	public void fadeBackFromMinigame(MinigameResult result) {
		boardFadeLayer.toBoard(result);
	}
	
	public void removeFadeLayer() {
		contentLayer.getChildren().remove(boardFadeLayer);
	}
	
	public boolean isPlayingMinigame() {
		return !contentLayer.getChildren().isEmpty() && baseContent() instanceof Minigame;
	}
	
	/** If a {@link Minigame} is not {@link #isPlayingMinigame() playing}, returns {@code null}. */
	public Minigame currentMinigame() {
		if(isPlayingMinigame())
			return (Minigame) baseContent();
		return null;
	}
	
	/** Returns {@code true} if the player is in the game. (In other words, if the main menu isn't showing). */
	public boolean ingame() {
		return baseContent() != MainMenuPane.get();
	}
	
	public GamePane baseContent() {
		return (GamePane) contentLayer.getChildren().get(0);
	}
	
	public DoubleBinding hscaleBinding() {
		return hscaleBinding;
	}
	
	public DoubleBinding wscaleBinding() {
		return wscaleBinding;
	}

	public double hscale() {
		return hscaleBinding().get();
	}

	public double wscale() {
		return wscaleBinding().get();
	}
	
	public PauseLayer pauseLayer() {
		return pauseLayer;
	}
	
	public void pause() {
		glassLayer.setMouseTransparent(false);
		pauseLayer().fader().fadeIn(); //TODO
	}
	
	public void unpause() {
		glassLayer.setMouseTransparent(true);
		pauseLayer().fader().fadeOutAndHide();
	}
	
}
