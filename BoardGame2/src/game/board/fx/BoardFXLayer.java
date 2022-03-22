package game.board.fx;

import java.util.List;

import base.panes.*;
import events.*;
import fxutils.Fadeable;
import game.board.*;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import medals.*;
import players.Player;

public class BoardFXLayer extends FXLayer {
	
	/** row=medal, col=player*/
	private final MedalLabel[][] medals = new MedalLabel[Medal.count()][Player.maxCount() + 1];
	private final EventTitle eventTitle;
	private final EventDescription eventDescription;
	private final PressAnyKey pressAnyKey;
	private final Pane customNodes;
	
	public BoardFXLayer() {
		for(int mindex = 0; mindex < medals.length; mindex++) {
			for(int player = 1; player < medals[mindex].length; player++) {
				MedalLabel ml = new MedalLabel(0);
				medals[mindex][player] = ml;
				getChildren().add(ml);
				ml.setVisible(false);
				Point2D coords = MedalCoords.forPlayer(player).medal(mindex);
				ml.setLayoutX(coords.getX() + 18);
				ml.setLayoutY(coords.getY() - 18);
			}
		}
		for(int player = 1; player <= Player.maxCount(); player++) {
			MedalCounter c = Player.get(player).medalCounter();
			final int p = player;
			Runnable changeListener = () -> {
				medals[Medal.GOLD.index()][p].setValue(c.get(Medal.GOLD));
				medals[Medal.SILVER.index()][p].setValue(c.get(Medal.SILVER));
				medals[Medal.BRONZE.index()][p].setValue(c.get(Medal.BRONZE));
			};
			c.addChangeListener(changeListener);
		}
		eventTitle = new EventTitle();
		eventDescription = new EventDescription();
		pressAnyKey = new PressAnyKey();
		customNodes = new Pane();
		getChildren().addAll(eventTitle, eventDescription, pressAnyKey, customNodes);
	}
	
	public void start() {
		for(int mindex = 0; mindex < medals.length; mindex++)
			for(int player = 1; player <= Board.maxPlayerCount(); player++)
				medals[mindex][player].setVisible(player <= gamePane().playerCount());
		eventTitle.fader().disappear();
		eventDescription.fader().disappear();
		pressAnyKey.fader().disappear();
	}
	
	public void showSimpleTextEvent(SimpleTextEvent event) {
		eventTitle.setText(event.name());
		eventDescription.setText(event.description());
		eventTitle.fader().fadeIn();
		eventDescription.fader().fadeIn();
		pressAnyKey.fader().fadeIn();
	}
	
	public void showComplexEvent(ComplexEvent event) {
		eventTitle.setText(event.name());
		eventTitle.fader().fadeIn();
		List<Node> eventNodes = event.fxNodes();
		for(Node n : eventNodes) {
			if(n instanceof Fadeable)
				((Fadeable) n).fader().fadeIn();
			else
				n.setVisible(true);
		}
		customNodes.getChildren().addAll(eventNodes);
	}
	
	/** Begins fading out the {@link SimpleTextEvent}-related materials. */
	public void demandEventFinish() {
		eventTitle.fader().fadeOutAndHide();
		Event event = Board.get().currentEvent();
		if(event instanceof SimpleTextEvent) {
			eventDescription.fader().fadeOutAndHide();
			pressAnyKey.fader().fadeOutAndHide();
		}
		else {
			ComplexEvent ce = (ComplexEvent) event;
			for(Node node : ce.fxNodes())
				if(node instanceof Fadeable)
					((Fadeable) node).fader().fadeOutAndHide();
		}
	}
	
	public void eventFinished() {
		customNodes.getChildren().clear();
	}
	
	@Override
	public Board gamePane() {
		return (Board) super.gamePane();
	}
	
}
