package game.pause;

import base.Main;
import fxutils.*;
import game.fx.BoardFXLayer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/** placed within the {@link BoardFXLayer}. */
public class PauseLayer extends StackPane implements Fadeable {
	
	private static final Duration FADE_IN = Duration.millis(500), FADE_OUT = FADE_IN;
	
	private final Fader fader;
	private final VBox vBox;
	private final PausedText pausedText;
	private final Button quit;
	
	public PauseLayer() {
		super();
		fader = new Fader(this).setInDuration(FADE_IN).setOutDuration(FADE_OUT);
		pausedText = new PausedText();
		quit = new Button("Quit :O");
		quit.setOnAction(eh -> Main.stage().close());
		vBox = new VBox(pausedText, quit);
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
