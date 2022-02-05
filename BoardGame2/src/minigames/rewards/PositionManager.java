package minigames.rewards;

import minigames.MinigameResult;
import players.PlayerIcon;

import static game.MainScene.*;

import java.util.*;

import fxutils.Images;
import medals.*;

public final class PositionManager {

	private static final double
			TOP_Y = DEFAULT_HEIGHT * .32,
			BOTTOM_Y = DEFAULT_HEIGHT * .68,
			HEIGHT_AVAILABLE = BOTTOM_Y - TOP_Y,
			WIDTH_AVAILABLE = Images.REWARDS_BACKGROUND.getWidth(),
			LEFT_X = CENTER_X - WIDTH_AVAILABLE * .5;
	
	private final RewardsDisplay rd;
	
	public PositionManager(RewardsDisplay rd) {
		this.rd = rd;
	}
	
	public void positionFor(MinigameResult mr) {
		int mcount = mr.medals().size();
		int	pcount = mr.groups().values().stream().mapToInt(Set::size).max().getAsInt();
		double yspacing = HEIGHT_AVAILABLE / (mcount + 1), xspacing = WIDTH_AVAILABLE / (pcount + 2);
		Medal m = Medal.GOLD;
		for(int i = 1; i <= mcount; i++) {
			MedalIcon medal = rd.medal(m);
			medal.setIdealCenter(LEFT_X + xspacing, TOP_Y + yspacing * i);
			m = m.down();
		}
		int groupIndex = 1;
		for(Set<MedalReward> group : mr.groups().values()) {
			int i = 2;
			for(MedalReward r : group) {
				PlayerIcon p = rd.player(r.playerNumber());
				p.setIdealCenter(LEFT_X + xspacing * i, TOP_Y + yspacing * groupIndex);
				i++;
			}
			groupIndex++;
		}
	}
	
}
