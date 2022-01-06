package base;

public interface Updatable {

	/** @param diff The time in nanoseconds since the last {@link #update(long)} call. */
	void update(long diff);
	
}
