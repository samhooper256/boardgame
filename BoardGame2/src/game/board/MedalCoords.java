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
	
	/** [total player count][player number]*/
	private static final MedalCoords[][] STORE = new MedalCoords[Player.maxCount() + 1][];
	
	static {
		for(int pc = 2; pc <= 4; pc++) {
			STORE[pc] = new MedalCoords[pc + 1];
			for(int p = 1; p <= pc; p++) {
				STORE[pc][p] = new MedalCoords(
						scale(p, pc, DEFAULT_WIDTH * .5, DEFAULT_HEIGHT * .4),
						scale(p, pc, DEFAULT_WIDTH * .35, DEFAULT_HEIGHT * .6),
						scale(p, pc, DEFAULT_WIDTH * .5, DEFAULT_HEIGHT * .6),
						scale(p, pc, DEFAULT_WIDTH * .65, DEFAULT_HEIGHT * .6)
				);
			}
		}
	}
	
	public static MedalCoords forPlayer(int playerCount, int player) {
		return STORE[Player.validate(playerCount)][Player.validate(player)];
	}

	private static Point2D scale(int player, int playerCount, double x, double y) {
		if(player < 1 || player > playerCount)
			throw new IllegalArgumentException(String.format("player < playerCount (%d < %d)", player, playerCount));
		if(playerCount == 4) {
			return quad(player, x, y);
		}
		else if(playerCount == 3) {
			if(player <= 2)
				return quad(player, x, y);
			return bottomCentered(x, y);
		}
		else if(playerCount == 2) {
			if(player == 1)
				return quad(1, x, y);
			return quad(4, x, y);
		}
		throw new IllegalArgumentException(String.format("playerCount: %d", playerCount));
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

	private static Point2D bottomCentered(double x, double y) {
		Point2D p = Points.zoom(x, y, .5);
		return new Point2D(p.getX(), p.getY() + QUAD_Y[3]);
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
