package base.panes;

import java.util.*;

import base.Updatable;
import javafx.scene.layout.Pane;

/**<p>{@link ImageLayer ImageLayers} allow for {@link ImagePane ImagePanes} to be properly displayed on the
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
		
		/** Inclusive. The lower bound of the {@link List#subList(int, int) subList} of
		 * {@link ImageLayer#imagesUnmodifiable()} that this {@link Packet} corresponds to. */
		int low();
		
		/** Exclusive. The upper bound of the {@link List#subList(int, int) subList} of
		 * {@link ImageLayer#imagesUnmodifiable()} that this {@link Packet} corresponds to. */		
		int high();
		
	}
	
	/** Adds the given {@link ImagePane} to the bottom packet.
	 * Returns {@code true} if the {@link ImagePane} was successfully added, {@code false} otherwise. The call
	 * {@code add(x)} is equivalent to {@code add(x, 0)}. */
	default boolean add(ImagePane image) {
		return add(0, image);
	}
	
	/** Adds the given {@link ImagePane} to the bottom packet if it is absent. Returns {@code true} iff the
	 * {@link ImagePane} was absent. If the {@link ImagePane} is present, it is moved to the front. */
	default boolean addIfAbsent(ImagePane image) {
		boolean present = remove(image);
		add(image);
		return !present;
	}
	
	default void addAll(ImagePane... images) {
		for(ImagePane image : images)
			add(image);
	}
	
	default void addAll(Collection<? extends ImagePane> images) {
		for(ImagePane image : images)
			add(image);
	}
	
	/** <p>Adds the given {@link ImagePane} to the top of the {@link Packet} with the given
	 * {@link Packet#index() index}. Returns {@code true} iff the {@link ImagePane} was successfully added,
	 * {@code false} otherwise.</p>
	 * <p>If {@code (index >= packetsUnmodifiable().size())}, a new packet will be created with the given index as well
	 * as for all indices {@code i}, {@code packetsUnmodifiable().size() <= i < index}. The size of
	 * {@link #packetsUnmodifiable()} will thus be equal to {@code index + 1} after this method returns.</p> */
	boolean add(int packetIndex, ImagePane image);
	
	default void addAll(int packetIndex, ImagePane... images) {
		for(ImagePane image : images)
			add(packetIndex, image);
	}
	
	/** Attempts to remove the given {@link ImagePane}, which may be in any {@link Packet}.
	 * Returns {@code true} if the {@link ImagePane} was present and has been removed, {@code false} otherwise. */
	boolean remove(ImagePane image);
	
	default void removeAll(ImagePane... images) {
		for(ImagePane image : images)
			remove(image);
	}
	
	default void removeAll(Collection<? extends ImagePane> images) {
		for(ImagePane ip : images)
			remove(ip);
	}
	
	void clearAllImages();
	
	/** Returns the {@link Packet} with the given index. Throws an exception if
	 * {@code (index < 0 || index >= packetsUnmodifiable().size)} */
	Packet getPacket(int index);
	
	/** The returned {@link List} does not necessarily reflect new {@link Packet Packets} added after this method is
	 * invoked. */
	List<Packet> packetsUnmodifiable();
	
	/** The returned {@link List} reflects new {@link ImagePane ImagePanes} added after this method is invoked. */
	List<ImagePane> imagesUnmodifiable();
	
	void resize(double width, double height);
	
	void updateImageSize(ImagePane image);
	
	void updateImageLayoutCoords(ImagePane image);
	
	boolean imagesIntersect(ImagePane image1, ImagePane image2);
	
	/** @throws IllegalStateException if the given {@link ImagePane} is not in this {@link ImageLayer}. */
	void bringToFrontOfPacket(ImagePane image);
	
	/** Returns {@code -1} if the given {@link ImagePane} is not in this {@link ImageLayer}. */
	int getIndexOfPacketContaining(ImagePane image);
	
	double getWidth();
	
	double getHeight();
	
	void setGamePane(GamePane gp);
	
	GamePane gamePane();
	
}
