package player;

import java.net.URISyntaxException;
import java.util.List;

import default_pkg.SceneController;
import engine.sprite.DisplayableImage;
import engine.utilities.data.GameDataHandler;
import gui.welcomescreen.WelcomeScreen;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This class serves as the player that displays any game the user created in authoring. When a user loads up a game from the game selector,
 * GameDisplay is initialized and acts as the primary medium for interaction and viewing of the game for the user. Any user inputs during a
 * game are sent from this GameDisplay to the game engine through the PlayerManager controller.
 * 
 * @author Samarth Desai
 *
 */
public class GameDisplay {
	
	private Stage stage;
	private Scene scene;
	private BorderPane rootPane;
	private Pane gamePane;
	private SceneController sceneController;
	private PlayerManager playerManager;
	private GameDataHandler gameDataHandler;
	private ParallelCamera camera;
	
	/**
	 * Primarily sets up the stage, root pane, and scene for the GameDisplay view.
	 * 
	 * @param currentStage - The stage instance
	 * @param currentSceneController - The scene controller instance
	 */
	public GameDisplay(Stage currentStage, SceneController currentSceneController) {
		stage = currentStage;
		rootPane = new BorderPane();
		gamePane = new Pane();
		rootPane.setCenter(gamePane);
		rootPane.setBackground(new Background(new BackgroundFill[] {new BackgroundFill(Color.WHITE, null, null)}));
		sceneController = currentSceneController;
		scene = new Scene(rootPane, WelcomeScreen.WIDTH, WelcomeScreen.HEIGHT);
		camera = new ParallelCamera();
	}
	
	/**
	 * Sets the key and mouse events that are inputs in the player.
	 */
	public void createGameDisplay() {
		scene.setOnKeyPressed(e -> playerManager.setKeyPressed(e.getCode()));
		scene.setOnKeyReleased(e -> playerManager.setKeyReleased(e.getCode()));
		scene.setOnMousePressed(e -> playerManager.setPrimaryButtonDown(e.getX(), e.getY()));
		scene.setOnMouseReleased(e -> playerManager.setPrimaryButtonUp(e.getX(), e.getY()));
		
		scene.setCamera(camera);
		
		createBack();
	}
	
	/**
	 * Creates a back button that sends the user out of the player and into the welcome screen.
	 */
	private void createBack() {
		Button back = new Button("Back");
		back.setOnMouseClicked(e -> leaveGame());
		rootPane.setTop(back);
	}
	
	/**
	 * Acts when the back button is selected, and changes scenes to the welcome screen while also stopping the engine controller.
	 */
	private void leaveGame() {
		sceneController.switchScene(SceneController.GAME_SELECTOR_KEY);
		playerManager.stop();
	}
	
	/**
	 * Passes the PlayerManager into GameDisplay.
	 * 
	 * @param currentPlayerManager - The instance of PlayerManager
	 */
	public void setPlayerManager(PlayerManager currentPlayerManager) {
		playerManager = currentPlayerManager;
		
	}
	
	/**
	 * Passes the GameDataHandler into GameDisplay.
	 * 
	 * @param currentGameDataHandler - The instance of GameDataHandler
	 */
	public void setDataHandler(GameDataHandler currentGameDataHandler) {
		gameDataHandler = currentGameDataHandler;
		
	}
	
	/**
	 * Takes in the updated objects from PlayerManager and displays them.
	 * 
	 * @param images - The list of objects for the player to display
	 */
	public void setUpdatedImages (List<DisplayableImage> images) {
		//TODO; takes in new image file name, location, and size for all objects
		gamePane.getChildren().clear();
		ImageView gameImage = null;
		for (DisplayableImage image : images) {
			try {
				gameImage = new ImageView(gameDataHandler.getImage(image.getFileName()));
			} catch (URISyntaxException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
			gameImage.setFitWidth(image.getWidth());
			gameImage.setFitHeight(image.getHeight());
			gameImage.setRotate(image.getHeading());
			gameImage.setX(image.getX()-image.getWidth()/2);
			gameImage.setY(image.getY()-image.getHeight()/2);
			gamePane.getChildren().add(gameImage);
		}
	}
	
	/**
	 * Gets the scene for initialization in SceneController.
	 * 
	 * @return the game display scene
	 */
	public Scene getScene() {
		return scene;
	}
}