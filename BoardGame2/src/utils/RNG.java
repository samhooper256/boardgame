package utils;

import java.util.*;

public final class RNG {

	private RNG() {
		
	}
	
	public static final Random SOURCE = new Random();
	
	/** From {@code 0} (inclusive) to {@code highExclusive}. */
	public static int intExclusive(int highExclusive) {
		return intExclusive(0, highExclusive);
	}
	
	public static int intExclusive(int a, int b) {
		if(a >= b)
			throw new IllegalArgumentException("a <= b");
		return SOURCE.nextInt(b - a) + a;
	}
	
	public static int intInclusive(int a, int b) {
		return intExclusive(a, b + 1);
	}
	
	public static <T> T pick(List<? extends T> list) {
		if(list.isEmpty())
			throw new IllegalArgumentException("List is empty");
		return list.get(intExclusive(list.size()));
	}
	
}
