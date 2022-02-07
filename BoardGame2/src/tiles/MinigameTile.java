package tiles;

import game.MainScene;
import minigames.MiniTag;
import players.Player;

public class MinigameTile extends Tile {
	
	private final MiniTag tag;
	
	/** Generates a new {@link MinigameTile} with a random minigame. */
	public MinigameTile() {
		this(MiniTag.random());
	}

	public MinigameTile(MiniTag tag) {
		super(tag.tileImage());
		this.tag = tag;
	}
	
	@Override
	public void land(Player p) {
		MainScene.get().startMinigame(tag.minigame());
	}
	
	public MiniTag tag() {
		return tag;
	}
	
}
