package minigames.archery;

import java.util.Collection;

import base.*;
import fxutils.Images;
import game.MainScene;
import javafx.scene.input.*;
import javafx.util.Duration;
import utils.Intersections;

public class ArcheryScaledPane extends AbstractScaledPane implements AcceptsInput {

	private static final Duration INSTRUCTIONS_FADE_OUT_DURATION = Duration.millis(300);
	
	private final FadeableImagePane instructions, pressSpace;
	private final Fence fence;
	
	public ArcheryScaledPane() {
		super();
		instructions = new FadeableImagePane(Images.MINIGAME_INSTRUCTIONS, INSTRUCTIONS_FADE_OUT_DURATION);
		instructions.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 2);
		pressSpace = new FadeableImagePane(Images.PRESS_SPACE, INSTRUCTIONS_FADE_OUT_DURATION);
		pressSpace.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT * .8);
		fence = new Fence();
		fence.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT * .75);
	}
	
	void init(Collection<Archer> archers) {
		int archer = 1;
		for(Archer a : archers)
			a.setIdealCenter(DEFAULT_WIDTH / (archers.size() + 1) * archer++, DEFAULT_HEIGHT * .85);
	}
	
	void start() {
		instructions.makeFullyVisible();
		add(new ImagePane(Images.ARCHERY_BACKGROUND));
		addAll(gamePane().archers());
		addAll(fence, instructions, pressSpace);
	}
	
	@Override
	public ArcheryMinigame gamePane() {
		return (ArcheryMinigame) super.gamePane();
	}

	public void addArrow(Arrow a) {
		add(a);
	}
	
	/** Returns {@code true} iff any {@link Arrow Arrows}. intersect the given {@link ImagePane}. */
	public boolean anyArrowsIntersect(ImagePane ip) {
		for(ImagePane a : images)
			if(a instanceof Arrow && Intersections.test(a, ip))
				return true;
		return false;
	}
	
	/** {@code true} if the instructions are showing, even if they are in the process of fading out. */
	private boolean instructionsShowing() {
		return instructions.getOpacity() > 0;
	}
	
	
	@Override
	public void keyPressed(KeyCode kc) {
		if(instructionsShowing()) {
			if(kc == KeyCode.SPACE && !instructions.isFadingOut()) {
				instructions.fadeOut(this);
				pressSpace.fadeOut(this);
			}
		}
		else {
			if(kc == KeyCode.E)
				MainScene.get().fadeBackFromMinigame(null);
			for(Archer a : gamePane().archers())
				a.keyPressed(kc);
		}
	}

	@Override
	public void keyReleased(KeyCode kc) {
		if(!instructionsShowing())
			for(Archer a : gamePane().archers())
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
	
	public Fence fence() {
		return fence;
	}
	
}
