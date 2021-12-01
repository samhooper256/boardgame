package base;

import fxutils.*;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

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
		ChangeListener<? super Number> sizeListener = (o, ov, nv) -> Board.updateImageSize(this);
		this.idealWidth.addListener(sizeListener);
		this.idealHeight.addListener(sizeListener);
		this.idealX = new SimpleDoubleProperty(idealX);
		this.idealY = new SimpleDoubleProperty(idealY);
		ChangeListener<? super Number> coordListener = (o, ov, nv) -> Board.updateImageLayoutCoords(this);
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
    
}
