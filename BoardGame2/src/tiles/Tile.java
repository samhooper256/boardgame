package tiles;

import java.util.*;

import base.ImagePane;
import game.Board;
import javafx.scene.image.Image;
import players.Player;

public abstract class Tile extends ImagePane {

	private final Set<Player> players;
	
	public Tile(Image tileImage) {
		super(tileImage);
		players = new HashSet<>(Player.MAX_PLAYER_COUNT);
	}
	
	public int index() {
		return Board.get().tileIndex(this);
	}

	/** The returned {@link Set} is modifiable. */
	public Set<Player> players() {
		return players;
	}
	
	public void arrangePlayers() {
		if(players.isEmpty())
			return;
		Iterator<Player> itr = players.iterator();
		Player first = itr.next();
		if(players.size() == 1) {
			first.setIdealCenter(this.getIdealCenterX(), this.getIdealCenterY());
			return;
		}
		Player second = itr.next();
		if(players.size() == 2) {
			first.setIdealCenter(this.getIdealCenterX() - this.getIdealWidth() * .25,
					this.getIdealCenterY() - this.getIdealHeight() * .25);
			second.setIdealCenter(this.getIdealCenterX() + this.getIdealWidth() * .25,
					this.getIdealCenterY() + this.getIdealHeight() * .25);
			return;
		}
		Player third = itr.next();
		if(players.size() == 3) {
			first.setIdealCenter(this.getIdealCenterX() - this.getIdealWidth() * .25,
					this.getIdealCenterY() - this.getIdealHeight() * .25);
			second.setIdealCenter(this.getIdealCenterX() + this.getIdealWidth() * .25,
					this.getIdealCenterY() - this.getIdealHeight() * .25);
			third.setIdealCenter(this.getIdealCenterX(), this.getIdealCenterY() + this.getIdealHeight() * .25);
			return;
		}
		Player fourth = itr.next();
		if(players.size() == 4) {
			first.setIdealCenter(this.getIdealCenterX() - this.getIdealWidth() * .25,
					this.getIdealCenterY() - this.getIdealHeight() * .25);
			second.setIdealCenter(this.getIdealCenterX() + this.getIdealWidth() * .25,
					this.getIdealCenterY() - this.getIdealHeight() * .25);
			third.setIdealCenter(this.getIdealCenterX() - this.getIdealWidth() * .25,
					this.getIdealCenterY() + this.getIdealHeight() * .25);
			fourth.setIdealCenter(this.getIdealCenterX() + this.getIdealWidth() * .25,
					this.getIdealCenterY() + this.getIdealHeight() * .25);
			return;
		}
		throw new UnsupportedOperationException(String.format("Unsupported amount of players: %d", players.size()));
	}
	
	/** Called when a {@link Player} lands on this {@link Tile}.
	 * Assumes the given {@link Player} is already in this {@link Tile Tile's} set of {@link #players() players}.*/
	public abstract void land(Player p);
	
}
