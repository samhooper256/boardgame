package game;

import javafx.geometry.Point2D;
import players.Player;
import utils.Points;

import static base.panes.ScaledPane.DEFAULT_WIDTH;
import static base.panes.ScaledPane.DEFAULT_HEIGHT;

/** All {@link Point2D points} are centers. */
public class MedalCoords {
	
	private static final double ZOOM_FACTOR = 1; //TODO remove this if I never end up using it?
	
	private static final double[]
			QUAD_X ={0, -DEFAULT_WIDTH * .25, DEFAULT_WIDTH * .25, -DEFAULT_WIDTH * .25, DEFAULT_WIDTH * .25},
			QUAD_Y = {0, -DEFAULT_HEIGHT * .25, -DEFAULT_HEIGHT * .25, DEFAULT_HEIGHT * .25, DEFAULT_HEIGHT * .25};
	
	private static final MedalCoords[] STORE = new MedalCoords[Player.maxCount() + 1];
	
	static {
		for(int i = 1; i <= Player.maxCount(); i++) {
			STORE[i] = new MedalCoords(
					quad(i, DEFAULT_WIDTH * .5, DEFAULT_HEIGHT * .4),
					quad(i, DEFAULT_WIDTH * .35, DEFAULT_HEIGHT * .6),
					quad(i, DEFAULT_WIDTH * .5, DEFAULT_HEIGHT * .6),
					quad(i, DEFAULT_WIDTH * .65, DEFAULT_HEIGHT * .6)
			);
		}
	}
	public static MedalCoords forPlayer(int player) {
		return STORE[Player.validate(player)];
	}
	
	/** Scaled the given coordinates (which are in terms of the entire screen) down to the given quadrant. Quadrants:
	 * 1 | 2
	 * -----
	 * 3 | 4
	 * */
	private static Point2D quad(int quad, double x, double y) {
		Point2D p = Points.zoom(x, y, .5);
		return new Point2D(p.getX() + QUAD_X[quad], p.getY() + QUAD_Y[quad]);
	}
	
	private final Point2D player, gold, silver, bronze;

	/** Coords are zoomed by the {@link #ZOOM_FACTOR}. */
	private MedalCoords(Point2D player, Point2D gold, Point2D silver, Point2D bronze) {
		this.player = Points.zoom(player, ZOOM_FACTOR);
		this.gold = Points.zoom(gold, ZOOM_FACTOR);
		this.silver = Points.zoom(silver, ZOOM_FACTOR);
		this.bronze = Points.zoom(bronze, ZOOM_FACTOR);
	}
	
	public Point2D player() {
		return player;
	}
	
	public Point2D gold() {
		return gold;
	}
	
	public Point2D silver() {
		return silver;
	}
	
	public Point2D bronze() {
		return bronze;
	}
	
}
