package minigames;

import medals.*;
import players.Player;

import static medals.Medal.*;
import static medals.MedalReward.to;

import java.util.*;
import java.util.stream.Collectors;

public interface MinigameResult {

	/** Duplicate {@link MedalReward MedalRewards} will be removed. */
	static MinigameResult of(MedalReward... rewards) {
		return new MinigameResultImpl(rewards);
	}
	
	/** Duplicate {@link MedalReward MedalRewards} will be removed. */
	static MinigameResult of(Collection<MedalReward> rewards) {
		return new MinigameResultImpl(rewards);
	}
	
	public static MinigameResult from(Set<Survival> survivals) {
		//maps each wave that a player died on to the unmodifiable set (sorted ascending) of all players who survived to
		//that wave. The keys in the map are in descending order.
		SortedMap<Integer, SortedSet<Integer>> groups = survivals.stream().collect(Collectors.groupingBy(
			Survival::dist,
			() -> new TreeMap<>(Comparator.reverseOrder()),
			Collectors.collectingAndThen(
				Collectors.mapping(Survival::number, Collectors.toCollection(TreeSet::new)),
				Collections::unmodifiableSortedSet
			)
		));
		List<MedalReward> rewards = new ArrayList<>();
		Medal medal = Medal.GOLD;
		for(Map.Entry<Integer, SortedSet<Integer>> e : groups.entrySet()) {
			for(int player : e.getValue())
				rewards.add(MedalReward.to(medal, player));
			medal = medal.down();
			if(medal == null)
				break;
		}
		return of(rewards);
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
	
	/** Returns a {@link Set} of all the different {@link Medal Medals} that are used in the {@link #rewards()} of this
	 * {@link MinigameResult}. The returned {@link Set} is unmodifiable. */
	default Set<Medal> medals() {
		return groups().keySet();
	}
	
	/** The {@link #rewards() rewards} of this {@link MinigameResult}
	 * {@link Collectors#groupingBy(java.util.function.Function) grouped by} their {@link MedalReward#medal() medal}.
	 * The keys are sorted by the natural order of {@link Medal}. */
	SortedMap<Medal, Set<MedalReward>> groups();
	
}
