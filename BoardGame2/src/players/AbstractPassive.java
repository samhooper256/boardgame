package players;

public abstract class AbstractPassive implements Passive {

	private final Player player;
	
	public AbstractPassive(Player player) {
		this.player = player;
	}
	
	@Override
	public Player player() {
		return player;
	}
	
}
