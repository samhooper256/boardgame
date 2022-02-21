package utils;

/** All indices start from the right (least significant bits). For example, in the number 10011 (base 2), the final 1
 * is at index 0, the 1 to its left is at index 1, the 0 to its left is at index 2, etc. Bit 32 in an {@code int} is the
 * sign bit and bit 64 in a {@code long} is a signed bit. All right shifting is done with the unsigned operator
 * ({@code >>>}); this class ignores sign. */
public final class Bits {

	private Bits() {
		
	}
	
	public static boolean isSet(long val, int index) {
		return (val & mask(index)) > 0;
	}
	
	/** A {@code long} mask whose only set bit is the one at the given index. */
	public static long mask(int index) {
		return 1L << index;
	}
	
}
