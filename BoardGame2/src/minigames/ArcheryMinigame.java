package minigames;

import base.*;
import fxutils.Images;
import game.MainScene;
import javafx.scene.input.*;
import javafx.util.Duration;

public class ArcheryMinigame extends Minigame {

	private static final Duration INSTRUCTIONS_FADE_OUT_DURATION = Duration.millis(300);
	
	private final FadeableImagePane instructions;
	
	public ArcheryMinigame() {
		super();
		instructions = new FadeableImagePane(Images.MINIGAME_INSTRUCTIONS, INSTRUCTIONS_FADE_OUT_DURATION);
		instructions.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 2);
	}

	@Override
	public void start() {
		instructions.makeFullyVisible();
		add(instructions);
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		if(instructionsShowing()) {
			if(!instructions.isFadingOut())
				instructions.fadeOut(this);
		}
		else {
			if(ke.getCode() == KeyCode.E)
				MainScene.get().fadeBackFromMinigame(null);
		}
	}
	
	/** {@code true} if the instructions are showing, even if they are in the process of fading out. */
	private boolean instructionsShowing() {
		return instructions.getOpacity() > 0;
	}
	
}
