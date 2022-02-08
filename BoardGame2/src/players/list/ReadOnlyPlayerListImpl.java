package players.list;

import java.util.*;

final class ReadOnlyPlayerListImpl implements ReadOnlyPlayerList {

	private final PlayerList list;
	
	public ReadOnlyPlayerListImpl(PlayerList list) {
		this.list = list;
	}

	@Override
	public Iterator<Integer> iterator() {
		return list.iterator();
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
		return list.mutableCopy();
	}

	@Override
	public ImmutablePlayerList frozen() {
		return list.frozen();
	}
	
}
