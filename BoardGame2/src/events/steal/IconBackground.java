package events.steal;

import java.util.*;

import base.panes.ImagePane;
import fxutils.*;
import game.board.Board;
import players.*;

public class IconBackground extends ImagePane implements Fadeable, PlayerNumbered {

	public static final List<IconBackground> LIST = Collections.unmodifiableList(Arrays.asList(
			new IconBackground(1), new IconBackground(1), new IconBackground(1), new IconBackground(1)));
	
	public static IconBackground of(int number) {
		return LIST.get(Player.validate(number) - 1);
	}
	
	private final int number;
	private final Fader fader;
	
	private IconBackground(int number) {
		super(Images.STEAL_ICON_BACKGROUND);
		this.number = number;
		fader = new Fader(this).setDurations(Board.EVENT_FADE_DURATION);
	}
	
	@Override
	public int number() {
		return number;
	}

	@Override
	public Fader fader() {
		return fader;
	}
	
}
