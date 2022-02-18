package minigames;

import base.*;
import base.panes.ImagePane;
import minigames.sprites.*;
import players.PlayerNumbered;

/** Must only be implemented by subclasses of {@link ImagePane}. */
public interface CoordinatedHitRegioned extends SpriteAnimated, HitRegioned {

	@Override
	default HitRegion hitRegion() {
		return SpriteRegions.forImagePane((ImagePane & PlayerNumbered) this, animator().spriteIndex());
	}
	
}
