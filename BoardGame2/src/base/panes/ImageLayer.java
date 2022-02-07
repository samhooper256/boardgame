package base.panes;

import java.util.*;

import base.Updatable;
import javafx.scene.layout.Pane;

/**<p>{@link ImageLayer ScaledImageLayers} allow for {@link ImagePane ImagePanes} to be properly displayed on the
 * screen. See the documentation of {@link ImagePane} for more details.</p>
 *  
 * <p>Implementing classes must also be a subclasses of {@link Pane}.</p>*/
public interface ImageLayer extends Updatable {

	/** A packet of {@link ImagePane}. If one packet is above another, all of its images will be rendered on top of
	 * those from the lower packet. */
	interface Packet {
		
		/** The returned {@link List} is unmodifiable. */
		List<ImagePane> images();
		
		int index();
		
	}
	
	/** Adds the given {@link ImagePane} to the bottom packet.
	 * Returns {@code true} if the {@link ImagePane} was successfully added, {@code false} otherwise. */
	boolean add(ImagePane ip);
	
	/** Adds the given {@link ImagePane} to the bottom packet if it is absent. Returns {@code true} iff the
	 * {@link ImagePane} was absent. If the {@link ImagePane} is present, it is moved to the front. */
	default boolean addIfAbsent(ImagePane ip) {
		boolean present = remove(ip);
		add(ip);
		return !present;
	}
	
	default void addAll(ImagePane... ips) {
		for(ImagePane ip : ips)
			add(ip);
	}
	
	default void addAll(Collection<? extends ImagePane> ips) {
		for(ImagePane ip : ips)
			add(ip);
	}
	
	/** Attempts to remove the given {@link ImagePane}, which may be in any {@link Packet}.
	 * Returns {@code true} if the {@link ImagePane} was present and has been removed, {@code false} otherwise. */
	boolean remove(ImagePane ip);
	
	default void removeAll(ImagePane... ips) {
		for(ImagePane ip : ips)
			remove(ip);
	}
	
	default void removeAll(Collection<? extends ImagePane> ips) {
		for(ImagePane ip : ips)
			remove(ip);
	}
	
	void clearAllImages();
	
	/** Returns the {@link Packet} with the given index. */
	Packet getPacket(int index);
	
	List<Packet> packetsUnmodifiable();
	
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
