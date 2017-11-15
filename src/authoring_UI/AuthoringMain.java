package authoring_UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AuthoringMain extends Application {
	private Menu myMenu;
	private MapManager myMap;
	
	private final static double SCENE_WIDTH = 700;
	private final static double SCENE_HEIGHT = 600;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		myMap = new MapManager();
		myMenu = new Menu(myMap);
		HBox authoringEnv = new HBox(myMenu, myMap);
		
		Scene scene = new Scene(authoringEnv, SCENE_WIDTH, SCENE_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
    public static void main(String[] args) {
        launch(args);
    }

}
