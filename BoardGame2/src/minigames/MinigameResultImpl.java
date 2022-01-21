package minigames;

import java.util.*;

import medals.MedalReward;

final class MinigameResultImpl implements MinigameResult {

	/** Unmodifiable. */
	private final Set<MedalReward> rewards;
	
	public MinigameResultImpl(MedalReward... rewards) {
		Set<MedalReward> set = new HashSet<>();
		Collections.addAll(set, rewards);
		this.rewards = Collections.unmodifiableSet(set);
	}
	
	/** The given {@link Collection} is defensively copied, and duplicates are removed. */
	public MinigameResultImpl(Collection<MedalReward> rewards) {
		this.rewards = Collections.unmodifiableSet(new HashSet<>(rewards));
	}
	
	@Override
	public Set<MedalReward> rewards() {
		return rewards;
	}
	
}