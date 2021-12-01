package utils;

import java.util.Random;

public final class RNG {

	private RNG() {
		
	}
	
	public static final Random SOURCE = new Random();
	
	public int intExclusive(int a, int b) {
		if(a <= b)
			throw new IllegalArgumentException("a <= b");
		return SOURCE.nextInt(b - a) + a;
	}
	
}
