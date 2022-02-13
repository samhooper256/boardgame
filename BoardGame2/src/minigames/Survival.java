package minigames;

import players.PlayerNumbered;

/** A {@link #number()} along with the {@link #dist()} they survived to. */
public final class Survival implements Comparable<Survival>, PlayerNumbered {

	private final int number, dist;
	
	public Survival(int number, int dist) {
		this.number = number;
		this.dist = dist;
	}

	@Override
	public int number() {
		return number;
	}

	public int dist() {
		return dist;
	}
	
	@Override
	public int compareTo(Survival o) {
		int c = Integer.compare(dist, o.dist);
		return c == 0 ? Integer.compare(number, o.number) : c;
	}
	
	@Override
	public String toString() {
		return String.format("Survival[p%d to %d]", number(), dist());
	}
	
}
