package minigames;

import base.ImagePane;
import fxutils.Images;

public class ArcheryMinigame extends Minigame {

	public ArcheryMinigame() {
		super();
		ImagePane ip = new ImagePane(Images.MINIGAME_INSTRUCTIONS);
		add(ip);
	}
	
}
