package players.passives;

import players.Player;

public class Injury extends AbstractTemporaryPassive {

	private static final int TURNS = 2;
	
	public Injury(Player p) {
		super(p, TURNS);
	}
	
	@Override
	public void activate() {
		player().setInjured(true);
	}
	
	@Override
	public void wearOff() {
		player().setInjured(false);
	}
	
}
