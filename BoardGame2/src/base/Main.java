package base;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

	private static Stage stage;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Main.stage = stage;
		StackPane sp = new StackPane();
		Scene scene = new Scene(sp);
		stage.setScene(scene);
		stage.show();
	}
	
	public Stage stage() {
		return stage;
	}
	
}
