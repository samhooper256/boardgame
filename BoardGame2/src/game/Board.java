package game;

import java.util.*;

import base.*;
import javafx.geometry.Point2D;
import players.Player;
import tiles.*;

public class Board extends AbstractScaledPane implements ScaledPane {

	public static final int TILE_COUNT = 36;
	
	private static final Board INSTANCE = new Board(1);
	
	public static Board get() {
		return INSTANCE;
	}
	
	private final Die die;
	
	private List<Tile> tileOrder;
	private int playerCount, turn;
	
	private Board(int playerCount) {
		this.playerCount = playerCount;
		turn = 1;
		die = new Die();
		die.setIdealCoords(DEFAULT_WIDTH / 2 - die.getIdealWidth() / 2, DEFAULT_HEIGHT / 2 - die.getIdealHeight() / 2);
		tileOrder = generateTileOrder();
		placeTiles();
		placePlayers();
		add(die);
	}

	private void placeTiles() {
		for(int i = 0; i < TILE_COUNT; i++) {
			Tile t = tileOrder.get(i);
			Point2D point = Main.POINTS.get(i);
			t.setIdealCoords(point);
			add(t);
		}
	}

	private void placePlayers() {
		for(int i = 1; i <= playerCount; i++) {
			add(Player.get(i));
			movePlayer(Player.get(i), tileAt(0));
		}
	}
	
	private List<Tile> generateTileOrder() {
		List<Tile> order = new ArrayList<>(TILE_COUNT);
		order.add(StartTile.get());
		for(TileSection section : TileSection.ORDER)
			order.addAll(section.randomOrder());
		return order;
	}
	
	public void clearAllImages() {
		getChildren().clear();
		images.clear();
	}
	
	/** The {@link Tile} at the given 0-based index, where the tile at index 0 is the start tile. */
	public Tile tileAt(int index) {
		return tileOrder.get(index);
	}

	/** Moves the given {@link Player} to the given {@link Tile}.*/
	public void movePlayer(Player p, Tile t) {
		p.setIdealCenter(t.getIdealCenterX(), t.getIdealCenterY());
	}
	
	public int turn() {
		return turn;
	}
	
}