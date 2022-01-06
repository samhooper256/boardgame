package minigames.archery;

import javafx.scene.input.KeyCode;

public interface ArcheryControls {

	ArcheryControls WASD = of(KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D);
	
	public static ArcheryControls of(KeyCode up, KeyCode left, KeyCode down, KeyCode right) {
		return new ArcheryControls() {
			
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
	
	KeyCode up();
	
	KeyCode left();
	
	KeyCode down();
	
	KeyCode right();
	
}
