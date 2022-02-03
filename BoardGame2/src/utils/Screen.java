package utils;

import base.MainScene;
import base.panes.ImagePane;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.transform.Scale;

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
	
	/** Returns a {@link Scale} that will scale to the screen. */
	public static Scale getScale() {
		Scale s = new Scale(1, 1, 0, 0);
		s.xProperty().bind(Screen.wscaleBinding());
		s.yProperty().bind(Screen.hscaleBinding());
		return s;
	}
	
}
