package players.passives;

import players.Player;

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
