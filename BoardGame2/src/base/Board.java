package base;

import java.util.*;

import javafx.geometry.Point2D;
import tiles.*;

public class Board extends AbstractScaledPane implements ScaledPane {

	public static final int TILE_COUNT = 36;
	
	private static final Board INSTANCE = new Board();
	
	public static Board get() {
		return INSTANCE;
	}
	
	private final Die die;
	
	private List<Tile> tileOrder;
	
	private Board() {
		die = new Die();
		die.setIdealCoords(DEFAULT_WIDTH / 2 - die.getIdealWidth() / 2, DEFAULT_HEIGHT / 2 - die.getIdealHeight() / 2);
		tileOrder = generateTileOrder();
		for(int i = 0; i < TILE_COUNT; i++) {
			Tile t = tileOrder.get(i);
			Point2D point = Main.POINTS.get(i);
			t.setIdealCoords(point);
			add(t);
		}
		add(die);
	}
	
	private List<Tile> generateTileOrder() {
		List<Tile> order = new ArrayList<>(TILE_COUNT);
		order.add(new StartTile());
		for(TileSection section : TileSection.ORDER)
			order.addAll(section.randomOrder());
		return order;
	}
	
	public void clearAllImages() {
		getChildren().clear();
		images.clear();
	}
	
}
