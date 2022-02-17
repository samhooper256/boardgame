package minigames.running.imagelayer;

import java.util.List;

import base.panes.ImagePane;
import javafx.scene.input.KeyCode;
import minigames.*;
import minigames.running.Running;

public class RunningImageLayer extends MinigameImageLayer {
	
	public RunningImageLayer() {
		super(MiniTag.RUNNING);
	}
	
	@Override
	public void startMinigame() {
		setupFromList(Sky.LIST);
		setupFromList(Ground.LIST);
		setupFromList(Runner.LIST);
	}

	private <T extends ImagePane & Alignable> void setupFromList(List<T> list) {
		removeAll(list);
		for(int i = 0, playerCount = gamePane().players().size(); i < playerCount; i++) {
			list.get(i).alignFor(playerCount);
			add(list.get(i));
		}
	}
	
	@Override
	public void keyPressedIngame(KeyCode kc) {
		for(Runner r : Runner.LIST)
			r.keyPressed(kc);
	}

	@Override
	public void keyReleasedIngame(KeyCode kc) {
		for(Runner r : Runner.LIST)
			r.keyReleased(kc);
	}

	@Override
	public void updateIngame(long diff) {
		for(int r = 1; r <= gamePane().playersRemaining().size(); r++)
			Runner.get(r).update(diff);
	}
	
	@Override
	public Running gamePane() {
		return (Running) super.gamePane();
	}
	
}
