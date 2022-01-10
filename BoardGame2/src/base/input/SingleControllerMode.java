package base.input;

public class SingleControllerMode implements GameInputMode {

	private static final SingleControllerMode INSTANCE = new SingleControllerMode();
	
	public static final SingleControllerMode get() {
		return INSTANCE;
	}
	
	private final GameControls controls;
	
	private SingleControllerMode() {
		controls = GameControls.DEFAULT;
	}	
	
	public GameControls controls() {
		return controls;
	}
	
}
