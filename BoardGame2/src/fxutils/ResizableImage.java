package fxutils;

import javafx.scene.image.*;

/**
 * @author Sam Hooper
 *
 */
public final class ResizableImage extends ImageView {
	
	private static final double MIN_SIZE = 2, MAX_SIZE = 16384;
    
    public ResizableImage(Image im) {
    	super(im);
    	setPreserveRatio(false);
    }

    @Override
    public double minWidth(double height) {
    	return MIN_SIZE;
    }

    @Override
    public double prefWidth(double height) {
    	Image image = getImage();
        if(image == null)
        	return minWidth(height);
        return image.getWidth();
    }

    @Override
    public double maxWidth(double height) {
    	return MAX_SIZE;
    }

    @Override
    public double minHeight(double width) {
    	return MIN_SIZE;
    }
    
    @Override
    public double maxHeight(double width) {
    	return MAX_SIZE;
    }

    @Override
    public double prefHeight(double width) {
        Image image = getImage();
        if(image == null)
        	return minHeight(width);
        return image.getHeight();
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public void resize(double width, double height) {
        setFitWidth(width);
        setFitHeight(height);
    }
    
}