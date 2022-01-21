package minigames;

import java.util.Set;

import medals.*;
import players.Player;

import static medals.Medal.*;
import static medals.MedalReward.to;

public interface MinigameResult {

	/** Duplicate {@link MedalReward MedalRewards} will be removed. */
	static MinigameResult of(MedalReward... rewards) {
		return new MinigameResultImpl(rewards);
	}
	
	/** {@code players.length} must be between 2 and {@link Player#maxCount()} (inclusive). Gives the first player
	 * a {@link Medal#GOLD}, the second a {@link Medal#SILVER}, and the third a {@link Medal#BRONZE}. */
	static MinigameResult simple(int... players) {
		if(players.length == 2)
			return of(to(GOLD, players[0]), to(SILVER, players[1]));
		if(players.length >= 3 && players.length <= Player.maxCount())
			return of(to(GOLD, players[0]), to(SILVER, players[1]), to(BRONZE, players[2]));
		throw new IllegalArgumentException(String.format("Player count: %d", players.length));
	}
	
	/** The returned {@link Set} is unmodifiable. */
	Set<MedalReward> rewards();
	
}
