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
	
	public void clear(Medal medal) {
		map.put(medal, 0);
		runChangeListeners();
	}
	
	public void reset() {
		for(Medal m : Medal.LIST)
			clear(m);
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
