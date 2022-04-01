package events.steal;

import java.util.*;

import base.panes.ImagePane;
import fxutils.*;
import game.MainScene;
import game.board.Board;
import javafx.scene.input.MouseEvent;
import medals.Medal;
import players.*;

public class IconBackground extends ImagePane implements Fadeable, PlayerNumbered {

	public static final List<IconBackground> LIST = Collections.unmodifiableList(Arrays.asList(
			new IconBackground(1), new IconBackground(2), new IconBackground(3), new IconBackground(4)));
	
	public static IconBackground of(int number) {
		return LIST.get(Player.validate(number) - 1);
	}
	
	private final int number;
	private final Fader fader;
	
	private IconBackground(int number) {
		super(Images.STEAL_ICON_BACKGROUND);
		this.number = number;
		fader = new Fader(this).setDurations(Board.EVENT_FADE_DURATION);
		MainScene.get().addMouseMoveHandler(me -> setHighlighted(mouseInBounds(me)));
		MainScene.get().addMouseClickHandler(me -> {
			if(isVisible() && getParent() != null && !fader().isFading() && mouseInBounds(me)) {
				Player theif = Player.get(Board.get().turn()), robbed = Player.get(number());
				Medal m = robbed.medalCounter().getHighest();
				if(m != null) {
					robbed.medalCounter().remove(m);
					theif.medalCounter().add(m);
				}
				Board.get().requestEventFinish();
			}
		});
	}
	
	private boolean mouseInBounds(MouseEvent me) {
		double x = me.getX(), y = me.getY(), lx = getLayoutX(), ly = getLayoutY();
		return x >= lx && x <= lx + getWidth() && y >= ly && y <= ly + getHeight();
	}
	
	public void setHighlighted(boolean highlighted) {
		if(highlighted)
			setImage(Images.STEAL_ICON_BACKGROUND_HIGHLIGHTED);
		else
			setImage(Images.STEAL_ICON_BACKGROUND);
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
