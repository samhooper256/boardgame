package game.board;

import game.MainScene;
import javafx.animation.*;
import javafx.event.*;
import javafx.util.Duration;
import players.RollType;
import utils.Screen;

public final class BoardAnimations {

	private static final Duration
			TRANSITION_TO_CHOOSE_ROLL_DURATION = Duration.millis(800),
			TRANSITION_TO_RANDOM_ROLL_DURATION = TRANSITION_TO_CHOOSE_ROLL_DURATION;
	
	private BoardAnimations() {
		
	}
	
	/** Does not do any animations.  */
	public static void setupFirstDie(RollType rollType) {
		removeAllDice();
		setupWithoutAnimation(rollType);
	}
	
	private static void removeAllDice() {
		Board.il().remove(RollableDie.get());
		removeFixedDice();
	}
	
	private static void removeFixedDice() {
		for(int i = 1; i <= Die.FACES; i++)
			Board.il().remove(FixedDie.showing(i));
	}
	
	private static void addFixedDice() {
		for(int i = 1; i <= Die.FACES; i++)
			Board.il().add(FixedDie.showing(i));
	}

	private static void setFixedDieToDestPositions() {
		for(int i = 1; i <= Die.FACES; i++)
			FixedDie.showing(i).moveToDest();
	}
	
	/** Assumes that the last roll was {@link RollType#RANDOM}. */
	public static void transitionToChooseRoll(Runnable finishAction) {
		int topFace = RollableDie.get().face();
		Timeline timeline = new Timeline();
		KeyValue[] startKeyValues = new KeyValue[Die.FACES * 2], endKeyValues = new KeyValue[Die.FACES * 2];
		for(int i = 1; i <= Die.FACES; i++) {
			FixedDie d = FixedDie.showing(i);
			Screen.center(d);
			startKeyValues[i - 1] = new KeyValue(d.idealXProperty(), MainScene.CENTER_X - d.getIdealWidth() / 2);
			startKeyValues[i - 1 + Die.FACES] = new KeyValue(d.idealYProperty(), MainScene.CENTER_Y - d.getIdealHeight() / 2);
			endKeyValues[i - 1] = new KeyValue(d.idealXProperty(), d.destX());
			endKeyValues[i - 1 + Die.FACES] = new KeyValue(d.idealYProperty(), d.destY());
		}
		KeyFrame start = new KeyFrame(Duration.ZERO, startKeyValues);
		KeyFrame end = new KeyFrame(TRANSITION_TO_CHOOSE_ROLL_DURATION, eh -> finishAction.run(), endKeyValues);
		timeline.getKeyFrames().addAll(start, end);
		Board.il().remove(RollableDie.get());
		for(int i = 1; i <= Die.FACES; i++)
			if(i != topFace)
				Board.il().add(FixedDie.showing(i));
		Board.il().add(FixedDie.showing(topFace));
		timeline.playFromStart();
	}
	
	/** Assumes that the last roll was {@link RollType#CHOOSE}. */
	public static void transitionToRandomRoll(Runnable finishAction) {
		RollableDie.get().setFace(1);
		Board.il().bringToFrontOfPacket(FixedDie.showing(1));
		Timeline timeline = new Timeline();
		KeyValue[] startKeyValues = new KeyValue[Die.FACES * 2], endKeyValues = new KeyValue[Die.FACES * 2];
		for(int i = 1; i <= Die.FACES; i++) {
			FixedDie d = FixedDie.showing(i);
			startKeyValues[i - 1] = new KeyValue(d.idealXProperty(), d.getIdealX());
			startKeyValues[i - 1 + Die.FACES] = new KeyValue(d.idealYProperty(), d.getIdealY());
			endKeyValues[i - 1] = new KeyValue(d.idealXProperty(), MainScene.CENTER_X - d.getIdealWidth() / 2);
			endKeyValues[i - 1 + Die.FACES] = new KeyValue(d.idealYProperty(), MainScene.CENTER_Y - d.getIdealHeight() / 2);
		}
		KeyFrame start = new KeyFrame(Duration.ZERO, startKeyValues);
		KeyFrame end = new KeyFrame(TRANSITION_TO_RANDOM_ROLL_DURATION, buildTransitionToRandomRollFinish(finishAction),
				endKeyValues);
		timeline.getKeyFrames().addAll(start, end);
		timeline.playFromStart();
	}
	
	private static EventHandler<ActionEvent> buildTransitionToRandomRollFinish(Runnable finishAction) {
		return eh -> {
			Board.il().add(RollableDie.get());
			removeFixedDice();
			finishAction.run();
		};
	}
	
	/** Assumes there are no die already on the screen. */
	private static void setupWithoutAnimation(RollType rollType) {
		switch(rollType) {
			case RANDOM:
				Board.il().add(RollableDie.get());
				break;
			case CHOOSE:
				setFixedDieToDestPositions();
				addFixedDice();
				break;
			default: throw new UnsupportedOperationException(String.format("RollType: %s", rollType));
		}
	}
	

}