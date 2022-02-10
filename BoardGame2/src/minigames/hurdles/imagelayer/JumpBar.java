package minigames.hurdles.imagelayer;

import java.util.*;

import base.Updatable;
import base.panes.ImagePane;
import fxutils.Images;
import javafx.scene.shape.Rectangle;
import players.Player;

public class JumpBar extends ImagePane implements Updatable {

	private static final double MAX_CLIP_HEIGHT = Images.JUMP_BAR.getHeight();
	/** in nanoseconds. */
	private static final long CHARGE_TIME = (long) 2e9;
	
	public static final List<JumpBar> LIST = Collections.unmodifiableList(Arrays.asList(
			new JumpBar(1), new JumpBar(2), new JumpBar(3), new JumpBar(4)));
	
	public static JumpBar get(int number) {
		return LIST.get(Player.validate(number) - 1);
	}
	
	private final int number;
	private final Rectangle clip;
	
	private long chargingElapsed;
	
	private JumpBar(int number) {
		super(Images.JUMP_BAR);
		this.number = number;
		clip = new Rectangle(Images.JUMP_BAR.getWidth(), 0);
		setClip(clip);
		chargingElapsed = -1;
	}
	
	public int number() {
		return number;
	}

	@Override
	public void update(long diff) {
		if(chargingElapsed >= 0 && chargingElapsed < CHARGE_TIME) {
			chargingElapsed += diff;
			double percent = Math.min(1, (double) chargingElapsed / CHARGE_TIME);
			double h = percent * MAX_CLIP_HEIGHT;
			double y = MAX_CLIP_HEIGHT - h;
			clip.setHeight(h);
			clip.setLayoutY(y);
		}
	}
	
	public void startCharging() {
		chargingElapsed = 0;
	}
	
	public JumpBarBackground background() {
		return JumpBarBackground.get(number);
	}
	
}
