package minigames.archery.waves;

import minigames.archery.*;

public interface ArcheryWave {

	static ArcheryWave of(TargetPath path) {
		return new ArcheryWaveImpl(path);
	}
	
	TargetPath path();
	
	default Target createTarget() {
		Target t = new Target(path(), null);
		Runnable hitAction = () -> {
			System.out.println("Hit!!");
			ArcheryMinigame.get().trash(t);
		};
		t.setHitAction(hitAction);
		return t;
	}
	
}
