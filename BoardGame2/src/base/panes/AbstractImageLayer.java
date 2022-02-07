package base.panes;

import java.util.*;

import fxutils.Nodes;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import utils.Screen;

/** It is crucial that {@code getChildren().add(...)} never be used to add any {@link Node Nodes}, even
 * {@link ImagePane ImagePanes}, to this {@link AbstractImageLayer} or heap pollution may occur.
 * The {@link #add(ImagePane)} method must be used instead. */
public abstract class AbstractImageLayer extends Pane implements ImageLayer {

	private final class PacketImpl implements Packet {

		private final int index;
		
		private int low, high;
		
		public PacketImpl(int index) {
			this.index = index;
			low = high = 0;
		}
		
		@Override
		public List<ImagePane> images() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int index() {
			return index;
		}
		
		/** Inclusive. */
		public int low() {
			return low;
		}
		
		/** Exclusive. */
		public int high() {
			return high;
		}
		
		public void setLow(int low) {
			this.low = low;
		}
		
		public void setHigh(int high) {
			this.high = high;
		}
		
		public void set(int low, int high) {
			setLow(low);
			setHigh(high);
		}
	
		public void shift(int distance) {
			setLow(low() + distance);
			setHigh(high() + distance);
		}
		
	}
	
	private final List<PacketImpl> packets;
	
	private final List<ImagePane> trash;
	private final List<Runnable> endOfUpdateActions;
	
	private GamePane gamePane;
	
	/** The {@link #gamePane()} must be set after construction. */
	protected AbstractImageLayer() {
		this(null);
	}
	
	public AbstractImageLayer(GamePane gamePane) {
		packets = new ArrayList<>();
		packets.add(new PacketImpl(0));
		trash = new ArrayList<>();
		endOfUpdateActions = new ArrayList<>();
		this.gamePane = gamePane;
	}
	
	@Override
	public boolean add(ImagePane image) {
		PacketImpl bottom = getPacket(0);
		if(getChildren().subList(bottom.low(), bottom.high()).add(image)) {
			bottom.setHigh(bottom.high() + 1);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean remove(ImagePane image) {
		for(int i = 0; i < packets.size(); i++) {
			if(tryRemove(image, packets.get(i))) {
				for(int k = i + 1; k < packets.size(); i++)
					packets.get(k).shift(-1);
				return true;
			}
		}
		return false;
	}
	
	private boolean tryRemove(ImagePane image, PacketImpl packet) {
		if(getChildren().subList(packet.low(), packet.high()).remove(image)) {
			packet.setHigh(packet.high() - 1);
			return true;
		}
		return false;
	}
	
	@Override
	public void clearAllImages() {
		getChildren().clear();
		for(PacketImpl p : packets)
			p.set(0, 0);
	}
	
	@Override
	public PacketImpl getPacket(int index) {
		return packets.get(index);
	}
	
	@Override
	public List<Packet> packetsUnmodifiable() {
		return Collections.unmodifiableList(packets);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ImagePane> imagesUnmodifiable() {
		return Collections.unmodifiableList((List<ImagePane>) (List<?>) getChildren());
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
		for(Node n : getChildren()) {
			ImagePane ip = (ImagePane) n;
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
