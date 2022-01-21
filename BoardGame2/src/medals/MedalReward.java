package medals;

import java.util.Objects;

import players.Player;

import static medals.Medal.*;
import static medals.MedalUtils.index;

/** An immutable object storing a {@link Medal} and the index of the {@link Player} it should be awarded to. */
public final class MedalReward {
	
	private static final MedalReward[][] STORE = new MedalReward[Medal.count()][Player.maxCount() + 1];
	
	static {
		for(int p = 1; p <= Player.maxCount(); p++) {
			STORE[index(GOLD)][p] = new MedalReward(GOLD, p);
			STORE[index(SILVER)][p]= new MedalReward(SILVER, p);
			STORE[index(BRONZE)][p] = new MedalReward(BRONZE, p);
		}
	}
	
	public static MedalReward to(Medal medal, int playerNumber) {
		return STORE[index(medal)][playerNumber];
	}
	
	private final Medal medal;
	private final int playerNumber;
	
	private MedalReward(Medal medal, int playerNumber) {
		this.medal = medal;
		this.playerNumber = playerNumber;
	}

	public Medal medal() {
		return medal;
	}
	
	public int playerNumber() {
		return playerNumber;
	}
	
	public Player player() {
		return Player.get(playerNumber());
	}

	@Override
	public int hashCode() {
		return Objects.hash(medal, playerNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof MedalReward))
			return false;
		MedalReward o = (MedalReward) obj;
		return Objects.equals(medal, o.medal) && playerNumber == o.playerNumber;
	}

}
