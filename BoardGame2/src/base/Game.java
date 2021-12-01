package base;

import java.util.*;

import fxutils.ResizableImage;
import javafx.scene.layout.*;

public class Game extends Pane {

	private final List<ImagePane> images;
	
	private static final double DEFAULT_WIDTH = 1920, DEFAULT_HEIGHT = 1080;
	
	public Game() {
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
		// TODO Auto-generated method stub
		super.resize(width, height);
		System.out.printf("Resized to: %f, %f%n", width, height);
		double hscale = height / DEFAULT_HEIGHT, wscale = width / DEFAULT_WIDTH;
		for(ImagePane im : images) {
//			im.resize(im.desiredWidth() * wscale, im.desiredHeight() * hscale);
			im.setFitWidth(im.desiredWidth() * wscale);
		}
	}
	
}
