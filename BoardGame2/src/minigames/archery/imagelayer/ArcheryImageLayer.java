package minigames.archery.imagelayer;

import java.util.Collection;

import base.*;
import base.panes.*;
import game.MainScene;
import javafx.scene.input.*;
import minigames.*;
import minigames.archery.*;
import utils.Intersections;

public class ArcheryImageLayer extends MinigameImageLayer {
	
	public static final double
		FENCE_TOP_Y = MainScene.DEFAULT_HEIGHT * .675,
		FENCE_HEIGHT = 60,
		FENCE_BOTTOM_Y = FENCE_TOP_Y + FENCE_HEIGHT;
	
	public ArcheryImageLayer() {
		super(MiniTag.ARCHERY);
	}
	
	/** Must only be called once. */
	public void init() {
		addAll(gamePane().archers());
		addAll(gamePane().rewardsDisplay().imagePanes());
	}

	public void putArchersInPosition() {
		int archer = 1;
		Collection<Archer> archers = gamePane().archers();
		for(Archer a : archers)
			a.setIdealCenter(MainScene.DEFAULT_WIDTH / (archers.size() + 1) * archer++, MainScene.DEFAULT_HEIGHT * .85);
	}
	
	@Override
	public void startMinigame() {
		removeAll(gamePane().archers());
		putArchersInPosition();
		addAll(gamePane().archers());
	}
	
	@Override
	public Archery gamePane() {
		return (Archery) super.gamePane();
	}

	public void addArrow(Arrow a) {
		add(a);
	}
	
	/** Returns one of the {@link Arrow Arrows} that is currently intersecting the given {@link ImagePane}. Returns
	 * {@code null} if no {@link Arrow} is currently intersecting the {@link ImagePane}. */
	public Arrow getIntersectingArrow(ImagePane ip) {
		for(ImagePane a : imagesUnmodifiable())
			if(a instanceof Arrow && Intersections.test(a, ip))
				return (Arrow) a;
		return null;
	}
	
	@Override
	public void keyPressedIngame(KeyCode kc) {
		for(Archer a : gamePane().archers())
			if(gamePane().isMobile(a))
				a.keyPressed(kc);
	}

	@Override
	public void keyReleasedIngame(KeyCode kc) {
		for(Archer a : gamePane().archers())
			if(gamePane().isMobile(a))
				a.keyReleased(kc);
	}

	@Override
	public void updateIngame(long diff) {
		for(ImagePane ip : imagesUnmodifiable())
			if(ip instanceof Updatable)
				((Updatable) ip).update(diff);
	}
	
}
