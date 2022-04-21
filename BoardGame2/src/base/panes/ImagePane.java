package base.panes;

import fxutils.*;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/** <p>A JavaFX {@link Node} used for displaying an {@link Image} in a {@link ImageLayer}. All
 * {@link ImagePane ImagePanes} have {@link #getIdealCoords() ideal} coordinates that assume a screen width of
 * {@link ImageLayer#DEFAULT_WIDTH} and a screen height of {@link ImageLayer#DEFAULT_HEIGHT} with {@code (0, 0)} at the
 * top-left corner.</p>
 * 
 * <p>A separate "ideal" coordinate system allows {@link ImagePane ImagePanes} to be laid out independent of how the
 * user has the screen resized. In other words, it allows all {@link ImagePane ImagePanes} on the screen to scale
 * smoothly and uniformly if the user resizes the game window.</p>
 * */
public class ImagePane extends StackPane {

	private final ResizableImage rimage;
	
	private final DoubleProperty idealWidth, idealHeight, idealX, idealY;
	
	public ImagePane(Image image) {
		this(image, image.getWidth(), image.getHeight());
	}
	
	public ImagePane(Image image, double idealWidth, double idealHeight) {
		this(image, idealWidth, idealHeight, 0, 0);
	}
	
	public ImagePane(Image image, double idealWidth, double idealHeight, double idealX, double idealY) {
		rimage = new ResizableImage(image);
		this.idealWidth = new SimpleDoubleProperty(idealWidth);
		this.idealHeight = new SimpleDoubleProperty(idealHeight);
		ChangeListener<? super Number> sizeListener = (o, ov, nv) -> {
			ImageLayer sp = getImageLayer();
			if(sp != null)
				sp.updateImageSize(this);
		};
		this.idealWidth.addListener(sizeListener);
		this.idealHeight.addListener(sizeListener);
		this.idealX = new SimpleDoubleProperty(idealX);
		this.idealY = new SimpleDoubleProperty(idealY);
		ChangeListener<? super Number> coordListener = (o, ov, nv) -> {
			ImageLayer sp = getImageLayer();
			if(sp != null)
				sp.updateImageLayoutCoords(this);
		};
		this.idealX.addListener(coordListener);
		this.idealY.addListener(coordListener);
		Nodes.setMaxSize(this, idealWidth, idealHeight);
		getChildren().add(rimage);
	}
	
	public DoubleProperty idealWidthProperty() {
		return idealWidth;
	}
	
	public DoubleProperty idealHeightProperty() {
		return idealHeight;
	}
	
	public double getIdealWidth() {
    	return idealWidthProperty().get();
    }
    
    public double getIdealHeight() {
    	return idealHeightProperty().get();
    }
    
    public void setIdealWidth(double idealWidth) {
    	idealWidthProperty().set(idealWidth);
    }
    
    public void setIdealHeight(double idealHeight) {
    	idealHeightProperty().set(idealHeight);
    }

    /** The width is set before the height. */
    public void setIdealSize(double idealWidth, double idealHeight) {
    	setIdealWidth(idealWidth);
    	setIdealHeight(idealHeight);
    }
    
    /** The width is set before the height. */
    public void setIdealSize(double idealSize) {
    	setIdealSize(idealSize, idealSize);
    }
    
    public DoubleProperty idealXProperty() {
    	return idealX;
    }
    
    public DoubleProperty idealYProperty() {
    	return idealY;
    }
    
    public double getIdealX() {
    	return idealXProperty().get();
    }
    
    public double getIdealY() {
    	return idealYProperty().get();
    }
    
    public void setIdealX(double idealX) {
    	idealXProperty().set(idealX);
    }
    
    public void setIdealRightX(double idealRightX) {
    	setIdealX(idealRightX - getIdealWidth());
    }
    
    /** Equivalent to {@code setIdealX(getIdealX() + offset)}.*/
    public void shiftIdealX(double offset) {
    	setIdealX(getIdealX() + offset);
    }
    
    public void setIdealY(double idealY) {
    	idealYProperty().set(idealY);
    }

    public void setIdealBottomY(double idealBottomY) {
    	setIdealY(idealBottomY - getIdealHeight());
    }
    
    /** Equivalent to {@code setIdealY(getIdealY() + offset)}. */
    public void shiftIdealY(double offset) {
    	setIdealY(getIdealY() + offset);
    }
    
    /** The x coordinate is set before the y coordinate. */
    public void setIdealCoords(double idealX, double idealY) {
    	setIdealX(idealX);
    	setIdealY(idealY);
    }
    
    public Point2D getIdealCoords() {
    	return new Point2D(getIdealX(), getIdealY());
    }
    
    public Point2D getIdealCenter() {
    	return new Point2D(getIdealCenterX(), getIdealCenterY());
    }
    
    public double getIdealCenterX() {
    	return getIdealX() + getIdealWidth() * .5;
    }
    
    public double getIdealCenterY() {
    	return getIdealY() + getIdealHeight() * .5;
    }
    
    public void setIdealCenter(Point2D idealCenter) {
    	setIdealCenter(idealCenter.getX(), idealCenter.getY());
    }
    
    public void setIdealCenter(double idealXCenter, double idealYCenter) {
    	setIdealCenterX(idealXCenter);
    	setIdealCenterY(idealYCenter);
    }

    public void setIdealCenterX(double idealXCenter) {
    	setIdealX(idealXCenter - getIdealWidth() * .5);
    }
    
    public void setIdealCenterY(double idealYCenter) {
    	setIdealY(idealYCenter - getIdealHeight() * .5);
    }
    
    /** The x coordinate is set before the y coordinate. */
    public void setIdealCoords(Point2D idealCoords) {
    	setIdealCoords(idealCoords.getX(), idealCoords.getY());
    }
 
    public double getIdealRightX() {
    	return getIdealX() + getIdealWidth();
    }
    
    public double getIdealBottomY() {
    	return getIdealY() + getIdealHeight();
    }
    
    /** Equivalent to {@link #setIdealCoords(double, double)}. */
    public void setIdealTopLeftCorner(double x, double y) {
    	setIdealCoords(x, y);
    }
    
    public void setIdealTopLeftCorner(Point2D point) {
    	setIdealCoords(point);
    }
    
    /** Equivalent to {@link #getIdealCoords()}*/
    public Point2D getIdealTopLeftCorner() {
    	return getIdealCoords();
    }
    
    public void setIdealTopRightCorner(double x, double y) {
    	setIdealRightX(x);
    	setIdealY(y);
    }
    
    public void setIdealTopRightCorner(Point2D point) {
    	setIdealTopRightCorner(point.getX(), point.getY());
    }
    
    public Point2D getIdealTopRightCorner() {
    	return new Point2D(getIdealRightX(), getIdealY());
    }
    
    public void setIdealBottomLeftCorner(double x, double y) {
    	setIdealX(x);
    	setIdealBottomY(y);
    }
    
    public void setIdealBottomLeftCorner(Point2D point) {
    	setIdealBottomLeftCorner(point.getX(), point.getY());
    }
    
    public Point2D getIdealBottomLeftCorner() {
    	return new Point2D(getIdealX(), getIdealBottomY());
    }
    
    public void setIdealBottomRightCorner(double x, double y) {
    	setIdealRightX(x);
    	setIdealBottomY(y);
    }
    
    public void setIdealBottomRightCorner(Point2D point) {
    	setIdealBottomRightCorner(point.getX(), point.getY());
    }
    
    public Point2D getIdealBottomRightCorner() {
    	return new Point2D(getIdealRightX(), getIdealBottomY());
    }
    
    public void multiplyIdealSize(double scale) {
    	setIdealWidth(getIdealWidth() * scale);
    	setIdealHeight(getIdealHeight() * scale);
    }
    
    public ResizableImage rimage() {
    	return rimage;
    }
    
    public Image image() {
    	return rimage().getImage();
    }

    public void setImage(Image image) {
    	rimage().setImage(image);
    }
    
    /** Returns the {@link ImageLayer} that this {@link ImagePane} is contained within. Returns {@code null} if this
     * {@link ImagePane} is not currently within a {@link ImageLayer}.*/
    public ImageLayer getImageLayer() {
    	Parent p = getParent();
    	while(p != null && !(p instanceof ImageLayer))
    		p = p.getParent();
    	return (ImageLayer) p;
    }
    
}
