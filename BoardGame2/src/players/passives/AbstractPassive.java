package players.passives;

import players.Player;

public abstract class AbstractPassive implements Passive {

	private final Player player;
	private final PassiveTag tag;
	
	public AbstractPassive(PassiveTag tag, Player player) {
		this.tag = tag;
		this.player = player;
	}
	
	@Override
	public Player player() {
		return player;
	}
	
	@Override
	public PassiveTag tag() {
		return tag;
	}
	
}
