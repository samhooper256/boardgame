package game.helper;

import base.Memory;
import utils.Bits;

/** Each bit in {@link #info()} corresponds to whether or not the user, during any session of the game at any time, has
 * done a certain action. The actions that each index (see {@link Bits} for the meaning of 'index') in {@link #info()}
 * corresponds to are as follows:
 * <ul>
 * <li>0 - {@link #hasRolledRollableDie()}</li>
 * <li>1 - {@link #hasUsedArcher1()}</li>
 * <li>2 - {@link #hasUsedArcher2()}</li>
 * </ul>
 */
public final class HelperInfo {

	private static final HelperInfo INSTANCE = new HelperInfo(0);
	
	/** {@link #info()} is {@code 0} by default. */
	public static HelperInfo get() {
		return INSTANCE;
	}
	
	public static void acquireFromMemory() {
		INSTANCE.set(Memory.HELPER_INFO);
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
	
	public void setRolledRollableDie() {
		set(Bits.set(info(), 0));
	}
	
	public boolean hasUsedArcher1() {
		return Bits.isSet(info(), 1);
	}
	
	public void setUsedArcher1() {
		set(Bits.set(info(), 1));
	}
	
	public boolean hasUsedArcher2() {
		return Bits.isSet(info(), 2);
	}
	
	public void setUsedArcher2() {
		set(Bits.set(info(), 2));
	}
	
}
