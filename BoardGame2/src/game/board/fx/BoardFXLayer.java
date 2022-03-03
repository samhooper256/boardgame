package game.board.fx;

import base.panes.*;
import events.*;
import fxutils.Fadeable;
import game.board.*;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import medals.*;
import players.Player;

public class BoardFXLayer extends FXLayer {
	
	/** row=medal, col=player*/
	private final MedalLabel[][] medals = new MedalLabel[Medal.count()][Player.maxCount() + 1];
	private final EventTitle eventTitle;
	private final EventDescription eventDescription;
	private final PressAnyKey pressAnyKey;
	
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
		getChildren().addAll(eventTitle, eventDescription, pressAnyKey);
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
		getChildren().addAll(event.fxNodes());
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
	
	@Override
	public Board gamePane() {
		return (Board) super.gamePane();
	}
	
}
