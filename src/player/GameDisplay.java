package player;

import gui.welcomescreen.MenuOptionsTemplate;
import gui.welcomescreen.WelcomeScreen;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameDisplay {
	
	private Stage stage;
	private BorderPane rootPane;
	
	public GameDisplay(Stage currentStage) {
		stage = currentStage;
		rootPane = new BorderPane();
		Scene scene = new Scene(rootPane, WelcomeScreen.WIDTH, WelcomeScreen.HEIGHT);

		stage.setScene(scene);
	}
	
	public void createGameDisplay() {
		
		
		
	}
	
	//private void 
}