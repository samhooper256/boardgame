package game.win;

import java.util.List;

import base.panes.*;
import game.MainScene;
import game.win.fx.WinFXLayer;
import game.win.imagelayer.WinImageLayer;
import javafx.scene.input.KeyCode;
import players.Player;

public class WinPane extends GamePane {

	private static final WinPane INSTANCE = new WinPane();
	
	public static WinPane get() {
		return INSTANCE;
	}
	
	private WinPane() {
		super(new WinImageLayer(), new WinFXLayer());
	}

	/** The {@link List} of players in descending order of their rank. (Index 0 stores the player who came in 1st place,
	 * index 1 stores the player who came in 2nd place, etc.) */
	public void setupFor(List<Player> ranking) {
		imageLayer().setupFor(ranking);
	}
	
	@Override
	public void keyReleased(KeyCode kc) {
		if(kc == KeyCode.SPACE)
			MainScene.get().fadeToMainFromWin();
	}
	
	@Override
	public WinImageLayer imageLayer() {
		return (WinImageLayer) super.imageLayer();
	}

	@Override
	public WinFXLayer fxLayer() {
		return (WinFXLayer) super.fxLayer();
	}
	
}
