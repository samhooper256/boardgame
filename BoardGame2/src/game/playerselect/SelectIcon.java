package game.playerselect;

import base.panes.ImagePane;
import fxutils.Images;
import game.MainScene;

public class SelectIcon extends ImagePane {

	public static final SelectIcon
			SELECT2 = new SelectIcon(2),
			SELECT3 = new SelectIcon(3),
			SELECT4 = new SelectIcon(4);
	
	private final int playerCount;
	
	private SelectIcon(int playerCount) {
		super(Images.playerCountIcon(playerCount));
		this.playerCount = playerCount;
		setOnMouseClicked(eh -> onClick());
	}
	
	public int playerCount() {
		return playerCount;
	}
	
	private void onClick() {
		MainScene.get().startGame(playerCount);
	}
	
}
