package game.playerselect;

import java.util.*;

import base.panes.ImagePane;
import fxutils.Images;
import players.Player;

public class AvatarBorder extends ImagePane {
	
	public static final List<AvatarBorder> LIST =
			Arrays.asList(new AvatarBorder(), new AvatarBorder(), new AvatarBorder(), new AvatarBorder());
	
	public static AvatarBorder forPlayer(int player) {
		return LIST.get(Player.validate(player) - 1);
	}
	
	public AvatarBorder() {
		super(Images.AVATAR_INACTIVE_BORDER);
	}
	
	public void setActive(boolean active) {
		if(active)
			setImage(Images.AVATAR_ACTIVE_BORDER);
		else
			setImage(Images.AVATAR_INACTIVE_BORDER);
	}
	
}
