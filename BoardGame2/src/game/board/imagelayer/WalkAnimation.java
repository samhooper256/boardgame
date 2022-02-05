package game.board.imagelayer;

import base.Updatable;
import game.board.Board;
import players.Player;

/** The delay between when a player lands on a tile and when {@link BoardImageLayer#walkFinished()} is called.*/
public final class WalkAnimation implements Updatable {

	private static final long STEP_DURATION = (long) .2e9, LAND_DELAY_TO_MINIGAME = (long) .5e9;
	
	private final Player player;
	private final int destIndex;
	
	private long elapsed;
	private boolean finished;
	
	public WalkAnimation(Player player, int distance) {
		this.player = player;
		this.destIndex = pIndex() + distance;
		finished = false;
	}
	
	@Override
	public void update(long diff) {
		if(finished)
			return;
		elapsed += diff;
		if(pIndex() < destIndex) {
			if(elapsed > STEP_DURATION) {
				Board.il().movePlayer(player, pIndex() + 1);
				elapsed %= STEP_DURATION;
			}
		}
		else if(elapsed > LAND_DELAY_TO_MINIGAME) {
			Board.il().walkFinished();
			finished = true;
		}
	}
	
	public boolean isFinished() {
		return finished;
	}

	private int pIndex() {
		return player().tile().index();
	}
	
	public Player player() {
		return player;
	}
	
	public int destTileIndex() {
		return destIndex;
	}
	
}
