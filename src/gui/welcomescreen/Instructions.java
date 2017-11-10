package gui.welcomescreen;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Instructions {

	private Stage stage;
	private BorderPane rootPane;
	
	public Instructions(Stage currentStage) {
		stage = currentStage;
		rootPane = new BorderPane();
		Scene scene = new Scene(rootPane, WelcomeScreen.WIDTH, WelcomeScreen.HEIGHT);

		stage.setScene(scene);
	}
	
	public void createInstructionsScreen() {
		rootPane.setStyle(WelcomeScreen.SET_BACKGROUND_COLOR + WelcomeScreen.BACKGROUND_COLOR);
	}
	
}
