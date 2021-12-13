package base;

import fxutils.*;
import game.MainScene;
import javafx.animation.Transition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import minigames.Minigame;

public class FadeLayer extends StackPane {

	private static final Duration FADE_IN_DURATION = Duration.millis(500), FADE_OUT_DURATION = FADE_IN_DURATION;
	
	private final Transition fadeIn = new Transition() {
		
		{
			setCycleDuration(FADE_IN_DURATION);
			setOnFinished(eh -> fadeInFinished());
		}
		
		@Override
		protected void interpolate(double frac) {
			setBackgroundOpacity(frac);
		}
		
	};
	
	private final Transition fadeOut = new Transition() {

		{
			setCycleDuration(FADE_OUT_DURATION);
			setOnFinished(eh -> fadeOutFinished());
		}
		
		@Override
		protected void interpolate(double frac) {
			setBackgroundOpacity(1 - frac);
		}
		
	};
	
	
	private AbstractScaledPane scaledPane;
	private Runnable peakAction;
	
	public FadeLayer() {
		setBackground(null);
		Nodes.setPrefSize(this, Double.MAX_VALUE);
		peakAction = null;
	}
	
	private void setBackgroundOpacity(double frac) {
		setBackground(Backgrounds.of(Color.rgb(255, 255, 255, frac)));
	}
	
	public void fadeIn(AbstractScaledPane minigame, Runnable peakAction) {
		this.scaledPane = minigame;
		this.peakAction = peakAction;
		fadeIn.playFromStart();
	}
	
	private void fadeInFinished() {
		MainScene.get().setRootBase(scaledPane);
		fadeOut.playFromStart();
		runPeakAction();
	}
	
	private void fadeOutFinished() {
		//TODO anything here?
	}
	
	private void runPeakAction() {
		if(peakAction != null)
			peakAction.run();
	}
	
}
