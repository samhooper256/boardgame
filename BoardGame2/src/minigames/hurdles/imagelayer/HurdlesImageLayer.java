package minigames.hurdles.imagelayer;

import java.util.*;

import base.panes.ImagePane;
import fxutils.Images;
import javafx.scene.input.KeyCode;
import minigames.*;
import minigames.hurdles.Hurdles;
import players.Player;

public class HurdlesImageLayer extends MinigameImageLayer {

	public static final double
		MAX_HURDLE_SPACING = 1500,
		MIN_HURDLE_SPACING = 400,
		HURDLE_SPACING_DIFF = 10,
		MIN_HURDLE_HEIGHT = 240, //TODO set it back to 100.
		MAX_HURDLE_HEIGHT = 400,
		HURDLE_HEIGHT_DIFF = 10,
		FIRST_HURDLE_X = 1400;
	
	private final ImagePane ground;
	
	private double nextHurdleSpacing, nextHurdleHeight;
	private int nextHurdleIndex;
	private LinkedList<Hurdle> hurdles;
	private final List<KeyIcon> keyIcons;
	
	public HurdlesImageLayer() {
		super(MiniTag.HURDLES);
		ground = new ImagePane(Images.HURDLES_GROUND);
		ground.setIdealY(Coords.get().groundY());
		hurdles = new LinkedList<>();
		add(ground);
		addAll(Jumper.LIST);
		addAll(JumpBarBackground.LIST);
		addAll(JumpBar.LIST);
		addAll(JumpBarTick.LIST);
		keyIcons = new ArrayList<>(Player.maxCount());
		for(int i = 1; i <= Player.maxCount(); i++)
			keyIcons.add(new KeyIcon(i));
		addAll(keyIcons);
	}
	
	@Override
	public void startMinigame() {
		for(Hurdle h : hurdles)
			removeAll(h.imagePanes());
		hurdles.clear();
		nextHurdleSpacing = MAX_HURDLE_SPACING;
		nextHurdleHeight = MIN_HURDLE_HEIGHT;
		nextHurdleIndex = 1;
		hurdles.add(new Hurdle(0, MIN_HURDLE_HEIGHT, FIRST_HURDLE_X));
		addAll(hurdles.getFirst().imagePanes());
		for(int i = 0; i < 7; i++)
			generateHurdle();
		removeAllPlayerImages();
		for(int n : gamePane().playersRemaining())
			addAllPlayerImages(n);
		for(int n : gamePane().playersRemaining())
			Jumper.get(n).start();
	}

	private void removeAllPlayerImages() {
		removeAll(Jumper.LIST);
		removeAll(JumpBarBackground.LIST);
		removeAll(JumpBar.LIST);
		removeAll(JumpBarTick.LIST);
		removeAll(keyIcons);
	}
	
	private void addAllPlayerImages(int number) {
		addAll(Jumper.get(number), JumpBarBackground.get(number), Jumper.get(number).bar(), JumpBarTick.get(number),
				keyIcons.get(number - 1));
	}
	
	@Override
	public void keyPressedIngame(KeyCode kc) {
		for(Jumper j : Jumper.LIST)
			j.keyPressed(kc);
		for(JumpBar jb : JumpBar.LIST)
			jb.keyPressed(kc);
	}

	@Override
	public void keyReleasedIngame(KeyCode kc) {
		for(Jumper j : Jumper.LIST)
			j.keyPressed(kc);
		for(JumpBar jb : JumpBar.LIST)
			jb.keyReleased(kc);
	}

	@Override
	public void updateIngame(long diff) {
		for(Jumper j : Jumper.LIST)
			if(gamePane().playersRemaining().contains(j.number()))
				j.update(diff);
		for(JumpBar jb : JumpBar.LIST)
			jb.update(diff);
		for(Hurdle h : hurdles)
			h.update(diff);
		if(hurdles.getFirst().getRightX() < 0) {
			generateHurdle();
			Hurdle first = hurdles.removeFirst();
			removeAll(first.imagePanes());
		}
	}

	private void generateHurdle() {
		double newX = hurdles.getLast().getBaseX() + nextHurdleSpacing;
		Hurdle h = new Hurdle(nextHurdleIndex, nextHurdleHeight, newX);
		if(nextHurdleSpacing - HURDLE_SPACING_DIFF >= MIN_HURDLE_SPACING)
			nextHurdleSpacing -= HURDLE_SPACING_DIFF;
		if(nextHurdleHeight + HURDLE_HEIGHT_DIFF <= MAX_HURDLE_HEIGHT)
			nextHurdleHeight += HURDLE_HEIGHT_DIFF;
		nextHurdleIndex++;
		hurdles.add(h);
		addAll(h.imagePanes());
	}

	public void kill(Jumper j) {
		trash(j);
	}

	public ImagePane ground() {
		return ground;
	}
	
	/** unmodifiable. */
	public List<Hurdle> hurdles() {
		return Collections.unmodifiableList(hurdles);
	}

	@Override
	public Hurdles gamePane() {
		return (Hurdles) super.gamePane();
	}
	
}
