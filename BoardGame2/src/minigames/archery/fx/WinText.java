package minigames.archery.fx;

import fxutils.Fonts;
import javafx.scene.control.Label;

public class WinText extends Label {
	
	public WinText() {
		setText("Someone won!");
		setFont(Fonts.UI_MED);
	}
	
	public void setWinner(int player) {
		setText(textFor(player));
	}
	
	private String textFor(int player) {
		return String.format("Player %d won!", player);
	}
	
}
