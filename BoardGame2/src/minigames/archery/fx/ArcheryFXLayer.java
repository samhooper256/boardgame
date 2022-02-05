package minigames.archery.fx;

import base.panes.*;
import game.MainScene;
import minigames.archery.ArcheryMinigame;

public class ArcheryFXLayer extends FXLayer {
	
	private final WaveText waveText;
	
	public ArcheryFXLayer() {
		super();
		waveText = new WaveText();
		waveText.layoutXProperty().bind(waveText.widthProperty().multiply(.5).negate().add(MainScene.DEFAULT_WIDTH * .5));
		waveText.layoutYProperty().bind(waveText.heightProperty().multiply(.5).negate().add(MainScene.DEFAULT_HEIGHT * .5));
		waveText.setVisible(false);
		getChildren().addAll(waveText);
	}
	
	public void init() {
		getChildren().addAll(gamePane().rewardsDisplay().fxNodes());
	}
	
	public void start() {
		waveText.setWave(1);
	}
	
	public void startWave(int wave) {
		waveText.setWave(wave);
		waveText.fader().appear();
	}
	
	public WaveText waveText() {
		return waveText;
	}
	
	@Override
	public ArcheryMinigame gamePane() {
		return (ArcheryMinigame) super.gamePane();
	}
	
}
