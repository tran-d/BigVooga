package default_pkg;

import java.util.HashMap;
import java.util.Map;

import authoring_UI.MapManager;
import gui.welcomescreen.Instructions;
import gui.welcomescreen.Settings;
import gui.welcomescreen.WelcomeScreen;
import javafx.scene.Scene;
import javafx.stage.Stage;
import player.GameSelector;

public class SceneController {

	public static final String WELCOME_SCREEN_KEY = "Welcome Screen";
	public static final String GAME_SELECTOR_KEY = "Game Selector";
	public static final String CREATE_KEY = "Create";
	public static final String LEARN_KEY = "Learn";
	public static final String SETTINGS_KEY = "Settings";
	
	private Map<String, Scene> sceneMap = new HashMap<String, Scene>() ;
	private Stage stage;
	private Scene scene;
	
	public SceneController(Stage currentStage) {
		
		stage = currentStage;
		
		WelcomeScreen welcomeScreen = new WelcomeScreen (stage, this);
		welcomeScreen.createWelcomeScreen();
		scene = welcomeScreen.getScene();
		sceneMap.put(WELCOME_SCREEN_KEY, scene);
		
		GameSelector gameSelector = new GameSelector(stage, this);
		gameSelector.createGameSelector();
		scene = gameSelector.getScene();
		sceneMap.put(GAME_SELECTOR_KEY, scene);
		
		MapManager mapManager = new MapManager(stage, this);
		scene = mapManager.getScene();
		sceneMap.put(CREATE_KEY, scene);
		
		Instructions instructions = new Instructions(stage, this);
		instructions.createInstructions();
		scene = instructions.getScene();
		sceneMap.put(LEARN_KEY, scene);

		Settings settings = new Settings(stage, this);
		settings.createSettings();
		scene = settings.getScene();
		sceneMap.put(SETTINGS_KEY, scene);
		
	}
	
	public void switchScene (String key) {
		stage.setScene(sceneMap.get(key));
		
	}
}
