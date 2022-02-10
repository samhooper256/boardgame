package minigames.hurdles.imagelayer;

import java.util.*;

import base.panes.ImagePane;
import fxutils.Images;
import players.Player;

public class JumpBarBackground extends ImagePane {

	public static final List<JumpBarBackground> LIST = Collections.unmodifiableList(Arrays.asList(
			new JumpBarBackground(1), new JumpBarBackground(2), new JumpBarBackground(3), new JumpBarBackground(4)));
	
	public static JumpBarBackground get(int player) {
		return LIST.get(Player.validate(player) - 1);
	}
	
	private final int number;
	
	public JumpBarBackground(int number) {
		super(Images.JUMP_BAR_BACKGROUND);
		this.number = number;
	}
	
	public int number() {
		return number;
	}
	
}
