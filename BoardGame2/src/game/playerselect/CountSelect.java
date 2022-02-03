package game.playerselect;

import base.panes.ImagePane;
import fxutils.Images;
import game.MainScene;

import static game.MainScene.*;

public class CountSelect extends ImagePane {

	public static final double SPACING = 32, Y = DEFAULT_HEIGHT * .55;
	
	private static final double DISTANCE = Images.SELECT_2.getWidth() + SPACING;
	
	public static final CountSelect
			SELECT2 = new CountSelect(2, CENTER_X - DISTANCE, Y),
			SELECT3 = new CountSelect(3, CENTER_X, Y),
			SELECT4 = new CountSelect(4, CENTER_X + DISTANCE, Y);
	
	public static CountSelect forCount(int playerCount) {
		switch(playerCount) {
			case 2: return SELECT2;
			case 3: return SELECT3;
			case 4: return SELECT4;
		}
		throw new IllegalArgumentException(String.format("playerCount = %d", playerCount));
	}
	
	private final int playerCount;
	
	private CountSelect(int playerCount, double centerX, double centerY) {
		super(Images.playerCountIcon(playerCount));
		setIdealCenter(centerX, centerY);
		this.playerCount = playerCount;
		setOnMouseClicked(eh -> onClick());
		setOnMouseEntered(eh -> mouseEntered());
	}
	
	public int playerCount() {
		return playerCount;
	}
	
	private void onClick() {
		MainScene.get().startGame(playerCount);
	}

	private void mouseEntered() {
		PlayerSelect.get().imageLayer().hoverEntered(playerCount);
	}
	
}
