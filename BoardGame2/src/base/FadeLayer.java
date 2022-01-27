package base;

import base.panes.GamePane;
import fxutils.*;
import game.*;
import javafx.animation.Transition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class FadeLayer extends StackPane {

	private final Transition fadeIn = new Transition() {
		
		{
			setCycleDuration(Board.FADE_IN_DURATION);
			setOnFinished(eh -> fadeInFinished());
		}
		
		@Override
		protected void interpolate(double frac) {
			setBackgroundOpacity(frac);
		}
		
	};
	
	private final Transition fadeOut = new Transition() {

		{
			setCycleDuration(Board.FADE_OUT_DURATION);
			setOnFinished(eh -> fadeOutFinished());
		}
		
		@Override
		protected void interpolate(double frac) {
			setBackgroundOpacity(1 - frac);
		}
		
	};
	
	
	private GamePane gamePane;
	private Runnable peakAction, finishAction;
	
	public FadeLayer() {
		setBackground(null);
		Nodes.setPrefSize(this, Double.MAX_VALUE);
		peakAction = null;
	}
	
	private void setBackgroundOpacity(double frac) {
		setBackground(Backgrounds.of(Color.rgb(255, 255, 255, frac)));
	}
	
	public void fadeIn(GamePane gamePane, Runnable peakAction, Runnable finishAction) {
		this.gamePane = gamePane;
		this.peakAction = peakAction;
		this.finishAction = finishAction;
		fadeIn.playFromStart();
	}
	
	private void fadeInFinished() {
		MainScene.get().setRootBase(gamePane);
		fadeOut.playFromStart();
		runPeakAction();
	}
	
	private void fadeOutFinished() {
		MainScene.get().removeFadeLayer();
		runFinishAction();
	}
	
	private void runPeakAction() {
		if(peakAction != null)
			peakAction.run();
	}
	
	private void runFinishAction() {
		if(finishAction != null)
			finishAction.run();
	}
	
}
