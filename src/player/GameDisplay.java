package player;

import default_pkg.SceneController;
import javafx.stage.Stage;

public class GameDisplay {
	
	private SceneController sceneController;
	private PlayerManager playerManager;
	
	public GameDisplay(Stage currentStage, SceneController currentSceneController) {
		sceneController = currentSceneController;
	}
	
	public void createGameDisplay() {
		
	}
	
	public void setPlayerManager(PlayerManager currentPlayerManager) {
		playerManager = currentPlayerManager;
	}
}