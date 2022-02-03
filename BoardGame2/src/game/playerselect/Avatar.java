package game.playerselect;

import java.util.*;

import base.panes.ImagePane;
import fxutils.Images;
import players.Player;

public class Avatar extends ImagePane {

	public static final List<Avatar> LIST = Arrays.asList(new Avatar(1), new Avatar(2), new Avatar(3), new Avatar(4));
	
	public static Avatar forPlayer(int player) {
		return LIST.get(Player.validate(player) - 1); 
	}
	
	private Avatar(int player) {
		super(Images.avatar(player));
	}
	
}
