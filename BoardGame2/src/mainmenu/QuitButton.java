package mainmenu;

import base.Main;
import base.panes.ImagePane;
import fxutils.Images;

public class QuitButton extends ImagePane {
	
	public QuitButton() {
		super(Images.QUIT_BUTTON);
		setOnMouseClicked(eh -> Main.stage().close());
	}
	
}
