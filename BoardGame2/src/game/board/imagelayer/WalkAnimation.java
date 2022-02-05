package game.board.imagelayer;

import base.Updatable;
import game.board.Board;
import players.Player;

public final class WalkAnimation implements Updatable {

	private static final long STEP_DURATION = (long) .2e9;
	
	private final Player player;
	private final int destIndex;
	
	private long elapsed;
	
	public WalkAnimation(Player player, int distance) {
		this.player = player;
		this.destIndex = pIndex() + distance;
	}
	
	@Override
	public void update(long diff) {
		if(!isFinished()) {
			elapsed += diff;
			if(elapsed > STEP_DURATION) {
				Board.il().movePlayer(player, pIndex() + 1);
				elapsed %= STEP_DURATION;
			}
			if(isFinished())
				Board.il().walkFinished();
		}
	}
	
	public boolean isFinished() {
		return pIndex() == destIndex;
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
