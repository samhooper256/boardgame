package tiles;

import fxutils.Images;

public class SafeTile extends Tile {

	public SafeTile() {
		super(Images.SAFE_TILE);
		this.setPickOnBounds(true);
		this.setOnMouseEntered(eh -> {
			System.out.println("entered");
		});
		this.setOnMouseClicked(eh -> {
			System.out.println("mouse clicked");
		});
	}
	
}
