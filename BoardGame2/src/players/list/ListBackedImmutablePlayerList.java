package players.list;

import java.util.*;

final class ListBackedImmutablePlayerList implements ImmutablePlayerList {

	/** The given {@link List} will be copied and {@link PlayerList#validate(List) validated}. */
	public static ListBackedImmutablePlayerList fromCopy(List<Integer> list) {
		return new ListBackedImmutablePlayerList(new ArrayList<>(PlayerList.validate(list)));
	}
	
	/** The given {@link List} will be copied but will not be {@link PlayerList#validate(List) validated}. */
	public static ListBackedImmutablePlayerList fromCopyAssumedValid(List<Integer> list) {
		return new ListBackedImmutablePlayerList(new ArrayList<>(list));
	}
	
	/** The given {@link List} will not be copied but will still be {@link PlayerList#validate(List) validated}. */
	public static ListBackedImmutablePlayerList fromTrusted(List<Integer> list) {
		return new ListBackedImmutablePlayerList(PlayerList.validate(list));
	}
	
	private final List<Integer> list;
	
	/** The given {@link List} is neither defensively copied nor {@link PlayerList#validate(List) validated}. */
	private ListBackedImmutablePlayerList(List<Integer> list) {
		this.list = list;
	}

	@Override
	public Integer get(int index) {
		return list.get(index);
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public PlayerList mutableCopy() {
		return PlayerList.fromCopyAssumedValid(list);
	}

	@Override
	public Iterator<Integer> iterator() {
		Iterator<Integer> itr = list.iterator();
		return new Iterator<Integer>() { //needed to make it immutable.

			@Override
			public boolean hasNext() {
				return itr.hasNext();
			}

			@Override
			public Integer next() {
				return itr.next();
			}
			
		};
	}
	
}
