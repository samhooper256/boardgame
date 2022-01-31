package game;

import base.*;
import base.input.GameInput;
import base.panes.*;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import mainmenu.*;
import medals.MedalReward;
import minigames.*;

public class MainScene extends Scene implements Updatable {

	private static final MainScene INSTANCE = new MainScene();
	
	static {
		INSTANCE.init();
	}
	
	private final DoubleBinding hscaleBinding, wscaleBinding;
	private final StackPane root;
	private final Timer timer;
	
	private FadeLayer fadeLayer; //can't be final for initialization reasons...
	
	public static MainScene get() {
		return INSTANCE;
	}
	
	private MainScene() {
		super(new StackPane());
		root = (StackPane) getRoot();
		hscaleBinding = root.heightProperty().divide(ScaledImageLayer.DEFAULT_HEIGHT);
		wscaleBinding = root.widthProperty().divide(ScaledImageLayer.DEFAULT_WIDTH);
		timer = new Timer(this::update);
		getStylesheets().add(Main.class.getResource(Main.RESOURCES_PREFIX + "style.css").toExternalForm());
	}
	
	private void init() {
		fadeLayer = new FadeLayer();
		setContent(MainMenuPane.get());
		this.setOnKeyPressed(this::keyPressed);
		this.setOnKeyReleased(this::keyReleased);
		this.setOnMouseClicked(this::mouseClicked);
		this.setOnMousePressed(this::mousePressed);
		this.setOnMouseReleased(this::mouseReleased);
		timer.start();
	}
	
	private void setContent(GamePane p) {
		ObservableList<Node> c = root.getChildren();
		if(c.isEmpty()) { //only on initial startup.
			c.add(p);
		}
		else {
			c.set(0, p);
			c.subList(1, c.size()).clear();
		}
	}
	
	private void keyPressed(KeyEvent ke) {
		KeyCode kc = ke.getCode();
		if(GameInput.isPressed(kc))
			return;
		GameInput.keysPressed().add(kc);
		content().keyPressed(kc);
	}
	
	private void keyReleased(KeyEvent ke) {
		KeyCode kc = ke.getCode();
		GameInput.keysPressed().remove(kc);
		content().keyReleased(kc);
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
		else
			Board.get().update(diff);
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
	
	public boolean isPlayingMinigame() {
		return !root.getChildren().isEmpty() && root.getChildren().get(0) instanceof Minigame;
	}
	
	/** If a {@link Minigame} is not {@link #isPlayingMinigame() playing}, returns {@code null}. */
	public Minigame currentMinigame() {
		if(isPlayingMinigame())
			return (Minigame) content();
		return null;
	}
	
	public GamePane content() {
		return (GamePane) root.getChildren().get(0);
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
	
}
