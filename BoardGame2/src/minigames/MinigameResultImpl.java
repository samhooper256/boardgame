package minigames;

import java.util.*;
import java.util.stream.Collectors;

import medals.*;

final class MinigameResultImpl implements MinigameResult {

	/** Unmodifiable. */
	private final Set<MedalReward> rewards;
	/** Unmodifiable. */
	private final SortedMap<Medal, Set<MedalReward>> groups;
	
	public MinigameResultImpl(MedalReward... rewards) {
		Set<MedalReward> set = new HashSet<>();
		Collections.addAll(set, rewards);
		this.rewards = Collections.unmodifiableSet(set);
		groups = getGroups();
	}
	
	/** The given {@link Collection} is defensively copied, and duplicates are removed. */
	public MinigameResultImpl(Collection<MedalReward> rewards) {
		this.rewards = Collections.unmodifiableSet(new HashSet<>(rewards));
		groups = getGroups();
	}
	
	private SortedMap<Medal, Set<MedalReward>> getGroups() {
		return Collections.unmodifiableSortedMap(
			rewards.stream().collect(Collectors.groupingBy(
				MedalReward::medal,
				() -> new TreeMap<>(), //does not compile as a method reference :(
				Collectors.collectingAndThen(
					Collectors.toSet(),
					Collections::unmodifiableSet
				)
			))
		);
	}
	
	@Override
	public Set<MedalReward> rewards() {
		return rewards;
	}

	@Override
	public SortedMap<Medal, Set<MedalReward>> groups() {
		return groups;
	}
	
}
