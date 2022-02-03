package tiles;

import base.MainScene;
import fxutils.Images;
import minigames.archery.ArcheryMinigame;
import players.Player;

public class MinigameTile extends Tile {
	
	public MinigameTile() {
		super(Images.ARCHERY);
	}

	@Override
	public void land(Player p) {
		MainScene.get().startMinigame(ArcheryMinigame.get()); //TODO - other minigames
	}
	
}
