package minigames.archery.waves;

import java.util.*;

import base.MainScene;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import minigames.archery.TargetPath;

final class StandardWaveGenerator implements WaveGenerator {

	private static final double
			MIN_DELAY_MILLIS = 100,
			SPEED_FUNCTION_FACTOR = 25,
			STARTING_SPEED = 100,
			TARGET_Y = 100;
	private static final Point2D[] POINTS = {
			new Point2D(MainScene.DEFAULT_WIDTH * .1, TARGET_Y),
			new Point2D(MainScene.DEFAULT_WIDTH * .9, TARGET_Y)
	};

	private final Map<Integer, ArcheryWave> waveMap;
	
	StandardWaveGenerator() {
		waveMap = new HashMap<>();	
	}
	
	@Override
	public ArcheryWave get(int index) {
		if(!waveMap.containsKey(index)) {
			Duration delay = Duration.millis(delayInMillis(index));
			double speed = speed(index);
			TargetPath path = new TargetPath(delay, speed, POINTS);
			ArcheryWave wave = ArcheryWave.of(path);
			waveMap.put(index, wave);
		}
		return waveMap.get(index);
	}

	private static double delayInMillis(int index) {
		return 4 * MIN_DELAY_MILLIS / index + MIN_DELAY_MILLIS;
	}
	
	private static double speed(int index) {
		return SPEED_FUNCTION_FACTOR * index + (STARTING_SPEED - SPEED_FUNCTION_FACTOR);
	}
	
}
