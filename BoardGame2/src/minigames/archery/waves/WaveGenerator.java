package minigames.archery.waves;

public interface WaveGenerator {

	WaveGenerator STANDARD = new StandardWaveGenerator();
	
	/** For a given index {@code i}, the call {@code get(i)} returns the same object (equal by identity {@code ==})
	 * every time it is invoked. */
	ArcheryWave get(int index);
	
}
