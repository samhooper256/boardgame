package game.playerselect;

import base.panes.AbstractImageLayer;
import game.MainScene;

import static game.playerselect.SelectIcon.*;
import static game.MainScene.*;

public class PlayerSelectImageLayer extends AbstractImageLayer {

	private static final double
			SELECT_HEIGHT = MainScene.DEFAULT_HEIGHT * .55,
			SELECT_SEPARATION = 128 + 32;
	
	public PlayerSelectImageLayer() {
		SELECT2.setIdealCenter(CENTER_X - SELECT_SEPARATION, SELECT_HEIGHT);
		SELECT3.setIdealCenter(CENTER_X, SELECT_HEIGHT);
		SELECT4.setIdealCenter(CENTER_X + SELECT_SEPARATION, SELECT_HEIGHT);
		addAll(SELECT2, SELECT3, SELECT4);
	}
	
	@Override
	public void updatePane(long diff) {
		// TODO Auto-generated method stub
		
	}
	
}
