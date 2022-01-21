package medals;

import java.util.Objects;

import players.Player;

import static medals.Medal.*;

/** An immutable object storing a {@link Medal} and the index of the {@link Player} it should be awarded to. */
public final class MedalReward {
	
	private static final MedalReward[][] STORE = new MedalReward[Medal.count()][Player.maxCount() + 1];
	
	static {
		for(int p = 1; p <= Player.maxCount(); p++) {
			STORE[GOLD.index()][p] = new MedalReward(GOLD, p);
			STORE[SILVER.index()][p]= new MedalReward(SILVER, p);
			STORE[BRONZE.index()][p] = new MedalReward(BRONZE, p);
		}
	}
	
	public static MedalReward to(Medal medal, int playerNumber) {
		return STORE[medal.index()][playerNumber];
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

	/** "Applies" this {@link MedalReward}, giving the {@link #medal()} to the {@link #player()}. */
	public void apply() {
		player().medalCounter().add(medal());
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
