package base;

import java.util.*;

import javafx.scene.layout.Pane;

/** Implementing classes should also be subclasses of {@link Pane}.*/
public interface ScaledPane {

	double DEFAULT_WIDTH = 1920, DEFAULT_HEIGHT = 1080, CENTER_X = DEFAULT_WIDTH / 2, CENTER_Y = DEFAULT_HEIGHT / 2;
	
	static void center(ImagePane ip) {
		ip.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 2);
	}
	
	/** Returns {@code true} if the {@link ImagePane} was successfully added, {@code false} otherwise. */
	boolean add(ImagePane ip);
	
	default void addAll(ImagePane... ips) {
		for(ImagePane ip : ips)
			add(ip);
	}
	
	default void addAll(Collection<? extends ImagePane> ips) {
		for(ImagePane ip : ips)
			add(ip);
	}
	
	/** Returns {@code true} if the {@link ImagePane} was present and has been removed, {@code false} otherwise. */
	boolean remove(ImagePane ip);
	
	List<ImagePane> imagesUnmodifiable();
	
	void resize(double width, double height);
	
	void updateImageSize(ImagePane ip);
	
	void updateImageLayoutCoords(ImagePane ip);
	
	/** @throws IllegalStateException if the given {@link ImagePane} is not in this {@link ScaledPane}. */
	default void bringToFront(ImagePane ip) {
		if(!remove(ip))
			throw new IllegalStateException(String.format("ImagePane is not in this ScaledPane: %s", ip));
		add(ip);
	}
}
