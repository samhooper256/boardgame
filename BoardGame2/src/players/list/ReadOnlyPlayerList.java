package players.list;

import java.util.Collection;

public interface ReadOnlyPlayerList extends Iterable<Integer> {

	Integer get(int index);
	
	int indexOf(Integer p);
	
	/** Starts at the smallest index {@code i} such that {@code (get(i) > player)}. If there is no such index
	 * {@code i}, returns {@code get(0)}. If there is such an index, returns {@code get(i)}.*/
	default Integer smallestGreaterWrapping(int player) {
		for(int i = 0; i < size(); i++)
			if(get(i) > player)
				return get(i);
		return get(0);
	}
	
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
