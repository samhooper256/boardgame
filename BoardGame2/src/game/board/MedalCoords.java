package game.board;

import javafx.geometry.Point2D;
import medals.Medal;
import players.Player;
import utils.Points;

import static game.MainScene.DEFAULT_HEIGHT;
import static game.MainScene.DEFAULT_WIDTH;
import static medals.Medal.*;

/** All {@link Point2D points} are centers. */
public class MedalCoords {
	
	private static final double ZOOM_FACTOR = 1; //TODO remove this if I never end up using it?
	
	private static final double[]
			QUAD_X = {0, -DEFAULT_WIDTH * .25, DEFAULT_WIDTH * .25, -DEFAULT_WIDTH * .25, DEFAULT_WIDTH * .25},
			QUAD_Y = {0, -DEFAULT_HEIGHT * .25, -DEFAULT_HEIGHT * .25, DEFAULT_HEIGHT * .25, DEFAULT_HEIGHT * .25};
	
	private static final MedalCoords[][] STORE = new MedalCoords[Player.maxCount() + 1][];
	
	static {
		STORE[Player.maxCount()] = new MedalCoords[Player.maxCount() + 1];
		for(int i = 1; i <= Player.maxCount(); i++) {
			STORE[Player.maxCount()][i] = new MedalCoords(
					quad(i, DEFAULT_WIDTH * .5, DEFAULT_HEIGHT * .4),
					quad(i, DEFAULT_WIDTH * .35, DEFAULT_HEIGHT * .6),
					quad(i, DEFAULT_WIDTH * .5, DEFAULT_HEIGHT * .6),
					quad(i, DEFAULT_WIDTH * .65, DEFAULT_HEIGHT * .6)
			);
		}
	}
	
	//TODO REMOVE THIS METHOD
	public static MedalCoords forPlayer(int player) {
		return forPlayer(player, Player.maxCount());
	}
	
	public static MedalCoords forPlayer(int player, int playerCount) {
		return STORE[Player.validate(playerCount)][Player.validate(player)];
	}
	
	/** Scales the given coordinates (which are in terms of the entire screen) down to the given quadrant. Quadrants:
	 * 1 | 2
	 * -----
	 * 3 | 4
	 * */
	private static Point2D quad(int quad, double x, double y) {
		Point2D p = Points.zoom(x, y, .5);
		return new Point2D(p.getX() + QUAD_X[quad], p.getY() + QUAD_Y[quad]);
	}
	
	private final Point2D player;
	private final Point2D[] medals;
	
	/** Coords are zoomed by the {@link #ZOOM_FACTOR}. */
	private MedalCoords(Point2D player, Point2D gold, Point2D silver, Point2D bronze) {
		this.player = Points.zoom(player, ZOOM_FACTOR);
		medals = new Point2D[Medal.count()];
		medals[0] = Points.zoom(gold, ZOOM_FACTOR);
		medals[1] = Points.zoom(silver, ZOOM_FACTOR);
		medals[2] = Points.zoom(bronze, ZOOM_FACTOR);
	}
	
	public Point2D player() {
		return player;
	}
	
	public Point2D gold() {
		return medal(GOLD.index());
	}
	
	public Point2D silver() {
		return medal(SILVER.index());
	}
	
	public Point2D bronze() {
		return medal(BRONZE.index());
	}
	
	public Point2D medal(int index) {
		return medals[index];
	}
	
}
