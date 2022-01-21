package minigames;

import java.util.Set;

import medals.MedalReward;

public interface MinigameResult {

	static MinigameResult of(MedalReward... rewards) {
		return new MinigameResultImpl(rewards);
	}
	
	/** The returned {@link Set} is unmodifiable. */
	Set<MedalReward> rewards();
	
}
