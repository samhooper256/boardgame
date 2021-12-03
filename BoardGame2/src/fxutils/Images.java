package fxutils;

import base.Main;
import javafx.scene.image.*;

/**
 * Utility class for creating {@link javafx.scene.image.Image} objects from resource files and working with
 * {@link ImageView ImageViews}.
 * @author Sam Hooper
 *
 */
public final class Images {
	
	public static final Image
			EVENT_TILE = get("MedicalTile.png"),
			MINIGAME_TILE = get("minigame_tile.png"),
			SAFE_TILE = get("safe_tile.png"),
			START_TILE = get("start_tile.png"),
			START_TILE_HOVERED = get("start_tile_hovered.png"),
			DIE0 = get("die0.png"),
			DIE1 = get("die1.png"),
			DIE2 = get("die2.png"),
			DIE3 = get("die3.png"),
			DIE4 = get("die4.png"),
			DIE5 = get("die5.png"),
			DIE6 = get("die6.png"),
			ARCHERY = get("Archerytile.png"),
			MAIN_MENU = get("main_menu.png"),
			PLAY_BUTTON = get("play_button.png");

	private Images() {}

	public static Image die(int face) {
		switch(face) {
			case 1: return DIE1;
			case 2: return DIE2;
			case 3: return DIE3;
			case 4: return DIE4;
			case 5: return DIE5;
			case 6: return DIE6;
		}
		throw new IllegalArgumentException(String.format("Invalid face: %d", face));
	}
	/**
	 * Returns the image given by {@code filename} by invoking {@link Image#Image(java.io.InputStream)} with
	 * the appropriate {@link InputStream}. The file indicated by {@code filename} must be in the "resources"
	 * folder.
	 * @return the image given by {@code filename}
	 */
	public static Image get(String filename) {
		return new Image(Main.getResourceStream(filename));
	}
	
	/**
	 * Returns the image given by {@code filename} with the given properties. The file indicated by
	 * {@code filename} must be in the "resources" folder.
	 * See {@link Image#Image(String, double, double, boolean, boolean) for details on the arguments.
	 * @return the {@link Image} described by the given filename with the given properties.
	 */
	public static Image get(	String filename,
								double requestedWidth,
								double requestedHeight,
								boolean preserveRatio,
								boolean smooth) {
		return new Image(Main.getResourceStream(filename), requestedWidth, requestedHeight, preserveRatio, smooth);
	}
	
	/**
	 * Equivalent to {@code setFitSize(view, fitSize, fitSize)}.
	 * */
	public static void setFitSize(ImageView view, double fitSize) {
		setFitSize(view, fitSize, fitSize);
	}
	
	public static void setFitSize(ImageView view, double fitWidth, double fitHeight) {
		view.setFitWidth(fitWidth);
		view.setFitHeight(fitHeight);
	}
	
}