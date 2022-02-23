package game.board;

import java.util.*;
import java.util.stream.Collectors;

import base.AcceptsInput;
import javafx.scene.input.KeyCode;

public final class BoardCheats implements AcceptsInput {
	
	public static final boolean ENABLED = true;
	
	private static final BoardCheats INSTANCE = new BoardCheats();
	
	public static BoardCheats get() {
		return INSTANCE;
	}
	
	private static int value(KeyCode digit) {
		if(!digit.isDigitKey())
			throw new IllegalArgumentException(String.format("Not a digit: %s", digit));
		switch(digit) {
			case DIGIT0: return 0;
			case NUMPAD0: return 0;
			case DIGIT1: return 1;
			case NUMPAD1: return 1;
			case DIGIT2: return 2;
			case NUMPAD2: return 2;
			case DIGIT3: return 3;
			case NUMPAD3: return 3;
			case DIGIT4: return 4;
			case NUMPAD4: return 4;
			case DIGIT5: return 5;
			case NUMPAD5: return 5;
			case DIGIT6: return 6;
			case NUMPAD6: return 6;
			case DIGIT7: return 7;
			case NUMPAD7: return 7;
			case DIGIT8: return 8;
			case NUMPAD8: return 8;
			case DIGIT9: return 9;
			case NUMPAD9: return 9;
			default: throw new IllegalStateException("Should not happen");
		}
	}

	private final List<KeyCode> buffer;
	
	private BoardCheats() {
		buffer = new ArrayList<>();
	}
	
	@Override
	public void keyPressed(KeyCode kc) {
		if(!ENABLED)
			return;
	}

	@Override
	public void keyReleased(KeyCode kc) {
		if(!ENABLED)
			return;
		System.out.printf("cheats released: %s%n", kc);
		if(kc == KeyCode.ENTER) {
			if(buffer.stream().allMatch(KeyCode::isDigitKey)) {
				int dist = Integer.parseInt(buffer.stream().map(c -> String.valueOf(value(c)))
						.collect(Collectors.joining()));
				Board.get().executeTurn(dist);
			}
			buffer.clear();
		}
		else {
			buffer.add(kc);
		}
	}
	
}
