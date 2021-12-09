package tiles;

import base.MainScene;
import fxutils.Images;
import minigames.*;
import players.Player;

public class MinigameTile extends Tile {
	
	public MinigameTile() {
		super(Images.ARCHERY);
	}

	@Override
	public void land(Player p) {
		// TODO Auto-generated method stub
		MainScene.get().startMinigame(new ArcheryMinigame());
	}
	
}
