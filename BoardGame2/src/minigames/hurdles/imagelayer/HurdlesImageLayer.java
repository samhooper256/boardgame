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
		Jumper one = Jumper.get(1);
		add(ground);
		one.setIdealCenterX(200);
		add(one);
		addAll(JumpBar.get(3).background(), JumpBar.get(3));
		JumpBar.get(3).setIdealCoords(20, MainScene.DEFAULT_HEIGHT - GROUND_HEIGHT + 20);
		JumpBar.get(3).background().setIdealCoords(20, MainScene.DEFAULT_HEIGHT - GROUND_HEIGHT + 20);
	}
	
	@Override
	public void startMinigame() {
		Jumper.get(1).fixToGroundLevel();
	}

	@Override
	public void keyPressedIngame(KeyCode kc) {
		for(Jumper j : Jumper.LIST)
			j.keyPressed(kc);
	}

	@Override
	public void keyReleasedIngame(KeyCode kc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateIngame(long diff) {
		for(Jumper j : Jumper.LIST)
			j.update(diff);
		for(JumpBar jb : JumpBar.LIST)
			jb.update(diff);
	}

	public ImagePane ground() {
		return ground;
	}
	
	@Override
	public Hurdles gamePane() {
		return (Hurdles) super.gamePane();
	}
	
}
