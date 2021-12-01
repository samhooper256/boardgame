package base;

import fxutils.Images;
import javafx.scene.image.Image;

public class MinigameTile extends Tile {
	
	public static final MinigameTile ARCHERY = new MinigameTile(Images.TILE);
	
	public MinigameTile(Image tileImage) {
		super(tileImage);
	}
	
}
