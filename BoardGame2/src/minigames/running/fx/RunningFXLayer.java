package minigames.running.fx;

import java.util.*;

import fxutils.*;
import javafx.scene.control.Label;
import minigames.*;
import minigames.running.Running;
import minigames.running.imagelayer.*;
import minigames.running.imagelayer.obstacles.Obstacle;

public class RunningFXLayer extends MinigameFXLayer {

	public static final double INDEX_HEIGHT_ABOVE_GROUND = 120;
	
	private final List<Label> indexLabels;
	
	public RunningFXLayer() {
		indexLabels = new ArrayList<>();
	}

	public void start() {
		getChildren().removeAll(indexLabels);
		indexLabels.clear();
	}
	
	public void displayIndexOfLethalObstacle(Runner r) {
		Obstacle o = r.lethalObstacle();
		double gy = Coords.p(Running.get().players().size()).groundY(r.number());
		Label l = new Label(String.valueOf(o.index()));
		l.setFont(Fonts.UI_LARGE);
		l.setLayoutY(gy - INDEX_HEIGHT_ABOVE_GROUND);
		l.layoutXProperty().bind(o.idealXProperty().add(o.getIdealWidth() * .5).subtract(l.widthProperty().multiply(.5)));
		indexLabels.add(l);
		getChildren().add(l);
	}
	
	@Override
	public Running gamePane() {
		return (Running) super.gamePane();
	}
	
}
