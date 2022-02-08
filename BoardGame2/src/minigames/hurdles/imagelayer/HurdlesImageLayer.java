package minigames.hurdles.imagelayer;

import base.panes.ImagePane;
import fxutils.Images;
import game.MainScene;
import javafx.scene.input.KeyCode;
import minigames.*;
import minigames.hurdles.Hurdles;

public class HurdlesImageLayer extends MinigameImageLayer {

	public static final double GROUND_HEIGHT = Images.HURDLES_GROUND.getHeight();
	
	private final ImagePane ground;
	
	public HurdlesImageLayer() {
		super(MiniTag.HURDLES);
		ground = new ImagePane(Images.HURDLES_GROUND);
		ground.setIdealY(MainScene.DEFAULT_HEIGHT - GROUND_HEIGHT);
		add(ground);
	}
	
	@Override
	public void startMinigame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressedIngame(KeyCode kc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleasedIngame(KeyCode kc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateIngame(long diff) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Hurdles gamePane() {
		return (Hurdles) super.gamePane();
	}
	
}
