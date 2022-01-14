package minigames;

import java.util.*;

import base.*;
import game.MainScene;
import javafx.scene.input.*;

public abstract class Minigame extends AbstractScaledPane implements Updatable {
	
	private final List<ImagePane> trash;
	private final List<Runnable> endOfUpdateActions;
	
	public Minigame() {
		trash = new ArrayList<>();
		endOfUpdateActions = new ArrayList<>();
	}
	
	/** Assumes this {@link Minigame} has already been set as the
	 * {@link MainScene#setRootBase(javafx.scene.layout.Pane) base of the MainScene root}.*/
	public abstract void start();
	
	/** no-op by default. */
	public abstract void keyPressed(KeyCode kc);
	
	/** no-op by default. */
	public abstract void keyReleased(KeyCode kc);
	
	/** no-op by default. */
	public abstract void mouseClicked(MouseEvent me);
	
	/** {@link ImagePane ImagePanes} put in the trash will be {@link #remove(ImagePane) removed} at the end of this
	 * {@link #update(long) update} pulse. */
	public void trash(ImagePane ip) {
		trash.add(ip);
	}

	public void addEndOfUpdateAction(Runnable action) {
		endOfUpdateActions.add(action);
	}
	
	public abstract void updateGame(long diff);
	
	@Override
	public final void update(long diff) {
		updateGame(diff);
		for(ImagePane ip : trash)
			remove(ip);
		trash.clear();
		for(Runnable eoua : endOfUpdateActions)
			eoua.run();
		endOfUpdateActions.clear();
	}
	
}
