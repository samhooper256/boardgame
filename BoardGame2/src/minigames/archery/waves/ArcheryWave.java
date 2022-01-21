package minigames.archery.waves;

import minigames.archery.*;

public interface ArcheryWave {

	static ArcheryWave of(TargetPath path) {
		return new ArcheryWaveImpl(path);
	}
	
	TargetPath path();
	
	/** The given {@link HitAction} need not {@link ArcheryMinigame#trash(base.ImagePane) trash} the {@link Target}.
	 * The passed action is wrapped in another action that will call
	 * {@link ArcheryMinigame#trash(base.ImagePane) trash}. The given action may be {@code null} (it will do nothing).
	 * The given action will be invoked <em>after</em> {@link ArcheryMinigame#trash(base.ImagePane) trash} is called. */
	default Target createTarget(HitAction hitAction) {
		Target t = new Target(path(), null);
		HitAction hitActionInternal = (arrow, target) -> {
			ArcheryMinigame.sp().trash(t);
			ArcheryMinigame.sp().addEndOfUpdateAction(() -> hitAction.accept(arrow, target));
		};
		t.setHitAction(hitActionInternal);
		return t;
	}
	
}