package controller.welcomeScreen;

import java.util.HashMap;
import java.util.Map;

import authoring_UI.MainAuthoringGUI;
import gui.welcomescreen.GameSelector;
import gui.welcomescreen.Learn;
import gui.welcomescreen.Settings;
import gui.welcomescreen.WelcomeScreen;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Stores all the scenes accessed by the welcome screen, and allows them to be accessible by a map. This ensures that only one instance of
 * each of these scenes is created, so the states of the scenes can be passed around effectively.
 * @author Samarth Desai
 *
 */
public class SceneController {

	public static final String WELCOME_SCREEN_KEY = "Welcome Screen";
	public static final String GAME_SELECTOR_KEY = "Game Selector";
	public static final String GAME_DISPLAY_KEY = "Game Display";
	public static final String CREATE_KEY = "Create";
	public static final String LEARN_KEY = "Learn";
	public static final String SETTINGS_KEY = "Settings";
	
	private Map<String, Scene> sceneMap = new HashMap<String, Scene>();
	private Stage stage;
	private Scene scene;
	
	/**
	 * Initializes all the scenes and puts them in the sceneMap.
	 * 
	 * @param currentStage - The instance of the stage being passed
	 */
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
		
		MainAuthoringGUI authoringGUI = new MainAuthoringGUI(stage, this);
		authoringGUI.createAuthoringGUI();
		scene = authoringGUI.getScene();
		sceneMap.put(CREATE_KEY, scene);
		
		Learn learn = new Learn(stage, this);
		learn.createLearn();
		scene = learn.getScene();
		sceneMap.put(LEARN_KEY, scene);

		Settings settings = new Settings(stage, this);
		settings.createSettings();
		scene = settings.getScene();
		sceneMap.put(SETTINGS_KEY, scene);
		
	}
	
	/**
	 * Changes and sets the scene.
	 * 
	 * @param key - The key that extracts the correct scene from the map to use
	 */
	public void switchScene (String key) {
		stage.setScene(sceneMap.get(key));
	}
}
