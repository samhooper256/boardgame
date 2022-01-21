package medals;

import static medals.Medal.*;

public final class MedalUtils {

	private MedalUtils() {
		
	}
	
	/** Returns {@code 0} for {@link Medal#GOLD}, {@code 1} for {@link Medal#SILVER}, and {@code 2} for
	 * {@link Medal#BRONZE}.*/
	public static int index(Medal medal) {
		return medal.equals(GOLD) ? 0 : medal.equals(SILVER) ? 1 : 2;
	}
	
}
