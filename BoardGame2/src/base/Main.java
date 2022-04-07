package base;

import java.io.*;
import java.util.*;

import game.MainScene;
import game.helper.HelperInfo;
import javafx.application.*;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import players.Player;

//TODO:
// - Hurdles Instructions: add better background.
// - Running Instructions: add final obstacle images, add better background.
// - Add hitboxes for Wings sprite images.
// - somewhere tell the user that gold medals are worth 3, silver 2, bronze 1.
public class Main extends Application {

	public static final String TITLE = "Olympics";
	public static final String RESOURCES_PREFIX = "/resources/";
	
	private static Stage stage;
	
	public static final List<Point2D> POINTS = Collections.unmodifiableList(Arrays.asList(
			new Point2D(55.38,31.11), //top left corner
			new Point2D(210.76, 31.11),
			new Point2D(366.14, 31.11),
			new Point2D(521.52, 31.11),
			new Point2D(676.90, 31.11),
			new Point2D(832.28, 31.11),
			new Point2D(987.66, 31.11),
			new Point2D(1143.04, 31.11),
			new Point2D(1298.42, 31.11),
			new Point2D(1453.80, 31.11),
			new Point2D(1609.18, 31.11),
			new Point2D(1764.56, 31.11), //top right corner
			new Point2D(1764.56, 162.22),
			new Point2D(1764.56, 293.33),
			new Point2D(1764.56, 424.44),
			new Point2D(1764.56, 555.55),
			new Point2D(1764.56, 686.66),
			new Point2D(1764.56, 817.77),
			new Point2D(1764.56, 948.88), //bottom right corner
			new Point2D(1609.18, 948.88),
			new Point2D(1453.80, 948.88),
			new Point2D(1298.42, 948.88),
			new Point2D(1143.04, 948.88),
			new Point2D(987.66, 948.88),
			new Point2D(832.28, 948.88),
			new Point2D(676.9, 948.88),
			new Point2D(521.52, 948.88),
			new Point2D(366.14, 948.88),
			new Point2D(210.76, 948.88),
			new Point2D(55.38, 948.88), //bottom left corner
			new Point2D(55.38, 817.77),
			new Point2D(55.38, 686.66),
			new Point2D(55.38, 555.55),
			new Point2D(55.38, 424.44),
			new Point2D(55.38, 293.33),
			new Point2D(55.38, 162.22)
	));
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Main.stage = stage;
		Player.maxCount(); //cause Player to be initialized.
		HelperInfo.acquireFromMemory();
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.setOnCloseRequest(eh -> Main.closeAction());
		stage.setScene(MainScene.get());
		stage.setFullScreen(true);
		stage.setTitle(TITLE);
		stage.show();
	}
	
	public static Stage stage() {
		return stage;
	}
	
	public static void quitGame() {
		closeAction();
		stage().close();
	}
	
	public static void closeAction() {
		Memory.save();
	}
	
	public void setFullScreen(boolean fullScreen) {
		stage().setFullScreen(fullScreen);
	}
	
	public boolean isFullScreen() {
		return stage().isFullScreen();
	}
	
	/**
	 * Produces an {@link Optional} of the {@link InputStream} for a resource in the "resources" folder.
	 * If the resource could not be located, the returned {@code Optional} will be empty. Otherwise, it
	 * will contain the {@code InputStream}.
	 * @param filename the name of the resource file, including its file extension. Must be in the "resources" folder.
	 * @return an {@link Optional} possibly containing the {@link InputStream}.
	 */
	public static Optional<InputStream> getOptionalResourceStream(String filename) {
		return Optional.ofNullable(Main.class.getResourceAsStream(RESOURCES_PREFIX + filename));
	}
	
	/**
	 * Produces the {@link InputStream} for a resource in the "resources" folder.
	 * @param filename the name of the file, including its file extension. Must be in the "resources" folder.
	 * @return the {@link InputStream} for the resource indicated by the given filename.
	 * @throws IllegalArgumentException if the file does not exist.
	 */
	public static InputStream getResourceStream(String filename) {
		Optional<InputStream> stream = getOptionalResourceStream(filename);
		if(!stream.isPresent())
			throw new IllegalArgumentException("The resource at " + RESOURCES_PREFIX + filename + " does not exist");
		return stream.get();
	}
	
}
