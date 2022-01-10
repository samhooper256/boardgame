package game;

import base.ScaledPane;
import javafx.animation.*;
import javafx.event.*;
import javafx.util.Duration;
import players.RollType;

public final class BoardAnimations {

	private static final Duration
			TRANSITION_TO_CHOOSE_ROLL_DURATION = Duration.millis(800),
			TRANSITION_TO_RANDOM_ROLL_DURATION = TRANSITION_TO_CHOOSE_ROLL_DURATION;
	
	private BoardAnimations() {
		
	}
	
	/** Assumes that the last roll was {@link RollType#RANDOM}. */
	public static void transitionToChooseRoll(Runnable finishAction) {
		int topFace = RollableDie.get().face();
		Timeline timeline = new Timeline();
		KeyValue[] startKeyValues = new KeyValue[Die.FACES * 2], endKeyValues = new KeyValue[Die.FACES * 2];
		for(int i = 1; i <= Die.FACES; i++) {
			FixedDie d = FixedDie.showing(i);
			ScaledPane.center(d);
			startKeyValues[i - 1] = new KeyValue(d.idealXProperty(), Board.CENTER_X - d.getIdealWidth() / 2);
			startKeyValues[i - 1 + Die.FACES] = new KeyValue(d.idealYProperty(), Board.CENTER_Y - d.getIdealHeight() / 2);
			endKeyValues[i - 1] = new KeyValue(d.idealXProperty(), d.choiceX());
			endKeyValues[i - 1 + Die.FACES] = new KeyValue(d.idealYProperty(), d.choiceY());
		}
		KeyFrame start = new KeyFrame(Duration.ZERO, startKeyValues);
		KeyFrame end = new KeyFrame(TRANSITION_TO_CHOOSE_ROLL_DURATION, eh -> finishAction.run(), endKeyValues);
		timeline.getKeyFrames().addAll(start, end);
		Board.get().remove(RollableDie.get());
		for(int i = 1; i <= Die.FACES; i++)
			if(i != topFace)
				Board.get().add(FixedDie.showing(i));
		Board.get().add(FixedDie.showing(topFace));
		timeline.playFromStart();
	}
	
	/** Assumes that the last roll was {@link RollType#CHOOSE}. */
	public static void transitionToRandomRoll(Runnable finishAction) {
		RollableDie.get().setFace(1);
		Board.get().bringToFront(FixedDie.showing(1));
		Timeline timeline = new Timeline();
		KeyValue[] startKeyValues = new KeyValue[Die.FACES * 2], endKeyValues = new KeyValue[Die.FACES * 2];
		for(int i = 1; i <= Die.FACES; i++) {
			FixedDie d = FixedDie.showing(i);
			startKeyValues[i - 1] = new KeyValue(d.idealXProperty(), d.getIdealX());
			startKeyValues[i - 1 + Die.FACES] = new KeyValue(d.idealYProperty(), d.getIdealY());
			endKeyValues[i - 1] = new KeyValue(d.idealXProperty(), Board.CENTER_X - d.getIdealWidth() / 2);
			endKeyValues[i - 1 + Die.FACES] = new KeyValue(d.idealYProperty(), Board.CENTER_Y - d.getIdealHeight() / 2);
		}
		KeyFrame start = new KeyFrame(Duration.ZERO, startKeyValues);
		KeyFrame end = new KeyFrame(TRANSITION_TO_RANDOM_ROLL_DURATION, buildTransitionToRandomRollFinish(finishAction),
				endKeyValues);
		timeline.getKeyFrames().addAll(start, end);
		timeline.playFromStart();
	}
	
	private static EventHandler<ActionEvent> buildTransitionToRandomRollFinish(Runnable finishAction) {
		return eh -> {
			Board.get().add(RollableDie.get());
			for(int i = 1; i <= Die.FACES; i++)
				Board.get().remove(FixedDie.showing(i));
			finishAction.run();
		};
	}
}