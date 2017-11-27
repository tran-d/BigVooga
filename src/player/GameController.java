package player;

import java.io.FileNotFoundException;

import default_pkg.SceneController;
import engine.EngineController;
import engine.GameMaster;
import engine.utilities.data.GameDataHandler;
import javafx.stage.Stage;

/**
 * 
 * @author Samarth and Ian
 *
 */
public class GameController {

	private Stage stage;
	private PlayerManager playerManager;
	private GameDisplay gameDisplay;
	private EngineController engineController;
	private GameDataHandler gameDataHandler;
	
	public GameController(Stage currentStage, String projectName, GameDisplay currentGameDisplay) throws FileNotFoundException {
		
		stage = currentStage;
		
		playerManager = new PlayerManager();
		gameDisplay = currentGameDisplay;
		gameDataHandler = new GameDataHandler(projectName);
		playerManager.setDisplay(gameDisplay);
		gameDisplay.setPlayerManager(playerManager);
		gameDisplay.setDataHandler(gameDataHandler);
		engineController = gameDataHandler.loadGame();
		engineController.setPlayerManager(playerManager);
		engineController.start();
		
	}
	
}
