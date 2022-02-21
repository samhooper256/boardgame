package game.helper;

import utils.Bits;

/** Each bit in {@link #info()} corresponds to whether or not the user, during any session of the game at any time, has
 * done a certain action. The actions that each index (see {@link Bits} for the meaning of 'index') in {@link #info()}
 * corresponds to are as follows:<br>
 * 0 - {@link #hasRolledRollableDie()}
 */
public final class HelperInfo {

	private static final HelperInfo INSTANCE = new HelperInfo(0);
	
	/** {@link #info()} is {@code 0} by default. */
	public static HelperInfo get() {
		return INSTANCE;
	}
	
	private long info;
	
	private HelperInfo(long info) {
		set(info);
	}

	public long info() {
		return info;
	}
	
	public void set(long info) {
		this.info = info;
	}
	
	public boolean hasRolledRollableDie() {
		return Bits.isSet(info(), 0);
	}
	
}
