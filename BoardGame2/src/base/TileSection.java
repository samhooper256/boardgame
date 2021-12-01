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
	
	private final Tile[] tiles;
	
	public TileSection(Tile... tiles) {
		this.tiles = tiles;
	}

	public Tile[] tiles() {
		return tiles;
	}
	
}
