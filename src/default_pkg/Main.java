package default_pkg;

import default_pkg.SceneController;
import gui.welcomescreen.WelcomeScreen;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Launches VOOGA program by calling the initial welcome screen
 * @author Samarth Desai
 */

public class Main extends Application {
	
	private SceneController sceneController;
	/**
	 * Launches the program
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Calls the first screen, the welcome screen
	 */
	public void start(Stage stage) {
		sceneController = new SceneController(stage);
		sceneController.switchScene(SceneController.WELCOME_SCREEN_KEY);
		
	}
	
	
}