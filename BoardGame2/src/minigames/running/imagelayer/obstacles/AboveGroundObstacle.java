package minigames.running.imagelayer.obstacles;

import java.util.*;

import fxutils.Images;
import javafx.scene.image.Image;
import minigames.running.imagelayer.Coords;

public final class AboveGroundObstacle extends Obstacle {

	private static final Map<Image, Integer> MAP_TO_INDEX = new LinkedHashMap<>();
	private static final List<ObstacleGenerator> LIST = new ArrayList<>();
	
	static {
		addGenerator(Images.BIG_ROCK);
		addGenerator(Images.SMALL_ROCK);
	}

	/** Assumes the given {@link Image} is not a key in {@link #MAP}. */
	private static void addGenerator(Image image) {
		MAP_TO_INDEX.put(image, LIST.size());
		LIST.add((n, i) -> new AboveGroundObstacle(image, n, i));
	}
	
	public static List<ObstacleGenerator> generators() {
		return LIST;
	}
	
	public static ObstacleGenerator generatorFor(Image image) {
		if(!MAP_TO_INDEX.containsKey(image))
			throw new IllegalArgumentException(String.format("No generator for image: %s", image));
		return LIST.get(MAP_TO_INDEX.get(image));
	}
	
	private AboveGroundObstacle(Image image, int number, int index) {
		super(image, number, index);
	}

	@Override
	public void alignFor(int playerCount) {
		if(number() > playerCount)
			throw new IllegalArgumentException(String.format("number() > playerCount (%d > %d)", number(), playerCount));
		setIdealBottomY(Coords.p(playerCount).playerBottomY(number()));
	}
	
}
