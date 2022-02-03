package game.playerselect;

import base.panes.ImagePane;
import fxutils.Images;
import game.MainScene;

public class CountSelect extends ImagePane {

	public static final CountSelect
			SELECT2 = new CountSelect(2),
			SELECT3 = new CountSelect(3),
			SELECT4 = new CountSelect(4);
	
	private final int playerCount;
	
	private CountSelect(int playerCount) {
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
