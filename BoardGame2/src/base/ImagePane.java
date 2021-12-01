package base;

import fxutils.ResizableImage;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class ImagePane extends StackPane {

	private final ResizableImage rimage;
	
	public ImagePane(Image image) {
		rimage = new ResizableImage(image);
		getChildren().add(rimage);
	}
	
}
