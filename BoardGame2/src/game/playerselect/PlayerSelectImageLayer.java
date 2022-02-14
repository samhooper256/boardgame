package game.playerselect;

import base.panes.AbstractImageLayer;
import players.*;

import static game.playerselect.CountSelect.*;
import static game.MainScene.*;

public class PlayerSelectImageLayer extends AbstractImageLayer {

	private static final double
			AVATAR_Y_DIST = DEFAULT_HEIGHT * .25,
			AVATAR_X_DIST = DEFAULT_WIDTH * .35;
	
	private final CountSelectHover[] hovers;
	private final PlayerIcon[] icons;
	
	public PlayerSelectImageLayer() {
		Avatar.forPlayer(1).setIdealCenter(CENTER_X - AVATAR_X_DIST, CENTER_Y - AVATAR_Y_DIST);
		Avatar.forPlayer(2).setIdealCenter(CENTER_X + AVATAR_X_DIST, CENTER_Y - AVATAR_Y_DIST);
		Avatar.forPlayer(3).setIdealCenter(CENTER_X - AVATAR_X_DIST, CENTER_Y + AVATAR_Y_DIST);
		Avatar.forPlayer(4).setIdealCenter(CENTER_X + AVATAR_X_DIST, CENTER_Y + AVATAR_Y_DIST);
		for(int i = 0; i < AvatarBorder.LIST.size(); i++)
			AvatarBorder.LIST.get(i).setIdealCenter(Avatar.LIST.get(i).getIdealCenter());
		hovers = new CountSelectHover[Player.maxCount() - 1]; // for 2, 3, and 4 players.
		for(int i = 0; i < hovers.length; i++)
			hovers[i] = new CountSelectHover();
		for(int count = 2; count <= Player.maxCount(); count++)
			hover(count).setIdealCenter(CountSelect.forCount(count).getIdealCenter());
		icons = new PlayerIcon[Player.maxCount()];
		for(int i = 1; i <= Player.maxCount(); i++) {
			icons[i - 1] = new PlayerIcon(i);
			icons[i - 1].setIdealCoords(Avatar.forPlayer(i).getIdealCoords());
		}
		addAll(hovers);
		addAll(SELECT2, SELECT3, SELECT4);
		addAll(AvatarBorder.LIST);
		addAll(Avatar.LIST);
		addAll(icons);
		
	}
	
	void notifyAnimationToPlayerSelectStarted() {
		for(AvatarBorder ab : AvatarBorder.LIST)
			ab.setActive(false);
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
			AvatarBorder.forPlayer(i).setActive(true);
		for(; i <= Player.maxCount(); i++)
			AvatarBorder.forPlayer(i).setActive(false);
	}
	
	@Override
	public void updatePane(long diff) {
		// nothing
	}
	
}
