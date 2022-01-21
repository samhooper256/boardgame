package medals;

import fxutils.Images;
import javafx.scene.image.Image;

public final class Medal {

	public static final Medal
			GOLD = new Medal("Gold", 0, 3, Images.GOLD_MEDAL),
			SILVER = new Medal("Silver", 1, 2, Images.SILVER_MEDAL),
			BRONZE = new Medal("Bronze", 2, 1, Images.BRONZE_MEDAL);
	
	public static Medal of(String type) {
		switch(type.toLowerCase()) {
			case "gold": return GOLD;
			case "silver": return SILVER;
			case "bronze": return BRONZE;
		}
		throw new IllegalArgumentException(String.format("Invalid medal type: %s", type));
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
	
	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}
	
	@Override
	public int hashCode() {
		return index;
	}
	
}
