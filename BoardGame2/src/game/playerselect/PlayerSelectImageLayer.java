package game.playerselect;

import base.panes.AbstractImageLayer;
import players.*;

import static game.playerselect.CountSelect.*;
import static game.MainScene.*;

public class PlayerSelectImageLayer extends AbstractImageLayer {

	private static final double
			AVATAR_Y_DIST = DEFAULT_HEIGHT * .25,
			AVATAR_X_DIST = DEFAULT_WIDTH * .35,
			ICON_PADDING = 6,
			CENTAUR_X_OFFSET = 12;
	
	private final CountSelectHover[] hovers;
	private final PlayerIcon[] icons;
	
	public PlayerSelectImageLayer() {
		runningSprite(1).setIdealCenter(CENTER_X - AVATAR_X_DIST, CENTER_Y - AVATAR_Y_DIST);
		runningSprite(2).setIdealCenter(CENTER_X + AVATAR_X_DIST, CENTER_Y - AVATAR_Y_DIST);
		runningSprite(3).setIdealCenter(CENTER_X - AVATAR_X_DIST, CENTER_Y + AVATAR_Y_DIST);
		runningSprite(4).setIdealCenter(CENTER_X + AVATAR_X_DIST, CENTER_Y + AVATAR_Y_DIST);
		for(int i = 1; i <= Player.maxCount(); i++)
			SpriteBorder.forPlayer(i).setIdealCenter(runningSprite(i).getIdealCenter());
		hovers = new CountSelectHover[Player.maxCount() - 1]; // for 2, 3, and 4 players.
		for(int i = 0; i < hovers.length; i++)
			hovers[i] = new CountSelectHover();
		for(int count = 2; count <= Player.maxCount(); count++) {
			CountSelect cs = CountSelect.forCount(count);
			hover(count).setIdealCenter(cs.getIdealCenterX(), cs.getIdealCenterY());
		}
		icons = new PlayerIcon[Player.maxCount()];
		for(int i = 1; i <= Player.maxCount(); i++) {
			icons[i - 1] = new PlayerIcon(i);
			SpriteBorder sb = SpriteBorder.forPlayer(i);
			icons[i - 1].setIdealCoords(
				sb.getIdealX() + SpriteBorder.THICKNESS + ICON_PADDING,
				sb.getIdealY() + SpriteBorder.THICKNESS + ICON_PADDING
			);
		}
		RunningSprite.forPlayer(PlayerNumbers.CENTAUR).shiftIdealX(CENTAUR_X_OFFSET);
		addAll(hovers);
		addAll(SELECT2, SELECT3, SELECT4);
		addAll(SpriteBorder.LIST);
		addAll(RunningSprite.LIST);
		addAll(icons);
	}
	
	void notifyAnimationToPlayerSelectStarted() {
		for(SpriteBorder sb : SpriteBorder.LIST)
			sb.setActive(false);
		for(CountSelectHover csh : hovers)
			csh.setVisible(false);
	}
	
	private CountSelectHover hover(int player) {
		return hovers[player - 2];
	}
	
	void hoverEntered(int playerCount) {
		for(int count = 2; count <= Player.maxCount(); count++)
			if(count != playerCount)
				hover(count).setVisible(false);
		hover(playerCount).setVisible(true);
		int i;
		for(i = 1; i <= playerCount; i++)
			SpriteBorder.forPlayer(i).setActive(true);
		for(; i <= Player.maxCount(); i++)
			SpriteBorder.forPlayer(i).setActive(false);
	}
	
	private RunningSprite runningSprite(int number) {
		return RunningSprite.forPlayer(number);
	}
	
	@Override
	public void updatePane(long diff) {
		for(RunningSprite rs : RunningSprite.LIST)
			rs.update(diff);
	}
	
}
