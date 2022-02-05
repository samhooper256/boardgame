package minigames.archery;

/** A {@link #player()} along with the {@link #wave()} they survived to. */
public final class Survival implements Comparable<Survival> {

	private final int player, wave;
	
	public Survival(int player, int wave) {
		this.player = player;
		this.wave = wave;
	}

	public int player() {
		return player;
	}

	public int wave() {
		return wave;
	}
	
	@Override
	public int compareTo(Survival o) {
		int c = Integer.compare(wave, o.wave);
		return c == 0 ? Integer.compare(player, o.player) : c;
	}
	
}
