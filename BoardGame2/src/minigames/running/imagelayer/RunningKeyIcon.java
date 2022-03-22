package minigames.running.imagelayer;

import minigames.KeyIcon;

public class RunningKeyIcon extends KeyIcon implements Alignable {

	public RunningKeyIcon(int number) {
		super(number);
	}
	
	@Override
	public void alignFor(int playerCount) {
		if(number() > playerCount)
			throw new IllegalArgumentException(String.format("number() > playerCount (%d > %d)", number(), playerCount));
		alignForTrusted(playerCount);
	}
	
	private void alignForTrusted(int playerCount) {
		Coords c = Coords.p(playerCount);
		setIdealY(c.keyIconY(number()));
		setIdealX(c.keyIconX());
	}
	
}
