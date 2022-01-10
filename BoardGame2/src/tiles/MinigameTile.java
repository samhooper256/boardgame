package tiles;

import fxutils.Images;
import game.MainScene;
import minigames.*;
import minigames.archery.ArcheryMinigame;
import players.Player;

public class MinigameTile extends Tile {
	
	public MinigameTile() {
		super(Images.ARCHERY);
	}

	@Override
	public void land(Player p) {
		// TODO Auto-generated method stub
		MainScene.get().startMinigame(ArcheryMinigame.get());
	}
	
}
