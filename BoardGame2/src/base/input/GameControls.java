package base.input;

import javafx.scene.input.KeyCode;

public interface GameControls extends DirectionalControls, SingleKeyControls {

	GameControls DEFAULT = of(
			KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D,
			KeyCode.DIGIT1, KeyCode.X, KeyCode.M, KeyCode.EQUALS,
			KeyCode.SPACE, KeyCode.ESCAPE
	);
	
	static GameControls of(
			KeyCode up, KeyCode left, KeyCode down, KeyCode right,
			KeyCode single1, KeyCode single2, KeyCode single3, KeyCode single4,
			KeyCode next, KeyCode pause) {
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
			
			
			@Override
			public KeyCode single1() {
				return single1;
			}

			@Override
			public KeyCode single2() {
				return single2;
			}

			@Override
			public KeyCode single3() {
				return single3;
			}

			@Override
			public KeyCode single4() {
				return single4;
			}

			@Override
			public KeyCode pause() {
				return pause;
			}
			
			@Override
			public KeyCode next() {
				return next;
			}
			
		};
	}
	
	KeyCode pause();
	
	KeyCode next();
	
}
