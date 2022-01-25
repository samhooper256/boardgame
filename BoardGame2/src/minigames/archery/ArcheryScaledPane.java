package minigames.archery;

import java.util.Collection;

import base.*;
import base.panes.*;
import fxutils.Images;
import game.MainScene;
import javafx.scene.input.*;
import javafx.util.Duration;
import utils.Intersections;

public class ArcheryScaledPane extends AbstractScaledPane implements AcceptsInput {

	private static final Duration INSTRUCTIONS_FADE_OUT_DURATION = Duration.millis(300);
	
	private final FadeableImagePane instructions, pressSpace;
	private final Fence fence;
	private final ImagePane winBackground, winner;
	
	public ArcheryScaledPane() {
		super();
		instructions = new FadeableImagePane(Images.MINIGAME_INSTRUCTIONS, INSTRUCTIONS_FADE_OUT_DURATION);
		instructions.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 2);
		pressSpace = new FadeableImagePane(Images.PRESS_SPACE, INSTRUCTIONS_FADE_OUT_DURATION);
		pressSpace.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT * .8);
		fence = new Fence();
		fence.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT * .75);
		winBackground = new ImagePane(Images.MINIGAME_WIN_BACKGROUND);
		winBackground.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 2);
		winner = new ImagePane(Images.TREE, 100, 100);
		winner.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT * .45);
	}
	
	void init() {
		add(new ImagePane(Images.ARCHERY_BACKGROUND));
		addAll(fence);
		addAll(gamePane().archers());
	}

	public void putArchersInPosition() {
		int archer = 1;
		Collection<Archer> archers = gamePane().archers();
		for(Archer a : archers)
			a.setIdealCenter(DEFAULT_WIDTH / (archers.size() + 1) * archer++, DEFAULT_HEIGHT * .85);
	}
	
	void start() {
		addAll(instructions, pressSpace);
		instructions.fader().appear();
		pressSpace.fader().appear();
		removeAll(winner, winBackground);
		removeAll(gamePane().archers());
		putArchersInPosition();
		addAll(gamePane().archers());
	}
	
	@Override
	public ArcheryMinigame gamePane() {
		return (ArcheryMinigame) super.gamePane();
	}

	public void addArrow(Arrow a) {
		add(a);
	}
	
	/** Returns one of the {@link Arrow Arrows} that is currently intersecting the given {@link ImagePane}. Returns
	 * {@code null} if no {@link Arrow} is currently intersecting the {@link ImagePane}. */
	public Arrow getIntersectingArrow(ImagePane ip) {
		for(ImagePane a : images)
			if(a instanceof Arrow && Intersections.test(a, ip))
				return (Arrow) a;
		return null;
	}
	
	/** {@code true} if the instructions are showing, even if they are in the process of fading out. */
	public boolean instructionsShowing() {
		return instructions.getOpacity() > 0;
	}
	
	
	@Override
	public void keyPressed(KeyCode kc) {
		if(instructionsShowing()) {
			if(kc == KeyCode.SPACE && !instructions.fader().isFadingOut()) {
				instructions.fader().fadeOutAndHide();
				pressSpace.fader().fadeOutAndHide();
			}
		}
		else {
			if(gamePane().hasWinner()) {
				if(kc == KeyCode.SPACE)
					MainScene.get().fadeBackFromMinigame(gamePane().getResult());
			}
			else {
				for(Archer a : gamePane().archers())
					if(gamePane().isMobile(a))
						a.keyPressed(kc);
			}
		}
	}

	@Override
	public void keyReleased(KeyCode kc) {
		if(!instructionsShowing())
			for(Archer a : gamePane().archers())
				if(gamePane().isMobile(a))
					a.keyReleased(kc);
	}

	@Override
	public void updatePane(long diff) {
		if(!instructionsShowing()) {
			for(ImagePane ip : imagesUnmodifiable())
				if(ip instanceof Updatable)
					((Updatable) ip).update(diff);
		}
	}
	
	public void win(int player) {
		winner.setImage(Images.player(player));
		addAll(winBackground, winner);
	}
	
	public Fence fence() {
		return fence;
	}
	
}
