package utils;

import java.util.*;

public final class Colls {

	private Colls() {
		
	}
	
	@SafeVarargs
	public static <T> List<T> ulist(T... items) {
		return Collections.unmodifiableList(Arrays.asList(items));
	}
	
	public static boolean contains(List<?> list, Object o, int lowInclusive, int highExclusive) {
		for(int i = lowInclusive; i < highExclusive; i++)
			if(Objects.equals(list.get(i), o))
				return true;
		return false;
	}
	
}