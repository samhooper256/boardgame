package utils;

import base.panes.ImagePane;
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
	
	public static boolean isEntirelyOffscreen(ImagePane ip) {
    	return 	ip.getIdealX() < -ip.getIdealWidth() || ip.getIdealX() > MainScene.DEFAULT_WIDTH ||
    			ip.getIdealY() < -ip.getIdealHeight() || ip.getIdealY() > MainScene.DEFAULT_HEIGHT;
    }
	
	public static void center(ImagePane ip) {
		ip.setIdealCenter(MainScene.DEFAULT_WIDTH / 2, MainScene.DEFAULT_HEIGHT / 2);
	}
	
}
