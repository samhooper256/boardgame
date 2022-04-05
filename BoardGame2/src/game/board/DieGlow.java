package game.board;

import base.Updatable;
import base.panes.ImagePane;
import fxutils.Images;
import game.board.imagelayer.Ring;
import utils.Screen;

public class DieGlow extends ImagePane implements Updatable {

	private static final long FADE_DURATION = (long) (Ring.FADE_IN_DURATION.toSeconds() * 1e9);
	
	public static DieGlow get() {
		return INSTANCE;
	}
	
	private static final DieGlow INSTANCE = new DieGlow();
	
	private long elapsed;
	private boolean fadingIn;
	
	private DieGlow() {
		super(Images.DIE_GLOW);
		Screen.center(this);
		setVisible(true);
		setOpacity(0);
		fadingIn = true;
	}
	
	@Override
	public void update(long diff) {
		if(elapsed == -1)
			return;
		elapsed += diff;
		if(elapsed >= FADE_DURATION)
			elapsed = FADE_DURATION;
		if(fadingIn)
			setOpacity((double) elapsed / FADE_DURATION);
		else
			setOpacity(1 - (double) elapsed / FADE_DURATION);
		if(elapsed == FADE_DURATION)
			elapsed = -1;
	}
	
	public void startFadingIn() {
		fadingIn = true;
		elapsed = 0;
	}
	
	public void startFadingOut() {
		fadingIn = false;
		elapsed = 0;
	}
	
	public void hide() {
		elapsed = -1;
		setOpacity(0);
	}
	
}
