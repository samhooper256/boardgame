package minigames.archery.waves;

import minigames.archery.*;

public interface ArcheryWave {

	static ArcheryWave of(TargetPath path) {
		return new ArcheryWaveImpl(path);
	}
	
	TargetPath path();
	
	default Target createTarget() {
		return new Target(path());
	}
	
}
