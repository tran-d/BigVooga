package default_pkg;

import gui.welcomescreen.WelcomeScreen;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Launches VOOGASalad program by calling the initial welcome screen and showing the stage
 * @author Samarth Desai
 */

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * start the program
	 */
	public void start(Stage stage) {
		WelcomeScreen welcome = new WelcomeScreen(stage);
		welcome.createWelcomeScreen();
	}
}