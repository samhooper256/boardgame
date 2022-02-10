package minigames.hurdles.imagelayer;

import java.util.*;

import base.panes.ImagePane;
import fxutils.Images;
import players.Player;

public class JumpBar extends ImagePane {

	public static final List<JumpBar> LIST = Collections.unmodifiableList(Arrays.asList(
			new JumpBar(1), new JumpBar(2), new JumpBar(3), new JumpBar(4)));
	
	public static JumpBar get(int number) {
		return LIST.get(Player.validate(number) - 1);
	}
	
	private final int number;
	
	private JumpBar(int number) {
		super(Images.JUMP_BAR);
		this.number = number;
	}
	
	public int number() {
		return number;
	}
	
}
