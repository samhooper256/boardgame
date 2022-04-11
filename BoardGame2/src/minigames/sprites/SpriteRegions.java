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
		return forImagePane(ip, Images.airSpriteIndex(ip.number()));
	}
	
	public static <T extends ImagePane & PlayerNumbered> HitRegion stillSpriteForImagePane(T ip) {
		return forImagePane(ip, Images.stillSpriteIndex(ip.number()));
	}
	
	//add one to bottom right coordinate from GIMP; don't add one to top left coordinate in GIMP.
	
	public static <T extends ImagePane & PlayerNumbered> HitRegion forImagePane(T ip, int sprite) {
		int n = ip.number();
		switch(sprite) {
			case 0:
				switch(n) {
					case 1: return new HitRegion(ip, 
						fc(21, 116, 87, 150),
						fc(64, 55, 84, 117),
						fc(26, 1, 65, 117)
					);
					case 2: return new HitRegion(ip, RectBounds.of(0, 0, 80, 120));
					case 3: return new HitRegion(ip, 
						fc(65, 105, 90, 150),
						fc(0, 82, 80, 109),
						fc(37, 42, 90, 89),
						fc(49, 7, 99, 43)
					);
					case 4: return new HitRegion(ip,
						fc(2, 95, 73, 123),
						fc(72, 45, 99, 150),
						fc(43, 1, 83, 96)
					);
					default: throw new IllegalArgumentException(String.format("Player: %d", ip.number()));
				}
			case 1:
				switch(n) {
					case 1: return new HitRegion(ip, 
						fc(39, 123, 66, 150),
						fc(26, 103, 81, 124),
						fc(26, 1, 77, 104)
					);
					case 2: return new HitRegion(ip, RectBounds.of(0, 0, 80, 120));
					case 3: return new HitRegion(ip,
						fc(54, 106, 77, 150),
						fc(9, 85, 55, 117),
						fc(38, 42, 91, 107),
						fc(50, 7, 100, 45)
					);
					case 4: return new HitRegion(ip, 
						fc(12, 118, 64, 144),
						fc(63, 118, 83, 150),
						fc(43, 1, 83, 119),
						fc(82, 52, 99, 66)
					);
					default: throw new IllegalArgumentException(String.format("Player: %d", ip.number()));
				}
			case 2:
				switch(n) {
					case 1: return new HitRegion(ip,
						fc(28, 118, 75, 150),
						fc(29, 102, 84, 119),
						fc(29, 62, 80, 103),
						fc(29, 0, 69, 63)
					);
					case 2: return new HitRegion(ip, RectBounds.of(0, 0, 80, 120));
					case 3: return new HitRegion(ip, 
						fc(34, 105, 80, 150),
						fc(38, 42, 91, 106),
						fc(50, 7, 100, 43)
					);
					case 4: return new HitRegion(ip, 
						fc(37, 134, 59, 148),
						fc(43, 59, 79, 135),
						fc(78, 68, 91, 79),
						fc(43, 0, 83, 60)
					);
					default: throw new IllegalArgumentException(String.format("Player: %d", ip.number()));
				}
			case 3:
				switch(n) {
					case 1: return new HitRegion(ip,
						fc(23, 118, 88, 147),
						fc(26, 94, 81, 119),
						fc(26, 71, 72, 95),
						fc(26, 0, 66, 72)
					);
					case 2: return new HitRegion(ip, RectBounds.of(0, 0, 80, 120));
					case 3: return new HitRegion(ip, 
						fc(32, 42, 91, 150),
						fc(50, 8, 100, 43)
					);
					case 4: return new HitRegion(ip,
						fc(57, 124, 78, 149),
						fc(22, 100, 58, 141),
						fc(43, 0, 89, 125),
						fc(88, 50, 99, 62)
					);
					default: throw new IllegalArgumentException(String.format("Player: %d", ip.number()));
				}
			default: throw new IllegalArgumentException(String.format("sprite=%d", sprite));
		}
	}

	private static final double X_FACTOR = Images.SPRITE_SCALE_FACTOR, Y_FACTOR = Images.SPRITE_SCALE_FACTOR;
	
	/** Given coords are scaled by {@link #X_FACTOR} and {@link #Y_FACTOR}. */
	private static RectBounds fc(double tlx, double tly, double brx, double bry) {
		return RectBounds.fromCorners(tlx * X_FACTOR, tly * Y_FACTOR, brx * X_FACTOR, bry * Y_FACTOR);
	}
	
}
