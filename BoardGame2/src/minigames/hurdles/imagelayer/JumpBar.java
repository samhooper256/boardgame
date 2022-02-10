package minigames.hurdles.imagelayer;

import java.util.*;

import base.*;
import base.input.GameInput;
import base.panes.ImagePane;
import fxutils.Images;
import game.MainScene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import players.Player;

public class JumpBar extends ImagePane implements Updatable, AcceptsInput {

	public static final double
		Y = MainScene.DEFAULT_HEIGHT - HurdlesImageLayer.GROUND_HEIGHT + 20,
		HEIGHT = Images.JUMP_BAR.getHeight();
	
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
		setIdealCenterX(Coords.get().xCenter(number));
		setIdealY(Y);
		clip = new Rectangle(Images.JUMP_BAR.getWidth(), 0);
		resetClip();
		setClip(clip);
		chargingElapsed = -1;
	}
	
	public int number() {
		return number;
	}

	@Override
	public void update(long diff) {
		if(isCharging()) {
			chargingElapsed += diff;
			double h = charge() * MAX_CLIP_HEIGHT;
			double y = MAX_CLIP_HEIGHT - h;
			clip.setHeight(h);
			clip.setLayoutY(y);
		}
	}
	
	@Override
	public void keyPressed(KeyCode kc) {
		if(kc == GameInput.controls().single(number) && Jumper.get(number).onGround())
			startCharging();
	}
	
	@Override
	public void keyReleased(KeyCode kc) {
		if(kc == GameInput.controls().single(number) && Jumper.get(number).onGround())
			stopCharging();
	}
	
	public void startCharging() {
		chargingElapsed = 0;
	}
	
	public void stopCharging() {
		if(!hasCharge())
			return;
		Jumper.get(number).tryJump(charge());
		chargingElapsed = -1;
		resetClip();
	}
	
	public boolean isCharging() {
		return chargingElapsed >= 0 && !isFullyCharged();
	}
	
	public boolean isFullyCharged() {
		return chargingElapsed >= CHARGE_TIME;
	}
	
	public boolean hasCharge() {
		return chargingElapsed >= 0;
	}

	public double charge() {
		return Math.min(1, (double) chargingElapsed / CHARGE_TIME);
	}
	
	public JumpBarBackground background() {
		return JumpBarBackground.get(number);
	}
	
	private void resetClip() {
		clip.setHeight(0);
		clip.setLayoutY(MAX_CLIP_HEIGHT);
	}
	
}
