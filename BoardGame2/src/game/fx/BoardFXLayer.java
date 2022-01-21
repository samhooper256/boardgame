package game.fx;

import base.panes.*;
import game.*;
import javafx.geometry.Point2D;
import medals.Medal;
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
				Point2D coords = MedalCoords.forPlayer(player).medal(mindex);
				ml.setLayoutX(coords.getX() + 18);
				ml.setLayoutY(coords.getY() - 18);
			}
		}
	}
	
	public void init() {
		
	}
	
	@Override
	public Board gamePane() {
		return (Board) super.gamePane();
	}
	
}
