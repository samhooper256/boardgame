package tiles;

import base.ImagePane;
import game.Board;
import javafx.scene.image.Image;

public abstract class Tile extends ImagePane {

	public Tile(Image tileImage) {
		super(tileImage);
	}
	
	public int index() {
		return Board.get().tileIndex(this);
	}
	
}
