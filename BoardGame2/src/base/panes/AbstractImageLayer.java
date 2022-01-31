package base.panes;

import java.util.*;

import fxutils.Nodes;
import javafx.geometry.*;
import javafx.scene.layout.Pane;
import utils.Screen;

public abstract class AbstractImageLayer extends Pane implements ImageLayer {

	protected final List<ImagePane> images;
	protected GamePane gamePane;
	
	private final List<ImagePane> trash;
	private final List<Runnable> endOfUpdateActions;
	
	/** The {@link #gamePane()} must be set after construction. */
	protected AbstractImageLayer() {
		this(null);
	}
	
	public AbstractImageLayer(GamePane gamePane) {
		images = new ArrayList<>();
		trash = new ArrayList<>();
		endOfUpdateActions = new ArrayList<>();
		this.gamePane = gamePane;
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
	
	/** {@link ImagePane ImagePanes} put in the trash will be {@link #remove(ImagePane) removed} at the end of this
	 * {@link #update(long) update} pulse. */
	public void trash(ImagePane ip) {
		trash.add(ip);
	}
	
	public void addEndOfUpdateAction(Runnable action) {
		endOfUpdateActions.add(action);
	}
	
	@Override
	public final void update(long diff) {
		updatePane(diff);
		for(ImagePane ip : trash)
			remove(ip);
		trash.clear();
		for(Runnable eoua : endOfUpdateActions)
			eoua.run();
		endOfUpdateActions.clear();
	}
	
	public abstract void updatePane(long diff);
	
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
		Nodes.setMaxSize(ip, Screen.wscale() * ip.getIdealWidth(), Screen.hscale() * ip.getIdealHeight());
		Nodes.setMinSize(ip, Screen.wscale() * ip.getIdealWidth(), Screen.hscale() * ip.getIdealHeight());
	}
	
	@Override
	public void updateImageLayoutCoords(ImagePane ip) {
		Nodes.setLayout(ip, Screen.wscale() * ip.getIdealX(), Screen.hscale() * ip.getIdealY());
	}
	
	public Point2D idealToLocal(Point2D ideal) {
		return idealToLocal(ideal.getX(), ideal.getY());
	}
	
	public Point2D idealToLocal(double idealX, double idealY) {
		return new Point2D(idealXToLocal(idealX), idealYToLocal(idealY));
	}

	public double idealXToLocal(double idealX) {
		return idealX * Screen.wscale();
	}
	
	public double idealYToLocal(double idealY) {
		return idealY * Screen.hscale();
	}
	
	public Point2D localToIdeal(Point2D local) {
		return localToIdeal(local.getX(), local.getY());
	}
	
	public Point2D localToIdeal(double localX, double localY) {
		return new Point2D(localXToIdeal(localX), localYToIdeal(localY));
	}

	public double localXToIdeal(double localX) {
		return localX / Screen.wscale();
	}

	public double localYToIdeal(double localY) {
		return localY / Screen.hscale();
	}

	@Override
	public void setGamePane(GamePane gamePane) {
		this.gamePane = gamePane;
	}
	
	@Override
	public GamePane gamePane() {
		return gamePane;
	}
	
}
