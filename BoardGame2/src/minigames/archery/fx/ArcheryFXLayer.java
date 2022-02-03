package minigames.archery.fx;

import base.MainScene;
import base.panes.*;
import minigames.archery.ArcheryMinigame;

public class ArcheryFXLayer extends FXLayer {
	
	private final WinBox winBox;
	private final WaveText waveText;
	
	public ArcheryFXLayer() {
		super();
		winBox = new WinBox();
		//TODO factor out centering a node?
		winBox.layoutXProperty().bind(winBox.widthProperty().multiply(.5).negate().add(MainScene.DEFAULT_WIDTH * .5));
		winBox.layoutYProperty().bind(winBox.heightProperty().multiply(.5).negate().add(MainScene.DEFAULT_HEIGHT * .5));
		winBox.setVisible(false);
		waveText = new WaveText();
		waveText.layoutXProperty().bind(waveText.widthProperty().multiply(.5).negate().add(MainScene.DEFAULT_WIDTH * .5));
		waveText.layoutYProperty().bind(waveText.heightProperty().multiply(.5).negate().add(MainScene.DEFAULT_HEIGHT * .5));
		waveText.setVisible(false);
		getChildren().addAll(waveText, winBox);
	}

	public void showWinner(int player) {
		winBox.setWinner(player);
		winBox.setVisible(true);
	}
	
	public void start() {
		winBox.setVisible(false);
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
