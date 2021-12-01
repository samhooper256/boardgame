package base;

import java.util.*;

import fxutils.Images;

public class TileSection {

	public static final List<TileSection> ORDER = new ArrayList<>(Arrays.asList(
			new TileSection(new EventTile(Images.EVENT_TILE), new SafeTile(Images.EVENT_TILE),
					new SafeTile(Images.EVENT_TILE), new MinigameTile(Images.EVENT_TILE), MinigameTile.ARCHERY),
			new TileSection(new EventTile(Images.EVENT_TILE), new SafeTile(Images.EVENT_TILE),
					new SafeTile(Images.EVENT_TILE), new MinigameTile(Images.EVENT_TILE), new MinigameTile(Images.EVENT_TILE)),
			new TileSection(new EventTile(Images.EVENT_TILE), new SafeTile(Images.EVENT_TILE),
					new SafeTile(Images.EVENT_TILE), new MinigameTile(Images.EVENT_TILE), new MinigameTile(Images.EVENT_TILE)),
			new TileSection(new EventTile(Images.EVENT_TILE), new SafeTile(Images.EVENT_TILE),
					new SafeTile(Images.EVENT_TILE), new MinigameTile(Images.EVENT_TILE), new MinigameTile(Images.EVENT_TILE)),
			new TileSection(new EventTile(Images.EVENT_TILE), new SafeTile(Images.EVENT_TILE),
					new SafeTile(Images.EVENT_TILE), new MinigameTile(Images.EVENT_TILE), new MinigameTile(Images.EVENT_TILE))
	));
	
	private final Tile[] tiles;
	
	public TileSection(Tile... tiles) {
		this.tiles = tiles;
	}

	public Tile[] tiles() {
		return tiles;
	}
	
}
