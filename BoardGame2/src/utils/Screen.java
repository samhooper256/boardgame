package utils;

import game.MainScene;
import javafx.beans.binding.DoubleBinding;

public final class Screen {

	private Screen() {
		
	}

	public static DoubleBinding wscaleBinding() {
		MainScene ms = MainScene.get();
		return ms.wscaleBinding();
	}
	
	public static DoubleBinding hscaleBinding() {
		return MainScene.get().hscaleBinding();
	}
	
	public static double wscale() {
		return wscaleBinding().get();
	}
	
	public static double hscale() {
		return hscaleBinding().get();
	}
	
}
