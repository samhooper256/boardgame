package game.board;

import java.util.*;
import java.util.function.*;

import base.Updatable;
import base.panes.ImagePane;
import fxutils.Images;
import game.helper.HelperInfo;
import utils.*;

public final class RollableDie extends ImagePane implements Die, Updatable {
	
	private static final int DEFAULT_FACE = 1;
	private static final long ROLL_DURATION = (long) 3e9;
	/** The number of different faces to show while the die is rolling. */
	private static final int FACE_COUNT = 10;
	private static final IntToLongFunction DURATION_FUNCTION = i -> 
		(ROLL_DURATION  / (FACE_COUNT * FACE_COUNT)) * (i * i);
	private static final List<Long> SWITCH_TIMES = Collections.unmodifiableList(generateSwitchTimes()); 
	private static final RollableDie INSTANCE = new RollableDie();
	
	public static RollableDie get() {
		return INSTANCE;
	}
	
	private static List<Long> generateSwitchTimes() {
		List<Long> times = new ArrayList<>();
		for(int i = 0; i < FACE_COUNT; i++) {
			long duration = DURATION_FUNCTION.applyAsLong(i);
			times.add(duration);
		}
		return times;
	}
	
	private int currentFace, nextSwitchIndex;
	private long rollElapsed;
	
	private RollableDie() {
		super(Images.die(DEFAULT_FACE));
		currentFace = DEFAULT_FACE;
		rollElapsed = -1;
		Screen.center(this);
		this.setOnMouseClicked(eh -> tryRoll());
	}
	
	public void tryRoll() {
		if(Board.get().readyToRoll() && !isRolling())
			startRoll();
	}
	
	private void startRoll() {
		Board.il().notifyRollableDieRolled();
		rollElapsed = 0;
		nextSwitchIndex = 1;
	}
	
	@Override
	public void update(long diff) {
		if(rollElapsed >= ROLL_DURATION) {
			rollElapsed = -1;
			Board.get().executeTurn(face());
		}
		else if(isRolling()) {
			rollElapsed += diff;
			if(nextSwitchIndex < SWITCH_TIMES.size() && rollElapsed >= SWITCH_TIMES.get(nextSwitchIndex)) {
				setFace(differentFace());
				nextSwitchIndex++;
			}
		}
	}
	
	/** Accepts an {@code int} from {@code 1} to {@code 6} (inclusive). */
	public void setFace(int face) {
		setImage(Images.die(face));
		currentFace = face;
	}
	
	private int differentFace() {
		int face;
		do
			face = RNG.intInclusive(1, 6);
		while(face == currentFace);
		return face;
	}
	
	public boolean isRolling() {
		return rollElapsed >= 0;
	}

	@Override
	public int face() {
		return currentFace;
	}
	
	@Override
	public void setIdealX(double idealX) {
		super.setIdealX(idealX);
	}
	
	@Override
	public void setIdealY(double idealY) {
		super.setIdealY(idealY);
	}
	
}
