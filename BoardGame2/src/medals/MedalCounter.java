package medals;

import java.util.*;

import static medals.Medal.*;

public class MedalCounter {

	private final Map<Medal, Integer> map;
	
	public MedalCounter() {
		this(0, 0, 0);
	}
	
	public MedalCounter(int gold, int silver, int bronze) {
		map = new HashMap<>();
		map.put(GOLD, gold);
		map.put(SILVER, silver);
		map.put(BRONZE, bronze);
	}
	
	public int get(Medal medal) {
		return map.get(medal);
	}
	
	public void add(Medal medal) {
		map.put(medal, get(medal) + 1);
	}
	
	public void clear(Medal medal) {
		map.put(medal, 0);
	}
	
}
