package base;

import java.util.*;

import fxutils.Nodes;
import javafx.geometry.*;
import javafx.scene.layout.Pane;

public abstract class AbstractScaledPane extends Pane implements ScaledPane {

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
	public boolean imagesIntersect(ImagePane ip1, ImagePane ip2) {
		return idealize(ip1.getBoundsInParent())
				.intersects(idealize(ip2.getBoundsInParent()));
	}

	public BoundingBox idealize(Bounds bounds) {
		return new BoundingBox(
				localXToIdeal(bounds.getMinX()),
				localYToIdeal(bounds.getMinY()),
				localXToIdeal(bounds.getWidth()),
				localYToIdeal(bounds.getHeight())
		);
	}
	
	@Override
	public void updateImageSize(ImagePane ip) {
		Nodes.setMaxSize(ip, wscale() * ip.getIdealWidth(), hscale() * ip.getIdealHeight());
	}
	
	@Override
	public void updateImageLayoutCoords(ImagePane ip) {
		Nodes.setLayout(ip, wscale() * ip.getIdealX(), hscale() * ip.getIdealY());
	}
	
	public Point2D idealToLocal(Point2D ideal) {
		return idealToLocal(ideal.getX(), ideal.getY());
	}
	
	public Point2D idealToLocal(double idealX, double idealY) {
		return new Point2D(idealXToLocal(idealX), idealYToLocal(idealY));
	}

	public double idealXToLocal(double idealX) {
		return idealX * wscale();
	}
	
	public double idealYToLocal(double idealY) {
		return idealY * hscale();
	}
	
	public Point2D localToIdeal(Point2D local) {
		return localToIdeal(local.getX(), local.getY());
	}
	
	public Point2D localToIdeal(double localX, double localY) {
		return new Point2D(localXToIdeal(localX), localYToIdeal(localY));
	}

	public double localXToIdeal(double localX) {
		return localX / wscale();
	}

	public double localYToIdeal(double localY) {
		return localY / hscale();
	}
	
}
