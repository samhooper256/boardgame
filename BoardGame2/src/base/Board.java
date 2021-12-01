package base;

import java.util.*;

import fxutils.*;
import javafx.scene.layout.*;

public class Board extends Pane {

	private static final double DEFAULT_WIDTH = 1920, DEFAULT_HEIGHT = 1080;
	
	private final List<ImagePane> images;
	
	public Board() {
		images = new ArrayList<>();
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
		for(ImagePane im : images) {
			Nodes.setMaxSize(im, wscale() * im.idealWidth(), hscale() * im.idealHeight());
			updateImageLayoutCoords(im);
		}
	}
	
	public void updateImageLayoutCoords(ImagePane im) {
		Nodes.setLayout(im, wscale() * im.getIdealX(), hscale() * im.getIdealY());
	}
	
	private double wscale() {
		return getWidth() / DEFAULT_WIDTH;
	}
	
	private double hscale() {
		return getHeight() / DEFAULT_HEIGHT;
	}
	
}
