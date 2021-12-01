package base;

import java.util.*;

public class TileSection {

	public static final List<TileSection> ORDER = new ArrayList<>(Arrays.asList(
			new TileSection(new EventTile(), new SafeTile(),
					new SafeTile(), new MinigameTile(), new MinigameTile()),
			new TileSection(new EventTile(), new SafeTile(),
					new SafeTile(), new MinigameTile(), new MinigameTile()),
			new TileSection(new EventTile(), new SafeTile(),
					new SafeTile(), new MinigameTile(), new MinigameTile()),
			new TileSection(new EventTile(), new SafeTile(),
					new SafeTile(), new MinigameTile(), new MinigameTile()),
			new TileSection(new EventTile(), new SafeTile(),
					new SafeTile(), new MinigameTile(), new MinigameTile())
	));
	
	private final List<Tile> tiles;
	
	public TileSection(Tile... tiles) {
		this.tiles = new ArrayList<>(tiles.length);
		Collections.addAll(this.tiles, tiles);
	}

	/** Returns the {@link Tile Tiles} that comprise this {@link TileSection}. The returned order is the same every
	 * time. */
	public List<Tile> tilesUnmodifiable() {
		return Collections.unmodifiableList(tiles);
	}
	
	public List<Tile> randomOrder() {
		List<Tile> order = new ArrayList<>(tiles);
		Collections.shuffle(order);
		return order;
	}
	
}
