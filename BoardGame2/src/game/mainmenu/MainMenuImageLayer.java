package game.mainmenu;

import static game.MainScene.*;

import base.panes.*;
import fxutils.Images;
import javafx.animation.Transition;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MainMenuImageLayer extends AbstractImageLayer {

	private final PlayButton playButton;
	private final QuitButton quitButton;
	
	Rectangle clip = new Rectangle(100, 200);
	
	MainMenuImageLayer() {
		ImagePane mainImage = new ImagePane(Images.MAIN_MENU);
		mainImage.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		mainImage.setIdealCenter(CENTER_X, CENTER_Y * .5);
		mainImage.setClip(clip);
		playButton = new PlayButton();
		playButton.setIdealCenter(CENTER_X, DEFAULT_HEIGHT * .55);
		quitButton = new QuitButton();
		quitButton.setIdealCenter(CENTER_X, DEFAULT_HEIGHT * .7);
		addAll(mainImage, playButton, quitButton);
		
		Transition t = new Transition() {
			{
				setCycleDuration(Duration.seconds(2));
			}
			@Override
			protected void interpolate(double frac) {
				clip.setHeight(frac * 600);
			}
		};
		t.playFromStart();
	}

	@Override
	public void updatePane(long diff) {
		// nothing
	}
	
	public PlayButton playButton() {
		return playButton;
	}
	
}
