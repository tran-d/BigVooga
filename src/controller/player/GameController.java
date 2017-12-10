package controller.player;

import java.io.FileNotFoundException;

import controller.welcomeScreen.SceneController;
import engine.EngineController;
import engine.utilities.data.GameDataHandler;
import gui.player.GameDisplay;
import javafx.stage.Stage;

/**
 * Acts as the main overarching controller that allows communication between the player and game engine.
 * 
 * @author Samarth and Ian
 *
 */
public class GameController {

	private Stage stage;
	private SceneController sceneController;
	private PlayerManager playerManager;
	private GameDisplay gameDisplay;
	private EngineController engineController;
	private GameDataHandler gameDataHandler;
	
	/**
	 * Delegates the correct instances to the correct classes for the player to appropriately work with the engine. This involves the intermediary
	 * PlayerManager which contains methods to communicate between player and engine, and thus has the instance of both player and engine
	 * passed to it. Likewise, the player and engine both have an instance of the PlayerManager to access its methods.
	 * 
	 * @param currentStage - The instance of the stage
	 * @param projectName - Name of game being played
	 * @param currentSceneController - The instance of the scene controller
	 * @throws FileNotFoundException - If the game name does not match with a game in the directory, throw this exception
	 */
	public GameController(Stage currentStage, String projectName, SceneController currentSceneController) throws FileNotFoundException {
		gameDataHandler = new GameDataHandler(projectName);
		engineController = gameDataHandler.loadGame();
		setup(currentStage, projectName, currentSceneController);
	}

	public GameController(Stage currentStage, String projectName, String saveFile, SceneController currentSceneController) throws FileNotFoundException {
		gameDataHandler = new GameDataHandler(projectName);
		engineController = gameDataHandler.loadGame(saveFile);
		setup(currentStage, projectName, currentSceneController);
	}
	
	private void setup(Stage currentStage, String projectName, SceneController currentSceneController) {
		stage = currentStage;
		sceneController = currentSceneController;
		playerManager = new PlayerManager(gameDataHandler);
		gameDisplay = new GameDisplay(stage, sceneController);
		stage.setScene(gameDisplay.getScene());
		playerManager.setDisplay(gameDisplay);
		gameDisplay.setPlayerManager(playerManager);
		gameDisplay.setDataHandler(gameDataHandler);
		gameDisplay.createGameDisplay();
		engineController.setPlayerManager(playerManager);
		playerManager.setEngineController(engineController);
		engineController.start();
	}
	
}
