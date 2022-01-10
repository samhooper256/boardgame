package minigames.archery;

import java.util.*;

import base.*;
import fxutils.Images;
import game.*;
import javafx.scene.input.*;
import javafx.util.Duration;
import minigames.Minigame;
import players.Player;

public class ArcheryMinigame extends Minigame {

	private static final Duration INSTRUCTIONS_FADE_OUT_DURATION = Duration.millis(300);
	private static final ArcheryMinigame INSTANCE = new ArcheryMinigame();
	
	public static ArcheryMinigame get() {
		return INSTANCE;
	}
	
	private final FadeableImagePane instructions, pressSpace;
	private final Fence fence;
	
	private final Map<Player, Archer> archerMap;
	
	private ArcheryMinigame() {
		super();
		instructions = new FadeableImagePane(Images.MINIGAME_INSTRUCTIONS, INSTRUCTIONS_FADE_OUT_DURATION);
		instructions.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 2);
		pressSpace = new FadeableImagePane(Images.PRESS_SPACE, INSTRUCTIONS_FADE_OUT_DURATION);
		pressSpace.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT * .8);
		fence = new Fence();
		fence.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT * .75);
		archerMap = new HashMap<>();
		Board.get().players().forEachOrdered(p -> {
			archerMap.put(p, new Archer(p.image()));
		});
		Collection<Archer> archers = archerMap.values();
		int archer = 1;
		for(Archer a : archers)
			a.setIdealCenter(DEFAULT_WIDTH / (archers.size() + 1) * archer++, DEFAULT_HEIGHT * .85);
	}

	@Override
	public void start() {
		instructions.makeFullyVisible();
		add(new ImagePane(Images.ARCHERY_BACKGROUND));
		addAll(archerMap.values());
		addAll(fence, instructions, pressSpace);
	}

	
	@Override
	public void updateGame(long diff) {
		if(!instructionsShowing()) {
			for(ImagePane ip : imagesUnmodifiable())
				if(ip instanceof Updatable)
					((Updatable) ip).update(diff);
		}
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
			for(Archer a : archerMap.values())
				a.keyPressed(kc);
		}
	}
	
	@Override
	public void keyReleased(KeyCode kc) {
		if(!instructionsShowing())
			for(Archer a : archerMap.values())
				a.keyReleased(kc);
	}

	
	@Override
	public void mouseClicked(MouseEvent me) {
		for(Archer a : archerMap.values())
			a.mouseClicked(me);
	}

	/** {@code true} if the instructions are showing, even if they are in the process of fading out. */
	private boolean instructionsShowing() {
		return instructions.getOpacity() > 0;
	}
	
	public void addArrow(Arrow a) {
		add(a);
	}
	
	public Fence fence() {
		return fence;
	}
	
}
