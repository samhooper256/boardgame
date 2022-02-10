package base.input;

import javafx.scene.input.KeyCode;

//1, X, M, =
public interface SingleKeyControls {
	
	default KeyCode single(int player) {
		switch(player) {
			case 1: return single1();
			case 2: return single2();
			case 3: return single3();
			case 4: return single4();
			default: throw new IllegalArgumentException(String.format("No single key for player %d", player));
		}
	}
	
	KeyCode single1();
	
	KeyCode single2();
	
	KeyCode single3();
	
	KeyCode single4();
	
}
