package minigames;

import java.util.*;
import java.util.function.Supplier;

import fxutils.Images;
import javafx.scene.image.Image;
import minigames.archery.Archery;
import minigames.hurdles.Hurdles;
import minigames.running.Running;
import utils.RNG;

/** Contains an enum constant corresponding to each kind of {@link Minigame}. Each instance provides access to that
 * the corresponding {@link Minigame} object and some related images. */
public enum MiniTag {
	ARCHERY(Archery::get), HURDLES(Hurdles::get), RUNNING(Running::get);
	
	public static final EnumSet<MiniTag> ENABLED = EnumSet.allOf(MiniTag.class);
//	public static final EnumSet<MiniTag> ENABLED = EnumSet.of(HURDLES);
	
	private static final List<MiniTag> ENABLED_AS_LIST = new ArrayList<>(ENABLED); //so we can choose a random one.
	
	public static MiniTag random() {
		return ENABLED_AS_LIST.get(RNG.intExclusive(ENABLED.size()));
	}
	
	/** Should always return the same object. */
	private final Supplier<? extends Minigame> minigameSupplier;
	
	MiniTag(Supplier<? extends Minigame> minigameSupplier) {
		this.minigameSupplier = minigameSupplier;
	}
	
	public Minigame minigame() {
		return minigameSupplier.get();
	}
	
	public Image tileImage() {
		return Images.tileImage(this);
	}
	
	public Image instructions() {
		return Images.instructions(this);
	}
	
	public Image background() {
		return Images.background(this);
	}
	
	public boolean hasBackground() {
		return Images.background(this) != null;
	}
	
}
