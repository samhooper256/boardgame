package fxutils;

import javafx.scene.text.*;

public final class Fonts {

	public static final String UI_FONT_FAMILY_NAME = "Courier New"; //TODO choose better font
	
	public static final Font
			UI_SMALL = Font.font(UI_FONT_FAMILY_NAME, FontWeight.BOLD, 24),
			UI_MED = Font.font(UI_FONT_FAMILY_NAME, FontWeight.BOLD, 36),
			UI_LARGE = Font.font(UI_FONT_FAMILY_NAME, FontWeight.BOLD, 48),
			UI_XLARGE = Font.font(UI_FONT_FAMILY_NAME, FontWeight.BOLD, 72);
	
	private Fonts() {
		
	}
	
}
