package base;

import java.util.*;

import fxutils.Images;

public class TileSection {

	public static final List<TileSection> ORDER = new ArrayList<>(Arrays.asList(
			new TileSection(new EventTile(Images.TILE), new SafeTile(Images.TILE),
					new SafeTile(Images.TILE), new MinigameTile(Images.TILE), MinigameTile.ARCHERY),
			new TileSection(new EventTile(Images.TILE), new SafeTile(Images.TILE),
					new SafeTile(Images.TILE), new MinigameTile(Images.TILE), new MinigameTile(Images.TILE)),
			new TileSection(new EventTile(Images.TILE), new SafeTile(Images.TILE),
					new SafeTile(Images.TILE), new MinigameTile(Images.TILE), new MinigameTile(Images.TILE)),
			new TileSection(new EventTile(Images.TILE), new SafeTile(Images.TILE),
					new SafeTile(Images.TILE), new MinigameTile(Images.TILE), new MinigameTile(Images.TILE)),
			new TileSection(new EventTile(Images.TILE), new SafeTile(Images.TILE),
					new SafeTile(Images.TILE), new MinigameTile(Images.TILE), new MinigameTile(Images.TILE))
	));
	
	private final Tile[] tiles;
	
	public TileSection(Tile... tiles) {
		this.tiles = tiles;
	}

	public Tile[] tiles() {
		return tiles;
	}
	
}
