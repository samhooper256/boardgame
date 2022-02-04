package game;

import base.*;
import base.input.GameInput;
import base.panes.*;
import fxutils.*;
import game.board.*;
import game.mainmenu.MainMenu;
import game.pause.PauseLayer;
import game.playerselect.PlayerSelect;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import minigames.*;

public class MainScene extends Scene implements Updatable {

	public static final double
			DEFAULT_WIDTH = 1920,
			DEFAULT_HEIGHT = 1080,
			CENTER_X = DEFAULT_WIDTH * .5,
			CENTER_Y = DEFAULT_HEIGHT * .5;
	
	private static final double SELECT_LAYER_HIDDEN_Y = 1e6;
	
	private static final MainScene INSTANCE = new MainScene();
	
	static {
		INSTANCE.init();
	}
	
	private final DoubleBinding hscaleBinding, wscaleBinding;
	//The root contains the selectLayer (0), contentLayer (1), fadeLayer (2) and glassLayer (3).
	//The contentLayer contains at the bottom either the MainMenu, the Board, or a Minigame. It contains the
	//BoardFade on top. The selectLayer contains the PlayerSelect.
	private final Pane root;
	private final StackPane selectLayer, contentLayer, fadeLayer;
	private final Timer timer;
	private final PauseLayer pauseLayer;
	private final BoardFade boardFade;
	private final PlayerSelectFade playerSelectFade;
	private final ToPlayerSelectAnimation tpsAnimation;
	
	private UnaffiliatedFXLayer glassLayer; //can't be final for initialization reasons...
	private boolean paused;
	
	public static MainScene get() {
		return INSTANCE;
	}
	
	private MainScene() {
		super(new Pane(), DEFAULT_WIDTH * .5, DEFAULT_HEIGHT * .5);
		hscaleBinding = heightProperty().divide(DEFAULT_HEIGHT);
		wscaleBinding = widthProperty().divide(DEFAULT_WIDTH);
		pauseLayer = new PauseLayer();
		pauseLayer.fader().setFadeOutFinishedAction(this::unpauseFinished);
		Nodes.setPrefSize(pauseLayer, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		paused = false;
		root = (Pane) getRoot();
		boardFade = new BoardFade();
		playerSelectFade = new PlayerSelectFade();
		fadeLayer = new StackPane(boardFade, playerSelectFade);
		fadeLayer.setMouseTransparent(true);
		selectLayer = new StackPane();
		contentLayer = new StackPane();
		contentLayer.prefWidthProperty().bind(widthProperty());
		contentLayer.prefHeightProperty().bind(heightProperty());
		selectLayer.prefWidthProperty().bind(widthProperty());
		selectLayer.prefHeightProperty().bind(heightProperty());
		selectLayer.setLayoutY(SELECT_LAYER_HIDDEN_Y);
		selectLayer.setVisible(false);
		fadeLayer.prefWidthProperty().bind(widthProperty());
		fadeLayer.prefHeightProperty().bind(heightProperty());
		root.getChildren().addAll(contentLayer, selectLayer, fadeLayer);
		tpsAnimation = new ToPlayerSelectAnimation();
		getStylesheets().add(Main.class.getResource(Main.RESOURCES_PREFIX + "style.css").toExternalForm());
		setOnKeyPressed(this::keyPressed);
		setOnKeyReleased(this::keyReleased);
		setOnMouseClicked(this::mouseClicked);
		setOnMousePressed(this::mousePressed);
		setOnMouseReleased(this::mouseReleased);
		timer = new Timer(this::update);
	}
	
	private void init() {
		glassLayer = new UnaffiliatedFXLayer();
		glassLayer.getChildren().add(pauseLayer);
		root.getChildren().add(glassLayer);
		contentLayer.getChildren().addAll(MainMenu.get());
		selectLayer.getChildren().addAll(PlayerSelect.get());
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
					requestUnpause();
				else
					pause();
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
	
	public void animateToPlayerSelect() {
		tpsAnimation.playFromStart();
	}
	
	private void showContentLayer() {
		Nodes.setLayout(contentLayer, 0, 0);
		selectLayer.setVisible(false);
		selectLayer.setLayoutY(SELECT_LAYER_HIDDEN_Y);
	}
	
	public void transitionToNewGame(int playerCount) {
		playerSelectFade.transitionToNewGame(playerCount);
	}
	
	public void startGame(int playerCount) {
		Board.get().start(playerCount);
		setBaseContent(Board.get());
		showContentLayer();
	}

	public void startMinigame(Minigame minigame) {
		boardFade.toMinigame(minigame);
	}
	
	public void fadeBackFromMinigame(MinigameResult result) {
		boardFade.toBoard(result);
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
	
	/** @throws IllegalStateException if not {@link #ingame()}. */
	public void returnToMainMenu() {
		if(!ingame())
			throw new IllegalStateException("Already on the main menu");
		if(paused)
			unpauseInstantly();
		setBaseContent(MainMenu.get());
	}
	
	/** Returns {@code true} if the player is in the game. (In other words, if the main menu isn't showing). */
	public boolean ingame() {
		return baseContent() != MainMenu.get();
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
		pauseLayer().fader().fadeIn();
		paused = true;
	}
	
	private void requestUnpause() {
		if(!pauseLayer().fader().isFading())
			unpause();
	}
	
	public void unpause() {
		pauseLayer().fader().fadeOutAndHide(); //the fade out finished action will call unpauseFinished()
	}
	
	private void unpauseFinished() {
		glassLayer.setMouseTransparent(true);
		paused = false;
	}
	
	public void unpauseInstantly() {
		pauseLayer().fader().disappear();
		unpauseFinished();
	}
	
	StackPane selectLayer() {
		return selectLayer;
	}
	
	StackPane contentLayer() {
		return contentLayer;
	}
	
}
