package minigames.running.imagelayer;

import java.util.*;

import base.*;
import base.panes.ImagePane;
import fxutils.Images;
import javafx.scene.input.*;
import minigames.sprites.*;
import players.Player;

public class Runner extends ImagePane implements Updatable, AcceptsInput, SpriteAnimated, Alignable {

	public static final List<Runner> LIST =
			Collections.unmodifiableList(Arrays.asList(new Runner(1), new Runner(2), new Runner(3), new Runner(4)));
	
	private static final double X = 192;

	public static Runner get(int number) {
		return LIST.get(Player.validate(number) - 1);
	}
	
	private final int number;
	private final SpriteAnimator animator;
	
	private Runner(int number) {
		super(Images.stillSprite(number));
		this.number = number;
		this.animator = new SpriteAnimator(this);
		setIdealX(X);
	}
	
	@Override
	public void update(long diff) {
		animator().update(diff);
	}
	
	@Override
	public void keyPressed(KeyCode kc) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyCode kc) {
		// TODO Auto-generated method stub
	}

	@Override
	public void alignFor(int playerCount) {
		if(number() > playerCount)
			throw new IllegalArgumentException(String.format("number() > playerCount (%d > %d)", number(), playerCount));
		Coords c = Coords.p(playerCount);
		setIdealBottomY(c.playerBottomY(number));
	}

	@Override
	public int number() {
		return number;
	}
	
	@Override
	public SpriteAnimator animator() {
		return animator;
	}
}
