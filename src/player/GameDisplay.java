package player;

import default_pkg.SceneController;
import engine.utilities.data.GameDataHandler;
import gui.welcomescreen.WelcomeScreen;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameDisplay {
	
	private Stage stage;
	private Scene scene;
	private Pane rootPane;
	private SceneController sceneController;
	private PlayerManager playerManager;
	private GameDataHandler gameDataHandler;
	
	public GameDisplay(Stage currentStage, SceneController currentSceneController) {
		stage = currentStage;
		rootPane = new Pane();
		sceneController = currentSceneController;
		scene = new Scene(rootPane, WelcomeScreen.WIDTH, WelcomeScreen.HEIGHT);
	}
	
	public void createGameDisplay() {
		scene.setOnKeyPressed(e -> playerManager.setKeyPressed(e.getCode()));
		scene.setOnKeyReleased(e -> playerManager.setKeyReleased(e.getCode()));
		scene.setOnMousePressed(e -> playerManager.setPrimaryButtonPressed(e.getX(), e.getY()));
		scene.setOnMouseReleased(e -> playerManager.setPrimaryButtonReleased(e.getX(), e.getY()));
	}
	
	public void setPlayerManager(PlayerManager currentPlayerManager) {
		playerManager = currentPlayerManager;
		
	}
	
	public void setDataHandler(GameDataHandler currentGameDataHandler) {
		gameDataHandler = currentGameDataHandler;
		
	}
	
	public void setUpdatedObject (Object updatedObject) {
		//TODO; takes in new image file name, location, and size for all objects
	}
	
	public Scene getScene() {
		return scene;
	}
}