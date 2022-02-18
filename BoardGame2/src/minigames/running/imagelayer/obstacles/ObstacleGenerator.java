package minigames.running.imagelayer.obstacles;

import fxutils.Images;
import utils.RNG;

@FunctionalInterface
public interface ObstacleGenerator {

	double LONGEST_WIDTH = Images.BIG_ROCK.getWidth();
	
	static ObstacleGenerator randomAboveGround() {	
		return RNG.pick(AboveGroundObstacle.generators());
	}
	
	Obstacle create(int player, int index);
	
}
