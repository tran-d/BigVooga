package player;

import default_pkg.SceneController;
import engine.GameMaster;
import javafx.stage.Stage;

public class GameController {

	private Stage stage;
	private PlayerManager playerManager;
	private GameDisplay gameDisplay;
	private GameMaster gameMaster;
	
	public GameController(Stage currentStage, GameDisplay currentGameDisplay) {
		
		stage = currentStage;
		
		playerManager = new PlayerManager();
		gameDisplay = currentGameDisplay;
		gameMaster = new GameMaster (playerManager);
		playerManager.setDisplay(gameDisplay);
		playerManager.setEngine(gameMaster);
		gameDisplay.setPlayerManager(playerManager);
		
	}
	
}
