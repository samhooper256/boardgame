package game.fx;

import fxutils.*;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/** placed within the {@link BoardFXLayer}. */
public class PauseLayer extends StackPane implements Fadeable {
	
	private static final Duration FADE_IN = Duration.millis(500), FADE_OUT = FADE_IN;
	
	private final Fader fader;
	private final VBox vBox;
	private final PausedText pausedText;
	
	public PauseLayer() {
		super();
		fader = new Fader(this).setInDuration(FADE_IN).setOutDuration(FADE_OUT);
		pausedText = new PausedText();
		vBox = new VBox(pausedText);
		vBox.setAlignment(Pos.CENTER);
		getChildren().add(vBox);
		setBackground(Backgrounds.of(Color.rgb(0, 0, 0, 0.25)));
		this.setMouseTransparent(false);
		setVisible(false);
	}
	
	@Override
	public Fader fader() {
		return fader;
	}
	
}
