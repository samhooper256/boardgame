package base.input;

public class GameInput {
	
	private GameInput() {
		
	}
	
	private static GameInputMode mode = GameInputMode.SINGLE;
	
	public static GameInputMode mode() {
		return mode;
	}
	
	/** Returns {@code true} iff {@code mode() instanceof SingleControllerMode}. */
	public static boolean isSingle() {
		return mode() instanceof SingleControllerMode;
	}
	
}
