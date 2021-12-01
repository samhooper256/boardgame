package base;

import java.util.*;

import fxutils.*;
import javafx.scene.layout.*;

public class Board extends Pane implements ScaledPane {

	private static final double DEFAULT_WIDTH = 1920, DEFAULT_HEIGHT = 1080;
	private static final Board INSTANCE = new Board();
	
	public static Board get() {
		return INSTANCE;
	}
	
	private final List<ImagePane> images;
	
	private Board() {
		images = new ArrayList<>();
		ImagePane im = new ImagePane(Images.TILE);
		im.setIdealCoords(200, 200);
		add(im);
	}
	
	public boolean add(ImagePane image) {
		if(getChildren().add(image)) {
			images.add(image);
			return true;
		}
		return false;
	}
	
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

	public void updateImageSize(ImagePane ip) {
		Nodes.setMaxSize(ip, wscale() * ip.getIdealWidth(), hscale() * ip.getIdealHeight());
	}
	
	public void updateImageLayoutCoords(ImagePane ip) {
		Nodes.setLayout(ip, wscale() * ip.getIdealX(), hscale() * ip.getIdealY());
	}
	
	private double wscale() {
		return getWidth() / DEFAULT_WIDTH;
	}
	
	private double hscale() {
		return getHeight() / DEFAULT_HEIGHT;
	}
	
}
