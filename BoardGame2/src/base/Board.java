package base;

import java.util.*;

import fxutils.*;
import javafx.geometry.Point2D;
import javafx.scene.layout.*;

public class Board extends Pane implements ScaledPane {

	public static final int TILE_COUNT = 36;
	
	private static final double DEFAULT_WIDTH = 1920, DEFAULT_HEIGHT = 1080;
	private static final Board INSTANCE = new Board();
	
	public static Board get() {
		return INSTANCE;
	}
	
	private final List<ImagePane> images;
	
	private List<Tile> tileOrder;
	
	private Board() {
		images = new ArrayList<>();
		tileOrder = generateTileOrder();
		for(int i = 0; i < TILE_COUNT; i++) {
			Tile t = tileOrder.get(i);
			Point2D point = Main.POINTS.get(i);
			t.setIdealCoords(point);
			add(t);
		}
	}
	
	@Override
	public boolean add(ImagePane image) {
		if(getChildren().add(image)) {
			images.add(image);
			return true;
		}
		return false;
	}
	
	public void clearAllImages() {
		getChildren().clear();
		images.clear();
	}
	
	@Override
	public List<ImagePane> imagesUnmodifiable() {
		return Collections.unmodifiableList(images);
	}
	
	@Override
	public void resize(double width, double height) {
		super.resize(width, height);
		relayout();
	}
	
	private void relayout() {
		for(ImagePane ip : images) {
			updateImageSize(ip);
			updateImageLayoutCoords(ip);
		}
	}

	@Override
	public void updateImageSize(ImagePane ip) {
		Nodes.setMaxSize(ip, wscale() * ip.getIdealWidth(), hscale() * ip.getIdealHeight());
	}
	
	@Override
	public void updateImageLayoutCoords(ImagePane ip) {
		Nodes.setLayout(ip, wscale() * ip.getIdealX(), hscale() * ip.getIdealY());
	}
	
	private double wscale() {
		return getWidth() / DEFAULT_WIDTH;
	}
	
	private double hscale() {
		return getHeight() / DEFAULT_HEIGHT;
	}
	
	private List<Tile> generateTileOrder() {
		List<Tile> order = new ArrayList<>(TILE_COUNT);
		order.add(new StartTile());
		for(TileSection section : TileSection.ORDER)
			order.addAll(section.randomOrder());
		return order;
	}
	
}
