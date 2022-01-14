package minigames.archery.waves;

import java.util.function.Consumer;

import minigames.archery.*;

public interface ArcheryWave {

	static ArcheryWave of(TargetPath path) {
		return new ArcheryWaveImpl(path);
	}
	
	TargetPath path();
	
	/** The given {@link Consumer} need not {@link ArcheryMinigame#trash(base.ImagePane) trash} the {@link Target}.
	 * The passed action is wrapped in another action that will call {@code trash}. The given action may be {@code null}
	 * (it will do nothing). */
	default Target createTarget(Consumer<Target> hitAction) {
		Target t = new Target(path(), null);
		Runnable hitActionInternal = () -> {
			ArcheryMinigame.get().trash(t);
			if(hitAction != null)
				hitAction.accept(t);
		};
		t.setHitAction(hitActionInternal);
		return t;
	}
	
}
