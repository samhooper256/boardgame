package game;

import javafx.animation.*;
import javafx.animation.Animation.Status;
import javafx.util.Duration;
import players.Player;

final class WalkAnimation {

	private static final Duration STEP_DURATION = Duration.millis(200);
	
	private final Timeline timeline;
	private final Player player;
	private final int pStartIndex, destIndex;
	
	public WalkAnimation(Player player, int distance) {
		this.player = player;
		this.pStartIndex = player.tile().index();
		this.destIndex = pStartIndex + distance;
		timeline = new Timeline();
		for(int i = 1; i <= distance; i++) {
			int iCopy = i; //for lambda below.
			timeline.getKeyFrames().add(new KeyFrame(STEP_DURATION.multiply(i), eh -> {
				Board.get().movePlayer(player, pStartIndex + iCopy);
				if(iCopy == distance)
					Board.get().walkFinished(this);
			}));
		}
	}
	
	public void playFromStart() {
		timeline.playFromStart();
	}
	
	public boolean isRunning() {
		return timeline.getStatus() == Status.RUNNING;
	}

}
