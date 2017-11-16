package authoring_UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AuthoringMain extends Application {
	
	private final static double SCENE_WIDTH = 770;
	private final static double SCENE_HEIGHT = 500;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		MapManager myMap = new MapManager(SCENE_WIDTH, SCENE_HEIGHT);

		Scene scene = new Scene(myMap, SCENE_WIDTH, SCENE_HEIGHT);

		primaryStage.setTitle("BIG VOOGA");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
    public static void main(String[] args) {
        launch(args);
    }

}
