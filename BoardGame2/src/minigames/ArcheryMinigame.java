package minigames;

import base.*;
import fxutils.Images;

public class ArcheryMinigame extends Minigame {

	private final FadeableImagePane instructions;
	
	public ArcheryMinigame() {
		super();
		instructions = new FadeableImagePane(Images.MINIGAME_INSTRUCTIONS);
		instructions.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 2);
		
	}

	@Override
	public void start() {
		instructions.makeFullyVisible();
		add(instructions);
	}
	
}
