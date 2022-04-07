package players;

import java.util.*;

import medals.MedalCounter;
import players.passives.*;
import tiles.*;

public class Player extends PlayerIcon {

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
	
	public static void resetAll() {
		for(int i = 1; i <= maxCount(); i++)
			get(i).reset();
	}
	
	private final List<Passive> passives;
	private final MedalCounter medalCounter;
	
	private Tile current;
	private RollType rollType;
	private boolean injured;
	
	private Player(int number) {
		super(number);
		medalCounter = new MedalCounter();
		passives = new ArrayList<>();
		reset();
	}

	public void reset() {
		setRollType(RollType.RANDOM);
		medalCounter().reset();
		passives.clear();
		current = StartTile.get();
	}
	
	/** If this {@link Player} already {@link #hasPassive(PassiveTag) has} a {@link Passive} with the given
	 * {@link PassiveTag tag}, then all of the following occur:
	 * <ol>
	 * <li>{@link Passive#activate()} is <em>not</em> called on the given passive</li>
	 * <li>The given passive is <em>not</em> added to {@link #passivesUnmodifiable()}</li>
	 * <li>The {@link TemporaryPassive#turnsRemaining() turnsRemaining()} of the current passive with the given's tag
	 * is incremented by the given's number of turns remaining, if applicable. </li>
	 * </ol>*/
	public void acquirePassive(Passive p) {
		if(hasPassive(p.tag())) {
			if(p.isTemporary()) {
				TemporaryPassive current = (TemporaryPassive) getPassive(p.tag());
				current.setTurnsRemaining(current.turnsRemaining() + ((TemporaryPassive) p).turnsRemaining());
			}
			//if p is not temporary, do nothing.
		}
		else {
			p.activate();
			passives.add(p);
		}
	}

	public boolean hasPassive(PassiveTag tag) {
		return getPassive(tag) != null;
	}
	
	/** Returns {@code null} if no {@link Passive} with the given {@link PassiveTag}. */
	public Passive getPassive(PassiveTag tag) {
		for(Passive p : passives)
			if(p.tag() == tag)
				return p;
		return null;
	}
	
	
	public List<Passive> passivesUnmodifiable() {
		return Collections.unmodifiableList(passives);
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
	
	public int score() {
		return medalCounter.score();
	}
	
	public void setTile(Tile newTile) {
		current = newTile;
	}
	
	public RollType rollType() {
		return rollType;
	}

	public void setRollType(RollType rollType) {
		this.rollType = rollType;
	}
	
	public MedalCounter medalCounter() {
		return medalCounter;
	}
	
	public boolean isInjured() {
		return injured;
	}
	
	public void setInjured(boolean injured) {
		this.injured = injured;
	}
	
	@Override
	public String toString() {
		return String.format("Player[%d, rollType=%s, injured=%b, passives=%s]", number(), rollType, injured, passives);
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
