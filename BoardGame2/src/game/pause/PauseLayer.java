package game.pause;

import fxutils.*;
import game.board.fx.BoardFXLayer;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/** placed within the {@link BoardFXLayer}. */
public class PauseLayer extends StackPane implements Fadeable {
	
	private static final Duration FADE_IN = Duration.millis(500), FADE_OUT = FADE_IN;
	private static final double OPACITY = .5;
	
	private final Fader fader;
	private final VBox vBox;
	
	public PauseLayer() {
		super();
		fader = new Fader(this).setInDuration(FADE_IN).setOutDuration(FADE_OUT);
		vBox = new VBox(new PausedText(), new  ButtonBox());
		vBox.setAlignment(Pos.CENTER);
		getChildren().add(vBox);
		setBackground(Backgrounds.of(Color.rgb(0, 0, 0, OPACITY)));
		setMouseTransparent(false);
		setVisible(false);
	}
	
	@Override
	public Fader fader() {
		return fader;
	}
	
}
