package base.input;

import java.util.EnumSet;

import javafx.scene.input.KeyCode;

public class GameInput {
	
	private GameInput() {
		
	}
	
	private static final EnumSet<KeyCode> KEYS_PRESSED = EnumSet.noneOf(KeyCode.class);
	
	public static boolean isPressed(KeyCode kc) {
		return keysPressed().contains(kc);
	}
	
	public static EnumSet<KeyCode> keysPressed() {
		return KEYS_PRESSED;
	}
	
	public static GameControls controls() {
		return GameControls.DEFAULT; //TODO let user edit controls?
	}
	
}
