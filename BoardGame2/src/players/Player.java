package players;

import java.util.*;

import base.panes.ImagePane;
import fxutils.Images;
import tiles.*;

public class Player extends ImagePane {

	private static final int MAX_COUNT = 4;
	
	private static final Player[] PLAYERS = {
			new Player(1),
			new Player(2),
			new Player(3),
			new Player(4)
	};
	
	/** @return {@code player} 
	 * @throws IllegalArgumentException if {@code (player < 1 || player > maxCount())}. */
	public static int validate(int player) {
		if(player < 1 || player > maxCount())
			throw new IllegalArgumentException(String.format("Invalid player number: %d", player));
		return player;
	}
	
	/** Returns the maximum number of players. */
	public static int maxCount() {
		return MAX_COUNT;
	}
	
	public static Player get(int n) {
		return PLAYERS[n - 1];
	}
	
	private final int number;
	private final List<Passive> passives;
	
	private Tile current;
	private RollType rollType;
	
	private Player(int number) {
		super(Images.player(number));
		this.number = number;
		passives = new ArrayList<>();
		current = StartTile.get();
		rollType = RollType.CHOOSE; //TODO change later
	}

	public void acquirePassive(Passive p) {
		p.activate();
		passives.add(p);
	}
	
	public void turnFinished() {
		for(int i = 0; i < passives.size(); i++) {
			Passive p = passives.get(i);
			if(p instanceof TemporaryPassive) {
				TemporaryPassive tp = (TemporaryPassive) p;
				tp.turnElapsed();
				if(tp.turnsRemaining() == 0) {
					passives.remove(i);
					i--;
				}
			}
		}
	}
	
	public Tile tile() {
		return current;
	}
	
	public void setTile(Tile newTile) {
		current = newTile;
	}
	
	public int number() {
		return number;
	}
	
	public RollType rollType() {
		return rollType;
	}

	public void setRollType(RollType rollType) {
		this.rollType = rollType;
	}
	
	@Override
	public String toString() {
		return String.format("Player[%d]", number());
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj == this;
	}
	
	@Override
	public int hashCode() {
		return number();
	}
	
}
