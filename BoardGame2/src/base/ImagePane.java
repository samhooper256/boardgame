package base;

import fxutils.*;
import javafx.beans.property.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class ImagePane extends StackPane {

	private final ResizableImage rimage;
	
	private final double idealWidth, idealHeight;
	private final DoubleProperty idealX;
	private final DoubleProperty idealY;
	
	public ImagePane(Image image) {
		this(image, image.getWidth(), image.getHeight());
	}
	
	public ImagePane(Image image, double idealWidth, double idealHeight) {
		this(image, idealWidth, idealHeight, 0, 0);
	}
	
	public ImagePane(Image image, double idealWidth, double idealHeight, double idealX, double idealY) {
		rimage = new ResizableImage(image);
		this.idealWidth = idealWidth;
		this.idealHeight = idealHeight;
		Nodes.setMaxSize(this, 2 * idealWidth, idealHeight);
		this.idealX = new SimpleDoubleProperty(idealX);
		this.idealY = new SimpleDoubleProperty(idealY);
		getChildren().add(rimage);
	}
	
	public double idealWidth() {
    	return idealWidth;
    }
    
    public double idealHeight() {
    	return idealHeight;
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
