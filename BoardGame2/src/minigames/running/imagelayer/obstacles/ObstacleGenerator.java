package minigames.running.imagelayer.obstacles;

import java.util.function.IntFunction;

import utils.RNG;

@FunctionalInterface
public interface ObstacleGenerator extends IntFunction<Obstacle> {

	static ObstacleGenerator randomAboveGround() {	
		return RNG.pick(AboveGroundObstacle.generators());
	}
	
	@Override
	default Obstacle apply(int player) {
		return create(player);
	}
	
	Obstacle create(int player);
	
}
