package base;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

	private static Stage stage;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Main.stage = stage;
		Pane sp = new Pane();
		Scene scene = new Scene(sp);
		stage.setScene(scene);
		
		Circle c = new Circle();
		c.setRadius(20);
		
		sp.getChildren().add(c);
		
		stage.show();
	}
	
	public Stage stage() {
		return stage;
	}
	
}
