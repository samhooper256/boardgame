package base.panes;

import java.util.*;

import base.Updatable;
import javafx.scene.layout.Pane;

/**<p>{@link ImageLayer ScaledImageLayers} allow for {@link ImagePane ImagePanes} to be properly displayed on the
 * screen. See the documentation of {@link ImagePane} for more details.</p>
 *  
 * <p>Implementing classes must also be a subclasses of {@link Pane}.</p>*/
public interface ImageLayer extends Updatable {

	/** Returns {@code true} if the {@link ImagePane} was successfully added, {@code false} otherwise. */
	boolean add(ImagePane ip);
	
	/** Returns {@code true} iff the {@link ImagePane} was absent. If the {@link ImagePane} is present, it is moved to
	 * the front. */
	default boolean addIfAbsent(ImagePane ip) {
		boolean result = remove(ip);
		add(ip);
		return result;
	}
	
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
	
	default void removeAll(ImagePane... ips) {
		for(ImagePane ip : ips)
			remove(ip);
	}
	
	default void removeAll(Collection<? extends ImagePane> ips) {
		for(ImagePane ip : ips)
			remove(ip);
	}
	
	List<ImagePane> imagesUnmodifiable();
	
	void resize(double width, double height);
	
	void updateImageSize(ImagePane ip);
	
	void updateImageLayoutCoords(ImagePane ip);
	
	boolean imagesIntersect(ImagePane ip1, ImagePane ip2);
	
	/** @throws IllegalStateException if the given {@link ImagePane} is not in this {@link ImageLayer}. */
	default void bringToFront(ImagePane ip) {
		if(!remove(ip))
			throw new IllegalStateException(String.format("ImagePane is not in this ImageLayer: %s", ip));
		add(ip);
	}
	
	double getWidth();
	
	double getHeight();
	
	void setGamePane(GamePane gp);
	
	GamePane gamePane();
	
}
