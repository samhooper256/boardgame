package minigames.archery.fx;

import game.MainScene;
import minigames.MinigameFXLayer;
import minigames.archery.Archery;

public class ArcheryFXLayer extends MinigameFXLayer {
	
	private final WaveText waveText;
	
	public ArcheryFXLayer() {
		super();
		waveText = new WaveText();
		waveText.layoutXProperty().bind(waveText.widthProperty().multiply(.5).negate().add(MainScene.DEFAULT_WIDTH * .5));
		waveText.layoutYProperty().bind(waveText.heightProperty().multiply(.5).negate().add(MainScene.DEFAULT_HEIGHT * .5));
		waveText.setVisible(false);
		getChildren().addAll(waveText);
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
	public Archery gamePane() {
		return (Archery) super.gamePane();
	}
	
}
