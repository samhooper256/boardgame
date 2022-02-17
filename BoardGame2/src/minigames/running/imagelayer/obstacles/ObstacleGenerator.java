package minigames.running.imagelayer.obstacles;

import utils.RNG;

@FunctionalInterface
public interface ObstacleGenerator {

	static ObstacleGenerator randomAboveGround() {	
		return RNG.pick(AboveGroundObstacle.generators());
	}
	
	Obstacle create(int player, int index);
	
}
