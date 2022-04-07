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
	private final MedalLabel[][] medalLabels;
	/** index=player */
	private final ScoreLabel[] scoreLabels;
	private final EventTitle eventTitle;
	private final EventDescription eventDescription;
	private final PressAnyKey pressAnyKey;
	private final Pane customNodes;
	
	public BoardFXLayer() {
		medalLabels = new MedalLabel[Medal.count()][Player.maxCount() + 1];
		scoreLabels = new ScoreLabel[Player.maxCount() + 1];
		for(int mindex = 0; mindex < medalLabels.length; mindex++) {
			for(int player = 1; player < medalLabels[mindex].length; player++) {
				MedalLabel ml = new MedalLabel(0);
				medalLabels[mindex][player] = ml;
				getChildren().add(ml);
				ml.setVisible(false);
			}
		}
		for(int player = 1; player <= Player.maxCount(); player++) {
			MedalCounter c = Player.get(player).medalCounter();
			final int p = player;
			Runnable mccl = () -> {
				medalLabels[Medal.GOLD.index()][p].setValue(c.get(Medal.GOLD));
				medalLabels[Medal.SILVER.index()][p].setValue(c.get(Medal.SILVER));
				medalLabels[Medal.BRONZE.index()][p].setValue(c.get(Medal.BRONZE));
				scoreLabel(p).setValue(c.score());
			};
			c.addChangeListener(mccl);
			scoreLabels[player] = new ScoreLabel();
			getChildren().add(scoreLabel(player));
		}
		eventTitle = new EventTitle();
		eventDescription = new EventDescription();
		pressAnyKey = new PressAnyKey();
		customNodes = new Pane();
		getChildren().addAll(eventTitle, eventDescription, pressAnyKey, customNodes);
	}
	
	public void start() {
		int pc = gamePane().playerCount();
		layoutMedalLabels(pc);
		layoutScoreLabels(pc);
		for(int mindex = 0; mindex < medalLabels.length; mindex++)
			for(int player = 1; player <= Board.maxPlayerCount(); player++)
				medalLabels[mindex][player].setVisible(player <= pc);
		eventTitle.fader().disappear();
		eventDescription.fader().disappear();
		pressAnyKey.fader().disappear();
	}
	
	private void layoutMedalLabels(final int pc) {
		for(int p = 1; p <= pc; p++) {
			MedalCoords mc = MedalCoords.forPlayer(pc, p);
			for(int mindex = 0; mindex < medalLabels.length; mindex++) {
				Point2D coords = mc.medal(mindex);
				MedalLabel ml = medalLabels[mindex][p];
				ml.setLayoutX(coords.getX() + 18);
				ml.setLayoutY(coords.getY() - 18);
			}
		}
	}
	
	private void layoutScoreLabels(final int pc) {
		for(int p = 1; p <= pc; p++) {
			MedalCoords mc = MedalCoords.forPlayer(pc, p);
			ScoreLabel pi = scoreLabel(p);
			Point2D player = mc.player();
			pi.setLayoutX(player.getX() - 32);
			pi.setLayoutY(player.getY() + 24);
		}
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
	
	/** @param player 1-based */
	private ScoreLabel scoreLabel(int player) {
		return scoreLabels[player];
	}
	
	public void eventFinished() {
		customNodes.getChildren().clear();
	}
	
	@Override
	public Board gamePane() {
		return (Board) super.gamePane();
	}
	
}
