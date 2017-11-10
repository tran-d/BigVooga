package gui.welcomescreen;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Settings {

	private Stage stage;
	private BorderPane rootPane;
	
	public Settings(Stage currentStage) {
		stage = currentStage;
		rootPane = new BorderPane();
		Scene scene = new Scene(rootPane, WelcomeScreen.WIDTH, WelcomeScreen.HEIGHT);

		stage.setScene(scene);
	}
	
	public void createSettingsScreen() {
		rootPane.setStyle(WelcomeScreen.SET_BACKGROUND_COLOR + WelcomeScreen.BACKGROUND_COLOR);
	}
	
}