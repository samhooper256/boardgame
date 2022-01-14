package base.input;

import java.util.EnumSet;

import javafx.scene.input.KeyCode;

public class GameInput {
	
	private GameInput() {
		
	}
	
	private static final EnumSet<KeyCode> KEYS_PRESSED = EnumSet.noneOf(KeyCode.class);
	
	private static GameInputMode mode = GameInputMode.SINGLE;
	
	public static GameInputMode mode() {
		return mode;
	}
	
	/** Returns {@code true} iff {@code mode() instanceof SingleControllerMode}. */
	public static boolean isSingle() {
		return mode() instanceof SingleControllerMode;
	}
	
	public static boolean isPressed(KeyCode kc) {
		return keysPressed().contains(kc);
	}
	
	public static EnumSet<KeyCode> keysPressed() {
		return KEYS_PRESSED;
	}
	
}
