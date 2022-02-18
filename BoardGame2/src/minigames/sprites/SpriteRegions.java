package minigames.sprites;

import base.HitRegion;
import base.panes.ImagePane;
import fxutils.Images;
import players.PlayerNumbered;
import utils.rects.RectBounds;

public final class SpriteRegions {

	private SpriteRegions() {
		
	}
	
	public static <T extends ImagePane & PlayerNumbered> HitRegion airSpriteForImagePane(T ip) {
		switch(ip.number()) {
			case 1: return new HitRegion(ip, RectBounds.of(0, 0, Images.SPRITE_WIDTH, Images.SPRITE_HEIGHT));
			case 2: return new HitRegion(ip, RectBounds.of(0, 0, Images.SPRITE_WIDTH, Images.SPRITE_HEIGHT));
			case 3: return new HitRegion(ip,
				fc(54, 106, 77, 150),
				fc(9, 85, 55, 117),
				fc(38, 42, 91, 107),
				fc(50, 7, 100, 45)
			);
			case 4: return new HitRegion(ip, RectBounds.of(0, 0, Images.SPRITE_WIDTH, Images.SPRITE_HEIGHT));
			default: throw new IllegalArgumentException(String.format("Player: %d", ip.number()));
		}
	}

	private static final double X_FACTOR = Images.SPRITE_WIDTH / 100, Y_FACTOR = Images.SPRITE_HEIGHT / 150;
	
	/** Given coords are scaled by {@link #X_FACTOR} and {@link #Y_FACTOR}. */
	private static RectBounds fc(double tlx, double tly, double brx, double bry) {
		return RectBounds.fromCorners(tlx * X_FACTOR, tly * Y_FACTOR, brx * X_FACTOR, bry * Y_FACTOR);
	}
	
}
