package minigames.sprites;

import base.Updatable;
import players.PlayerNumbered;

public interface SpriteAnimated extends PlayerNumbered, Updatable {

	SpriteAnimator animator();
	
}
