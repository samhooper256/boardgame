package base;

import java.io.*;
import java.util.*;

import fxutils.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.stage.Stage;

public class Main extends Application {

	private static Stage stage;
	static final String RESOURCES_PREFIX = "/resources/";
	
	private static final Image TILE_IMAGE = Images.get("tile.png");
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Main.stage = stage;
		Board g = new Board();
		Scene scene = new Scene(g, 600, 400);
		stage.setScene(scene);
		
		ImagePane im = new ImagePane(Main.TILE_IMAGE, Main.TILE_IMAGE.getWidth(), Main.TILE_IMAGE.getHeight());
		im.setIdealX(200);
		im.setIdealY(200);
		
		g.add(im);
		
		stage.setFullScreen(true);
		stage.show();
	}
	
	public Stage stage() {
		return stage;
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
