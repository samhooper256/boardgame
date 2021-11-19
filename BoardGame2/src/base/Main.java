package base;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static Stage stage;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Main.stage = stage;
		stage.show();
	}
	
	public Stage stage() {
		return stage;
	}
	
}
