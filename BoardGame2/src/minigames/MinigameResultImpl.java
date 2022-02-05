package minigames;

import java.util.*;
import java.util.stream.Collectors;

import medals.*;

final class MinigameResultImpl implements MinigameResult {

	/** Unmodifiable. */
	private final Set<MedalReward> rewards;
	/** Unmodifiable. */
	private final Set<Medal> medals;
	
	public MinigameResultImpl(MedalReward... rewards) {
		Set<MedalReward> set = new HashSet<>();
		Collections.addAll(set, rewards);
		this.rewards = Collections.unmodifiableSet(set);
		medals = getMedals();
	}
	
	/** The given {@link Collection} is defensively copied, and duplicates are removed. */
	public MinigameResultImpl(Collection<MedalReward> rewards) {
		this.rewards = Collections.unmodifiableSet(new HashSet<>(rewards));
		medals = getMedals();
	}
	
	private Set<Medal> getMedals() {
		return Collections.unmodifiableSet(this.rewards.stream().map(MedalReward::medal).collect(Collectors.toSet()));
	}
	
	@Override
	public Set<MedalReward> rewards() {
		return rewards;
	}
	
	@Override
	public Set<Medal> medals() {
		return medals;
	}
	
}
