package minigames.hurdles.imagelayer;

import java.util.*;

import base.panes.ImagePane;
import fxutils.Images;
import javafx.scene.input.KeyCode;
import minigames.*;
import minigames.hurdles.Hurdles;

public class HurdlesImageLayer extends MinigameImageLayer {

	public static final double
		MAX_HURDLE_SPACING = 700,
		MIN_HURDLE_SPACING = 400,
		HURDLE_SPACING_DIFF = 10,
		MIN_HURDLE_HEIGHT = 100,
		MAX_HURDLE_HEIGHT = 400,
		HURDLE_HEIGHT_DIFF = 10,
		FIRST_HURDLE_X = 1400;
	
	private final ImagePane ground;
	
	private double nextHurdleSpacing, nextHurdleHeight;
	private LinkedList<Hurdle> hurdles;
	
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
	}
	
	@Override
	public void startMinigame() {
		for(Hurdle h : hurdles)
			removeAll(h.imagePanes());
		hurdles.clear();
		nextHurdleSpacing = MAX_HURDLE_SPACING;
		nextHurdleHeight = MIN_HURDLE_HEIGHT;
		hurdles.add(new Hurdle(MIN_HURDLE_HEIGHT, FIRST_HURDLE_X));
		addAll(hurdles.getFirst().imagePanes());
		for(int i = 0; i < 7; i++)
			generateHurdle();
		removeAll(Jumper.LIST);
		addAll(Jumper.LIST);
		for(Jumper j : Jumper.LIST)
			j.start();
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
		Hurdle last = hurdles.getLast();
		double newX = last.getX() + nextHurdleSpacing;
		if(nextHurdleSpacing - HURDLE_SPACING_DIFF >= MIN_HURDLE_SPACING)
			nextHurdleSpacing -= HURDLE_SPACING_DIFF;
		if(nextHurdleHeight + HURDLE_HEIGHT_DIFF <= MAX_HURDLE_HEIGHT)
			nextHurdleHeight += HURDLE_HEIGHT_DIFF;
		Hurdle h = new Hurdle(nextHurdleHeight, newX);
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
