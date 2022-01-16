package base.panes;

import fxutils.*;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/** <p>A JavaFX {@link Node} used for displaying an {@link Image} in a {@link ScaledPane}. All
 * {@link ImagePane ImagePanes} have {@link #getIdealCoords() ideal} coordinates that assume a screen width of
 * {@link ScaledPane#DEFAULT_WIDTH} and a screen height of {@link ScaledPane#DEFAULT_HEIGHT} with {@code (0, 0)} at the
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
		ChangeListener<? super Number> sizeListener = (o, ov, nv) -> getScaledPane().updateImageSize(this);
		this.idealWidth.addListener(sizeListener);
		this.idealHeight.addListener(sizeListener);
		this.idealX = new SimpleDoubleProperty(idealX);
		this.idealY = new SimpleDoubleProperty(idealY);
		ChangeListener<? super Number> coordListener = (o, ov, nv) -> {
			ScaledPane sp = getScaledPane();
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
    
    public void setIdealY(double idealY) {
    	idealYProperty().set(idealY);
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
    	return getIdealX() + getIdealWidth() / 2;
    }
    
    public double getIdealCenterY() {
    	return getIdealY() + getIdealHeight() / 2;
    }
    
    public void setIdealCenter(Point2D idealCenter) {
    	setIdealCenter(idealCenter.getX(), idealCenter.getY());
    }
    
    public void setIdealCenter(double idealXCenter, double idealYCenter) {
    	setIdealCoords(idealXCenter - getIdealWidth() / 2, idealYCenter - getIdealHeight() / 2);
    }
    
    /** The x coordinate is set before the y coordinate. */
    public void setIdealCoords(Point2D idealCoords) {
    	setIdealCoords(idealCoords.getX(), idealCoords.getY());
    }
 
    public boolean isOffscreen() {
    	return 	getIdealX() < -getIdealWidth() || getIdealX() > ScaledPane.DEFAULT_WIDTH ||
    			getIdealY() < -getIdealHeight() || getIdealY() > ScaledPane.DEFAULT_HEIGHT;
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
    
    /** Returns the {@link ScaledPane} that this {@link ImagePane} is contained within. Returns {@code null} if this
     * {@link ImagePane} is not currently within a {@link ScaledPane}.*/
    public ScaledPane getScaledPane() {
    	Parent p = getParent();
    	while(p != null && !(p instanceof ScaledPane))
    		p = p.getParent();
    	return (ScaledPane) p;
    }
    
}
