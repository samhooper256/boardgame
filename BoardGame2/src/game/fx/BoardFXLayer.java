package game.fx;

import base.panes.*;
import game.*;
import javafx.geometry.Point2D;
import medals.*;
import players.Player;

public class BoardFXLayer extends FXLayer {
	
	/** row=medal, col=player*/
	private final MedalLabel[][] medals = new MedalLabel[Medal.count()][Player.maxCount() + 1];
	
	
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
	}
	
	public void init() {
		for(int mindex = 0; mindex < medals.length; mindex++)
			for(int player = 1; player < Board.get().playerCount(); player++)
				medals[mindex][player].setVisible(true);
	}
	
	@Override
	public Board gamePane() {
		return (Board) super.gamePane();
	}
	
}
