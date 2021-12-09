package base;

import java.util.*;

import fxutils.Nodes;
import javafx.scene.layout.Pane;

public abstract class AbstractScaledPane extends Pane implements ScaledPane {

	public static final double DEFAULT_WIDTH = 1920, DEFAULT_HEIGHT = 1080;
	
	protected final List<ImagePane> images;
	
	public AbstractScaledPane() {
		images = new ArrayList<>();
		
	}
	
	@Override
	public boolean add(ImagePane image) {
		if(getChildren().add(image)) {
			images.add(image);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean remove(ImagePane image) {
		if(getChildren().remove(image)) {
			images.remove(image);
			return true;
		}
		return false;
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
	
	protected double wscale() {
		return getWidth() / DEFAULT_WIDTH;
	}
	
	protected double hscale() {
		return getHeight() / DEFAULT_HEIGHT;
	}
	
}
