package medals;

import java.util.*;

import fxutils.Images;
import javafx.scene.image.Image;

public final class Medal implements Comparable<Medal> {

	private static final Medal[] MEDALS = {
			new Medal("Gold", 0, 3, Images.GOLD_MEDAL),
			new Medal("Silver", 1, 2, Images.SILVER_MEDAL),
			new Medal("Bronze", 2, 1, Images.BRONZE_MEDAL)
	};
	
	public static final Medal GOLD = MEDALS[0], SILVER = MEDALS[1], BRONZE = MEDALS[2];
	
	public static final List<Medal> LIST = Collections.unmodifiableList(Arrays.asList(GOLD, SILVER, BRONZE));
	
	public static Medal of(String type) {
		switch(type.toLowerCase()) {
			case "gold": return GOLD;
			case "silver": return SILVER;
			case "bronze": return BRONZE;
		}
		throw new IllegalArgumentException(String.format("Invalid medal type: %s", type));
	}
	
	public static Medal withIndex(int index) {
		return MEDALS[index];
	}
	
	/** Returns the number of different kinds of {@link Medal Medals}. */
	public static int count() {
		return 3;
	}
	
	private final String name;
	private final Image image;
	private final int pointValue, index;
	
	private Medal(String name, int index, int pointValue, Image image) {
		this.name = name;
		this.index = index;
		this.pointValue = pointValue;
		this.image = image;
	}
	
	public String name() {
		return name;
	}
	
	/** Returns {@code 0} for {@link #GOLD}, {@code 1} for {@link #SILVER}, and {@code 2} for {@link #BRONZE}. */
	public int index() {
		return index;
	}
	
	public int pointValue() {
		return pointValue;
	}
	
	public Image image() {
		return image;
	}
	
	/** Returns the next {@link Medal} down in value (e.g. {@code Medal.GOLD.down()} returns {@link #SILVER}).
	 * Returns {@code null} if {@code this} is {@link #BRONZE}. */
	public Medal down() {
		if(this == BRONZE)
			return null;
		return withIndex(index() + 1);
	}
	
	/** Medals are compared by their {@link #index()}. The order is {@link #GOLD}, {@link #SILVER}, {@link #BRONZE}. */
	@Override
	public int compareTo(Medal o) {
		return Integer.compare(index, o.index);
	}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}
	
	@Override
	public int hashCode() {
		return index;
	}
	
}
