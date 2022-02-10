package temp;

import fxutils.Images;
import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Bane extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		Image image = Images.FENCE;
		ImageView iv = new ImageView(image);
		
		Rectangle clip = new Rectangle(image.getWidth() * .5, image.getHeight());
		int x1 = 50, x2 = 0, x3 = 100;
		Polygon triangle = new Polygon(x1, 0, 0, image.getHeight(), 100, image.getHeight());
		iv.setClip(triangle);
		
		Transition t = new Transition() {
			
			{
				setCycleDuration(Duration.seconds(2));
				setInterpolator(Interpolator.LINEAR);
			}
			
			@Override
			protected void interpolate(double frac) {
				triangle.setLayoutX(frac * image.getWidth() - 50);
//				clip.setWidth(frac * image.getWidth());
			}
			
		};
		
		Pane pane = new Pane(iv);
		iv.setLayoutY(100);
		
		Scene scene = new Scene(pane);
		
		stage.setScene(scene);
		stage.show();
		
		t.playFromStart();
	}
	
	
}
