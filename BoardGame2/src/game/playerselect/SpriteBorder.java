package game.playerselect;

import java.util.*;

import base.panes.ImagePane;
import fxutils.Images;
import players.Player;

public class SpriteBorder extends ImagePane {
	
	public static final double THICKNESS = 17;
	
	public static final List<SpriteBorder> LIST =
			Arrays.asList(new SpriteBorder(), new SpriteBorder(), new SpriteBorder(), new SpriteBorder());
	
	public static SpriteBorder forPlayer(int player) {
		return LIST.get(Player.validate(player) - 1);
	}
	
	public SpriteBorder() {
		super(Images.AVATAR_INACTIVE_BORDER);
	}
	
	public void setActive(boolean active) {
		if(active)
			setImage(Images.AVATAR_ACTIVE_BORDER);
		else
			setImage(Images.AVATAR_INACTIVE_BORDER);
	}
	
}
