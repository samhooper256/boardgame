package base.input;

import javafx.scene.input.KeyCode;

public interface GameControls extends DirectionalControls {

	GameControls DEFAULT = of(KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D);
	
	static GameControls of(KeyCode up, KeyCode left, KeyCode down, KeyCode right) {
		return new GameControls() {
			
			@Override
			public KeyCode up() {
				return up;
			}
			
			@Override
			public KeyCode left() {
				return left;
			}
			
			@Override
			public KeyCode down() {
				return down;
			}
			
			@Override
			public KeyCode right() {
				return right;
			}
			
		};
	}
	
}
