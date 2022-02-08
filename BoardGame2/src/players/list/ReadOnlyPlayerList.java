package players.list;

import java.util.Collection;

public interface ReadOnlyPlayerList extends Iterable<Integer> {

	Integer get(int index);
	
	boolean contains(Object o);
	
	Object[] toArray();

	<T> T[] toArray(T[] a);
	
	boolean containsAll(Collection<?> c);
	
	boolean isEmpty();
	
	int size();
	
	PlayerList mutableCopy();
	
	/** Returns an {@link ImmutablePlayerList} containing the same contents as this {@link PlayerList} at the time this
	 * method is invoked. */
	ImmutablePlayerList frozen();
	
}
