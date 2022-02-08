package players.list;

import java.util.*;

import players.Player;

/** <p>A collection of <em>unique</em>, always <em>sorted</em> integers that only allows adding
 * {@link Player#validate(int) valid} player numbers. {@link PlayerList PlayerLists} also have a {@link #get(int)}
 * method, like {@link List Lists}. </p>
 * <p>{@link #addAll(int, Collection)} is unsupported and throws an {@link UnsupportedOperationException}.</p> */
public class PlayerList implements Collection<Integer>, ReadOnlyPlayerList {

	public static PlayerList all() {
		PlayerList all = new PlayerList();
		for(int p = 1; p <= Player.maxCount(); p++)
			all.add(p);
		return all;
	}
	
	public static PlayerList upTo(int maxInclusive) {
		Player.validate(maxInclusive);
		PlayerList list = new PlayerList();
		for(int p = 1; p <= maxInclusive; p++)
			list.add(p);
		return list;
	}
	
	static PlayerList fromCopyAssumedValid(List<Integer> list) {
		return new PlayerList(new ArrayList<>(list));
	}
	
	/** Returns the given {@link List}.
	 * @throws IllegalArgumentException if the given {@link List} does not describe a valid {@link PlayerList}. */
	public static List<Integer> validate(List<Integer> list) {
		if(list.isEmpty())
			return list;
		if(list.size() > Player.maxCount())
			throw new IllegalArgumentException(String.format("Invalid: %s", list));
		Player.validate(list.get(0));
		for(int i = 1; i < list.size(); i++) {
			Player.validate(list.get(i));
			if(list.get(i - 1) >= list.get(i))
				throw new IllegalArgumentException(String.format("Invalid: %s", list));
		}
		return list;
	}
	
	public static PlayerList empty() {
		return new PlayerList();
	}
	
	private final List<Integer> list;
	
	private PlayerList() {
		list = new ArrayList<>(Player.maxCount());
	}
	
	/** The given {@link List} is assumed to describe a {@link PlayerList#validate(List) valid} {@link PlayerList}.
	 * The given {@link List} is not defensively copied. */
	private PlayerList(List<Integer> trustedList) {
		list = trustedList;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public Iterator<Integer> iterator() {
		return list.iterator();
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
	public boolean add(Integer e) {
		if(contains(e))
			return false;
		list.add(e);
		sort();
		return true;
	}

	@Override
	public boolean remove(Object o) {
		return list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Integer> c) {
		boolean changed = false;
		for(Integer p : c)
			changed |= add(p);
		return changed;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	@Override
	public void clear() {
		list.clear();
	}
	
	private void sort() {
		Collections.sort(list);
	}

	@Override
	public Integer get(int index) {
		return list.get(index);
	}
	
	@Override
	public int indexOf(Integer p) {
		return list.indexOf(p);
	}
	
	@Override
	public PlayerList mutableCopy() {
		return new PlayerList(new ArrayList<>(list)); 
	}

	@Override
	public ImmutablePlayerList frozen() {
		return ListBackedImmutablePlayerList.fromCopyAssumedValid(list);
	}
	
	@Override
	public String toString() {
		return list.toString();
	}
	
}
