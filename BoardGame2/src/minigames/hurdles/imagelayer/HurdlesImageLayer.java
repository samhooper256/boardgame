package minigames.hurdles.imagelayer;

import base.panes.*;
import fxutils.Images;
import javafx.scene.input.KeyCode;
import minigames.*;
import minigames.hurdles.Hurdles;

public class HurdlesImageLayer extends MinigameImageLayer {

	public HurdlesImageLayer() {
		super(MiniTag.HURDLES);
		add(new ImagePane(Images.ARCHERY_BACKGROUND));
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