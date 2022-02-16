package minigames.running.imagelayer;

import javafx.scene.input.KeyCode;
import minigames.*;
import minigames.running.Running;

public class RunningImageLayer extends MinigameImageLayer {
	
	public RunningImageLayer() {
		super(MiniTag.RUNNING);
	}
	
	@Override
	public void startMinigame() {
		setupSkies();
	}

	private void setupSkies() {
		removeAll(Sky.LIST);
		int playerCount = gamePane().players().size();
		for(int i = 1; i <= playerCount; i++) {
			Sky.get(i).alignFor(playerCount);
			add(Sky.get(i));
		}
	}
	
	@Override
	public void keyPressedIngame(KeyCode kc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleasedIngame(KeyCode kc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateIngame(long diff) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Running gamePane() {
		return (Running) super.gamePane();
	}
	
}
