package minigames.sprites;

import base.HitRegion;
import base.panes.ImagePane;
import fxutils.Images;
import players.PlayerRepresentation;
import utils.rects.RectBounds;

public final class SpriteRegions {

	private SpriteRegions() {
		
	}
	
	public static <T extends ImagePane & PlayerRepresentation> HitRegion forImagePane(T ip) {
		switch(ip.number()) {
			case 1: return new HitRegion(ip, RectBounds.of(0, 0, Images.SPRITE_WIDTH, Images.SPRITE_HEIGHT));
			case 2: return new HitRegion(ip, RectBounds.of(0, 0, Images.SPRITE_WIDTH, Images.SPRITE_HEIGHT));
			case 3: return new HitRegion(ip, RectBounds.of(0, 0, Images.SPRITE_WIDTH, Images.SPRITE_HEIGHT));
			case 4: return new HitRegion(ip, RectBounds.of(0, 0, Images.SPRITE_WIDTH, Images.SPRITE_HEIGHT));
			default: throw new IllegalArgumentException(String.format("Player: %d", ip.number()));
		}
	}
	
}
