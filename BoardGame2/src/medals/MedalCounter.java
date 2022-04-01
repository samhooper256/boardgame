package medals;

import java.util.*;

import static medals.Medal.*;

public class MedalCounter {

	private final Map<Medal, Integer> map;
	private final List<Runnable> changeListeners;
	
	public MedalCounter() {
		this(0, 0, 0);
	}
	
	public MedalCounter(int gold, int silver, int bronze) {
		map = new HashMap<>();
		map.put(GOLD, gold);
		map.put(SILVER, silver);
		map.put(BRONZE, bronze);
		changeListeners = new ArrayList<>();
	}
	
	public int get(Medal medal) {
		return map.get(medal);
	}
	
	public void add(Medal medal) {
		map.put(medal, get(medal) + 1);
		runChangeListeners();
	}
	
	/** Removes one medal of the given tier. Returns {@code true} iff the medal was successfully removed (it will only
	 * unsuccessful if there were no medals of the given tier). */
	public boolean remove(Medal medal) {
		int current = get(medal);
		boolean result = current != 0;
		if(result)
			map.put(medal, current - 1);
		runChangeListeners();
		return result;
	}
	
	public void removeOrThrow(Medal medal) {
		if(!remove(medal))
			throw new IllegalStateException(String.format("Could not remove any: %s", medal));
	}
	
	public void clear(Medal medal) {
		map.put(medal, 0);
		runChangeListeners();
	}
	
	public void reset() {
		for(Medal m : Medal.LIST)
			clear(m);
	}
	
	/** Returns the highest (most valuable) tier of medal this {@link MedalCounter} has, or {@code null} if
	 * {@link #isEmpty()}. */
	public Medal getHighest() {
		if(get(Medal.GOLD) > 0)
			return Medal.GOLD;
		if(get(Medal.SILVER) > 0)
			return Medal.SILVER;
		if(get(Medal.BRONZE) > 0)
			return Medal.BRONZE;
		return null;
	}
	
	public int size() {
		return map.get(Medal.BRONZE) + map.get(Medal.SILVER) + map.get(Medal.GOLD);
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public int score() {
		int score = 0;
		for(Map.Entry<Medal, Integer> e : map.entrySet())
			score += e.getValue() * e.getKey().pointValue();
		return score;
	}
	
	/** The given {@link Runnable} will be run immediately after any change to any medal count. */
	public void addChangeListener(Runnable changeListener) {
		changeListeners.add(changeListener);
	}
	
	private void runChangeListeners() {
		for(Runnable changeListener : changeListeners)
			changeListener.run();
	}
	
}
