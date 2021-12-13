package base;

import java.util.List;

import javafx.scene.layout.Pane;

/** Implementing classes should also be subclasses of {@link Pane}.*/
public interface ScaledPane {

	double DEFAULT_WIDTH = 1920, DEFAULT_HEIGHT = 1080;
	
	/** Returns {@code true} if the {@link ImagePane} was successfully added, {@code false} otherwise. */
	boolean add(ImagePane ip);
	
	/** Returns {@code true} if the {@link ImagePane} was present and has been removed, {@code false} otherwise. */
	boolean remove(ImagePane ip);
	
	List<ImagePane> imagesUnmodifiable();
	
	void resize(double width, double height);
	
	void updateImageSize(ImagePane ip);
	
	void updateImageLayoutCoords(ImagePane ip);
	
}
