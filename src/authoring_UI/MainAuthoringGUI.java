package authoring_UI;

import controller.authoring.AuthoringController;
import controller.welcomeScreen.SceneController;
import gui.welcomescreen.WelcomeScreen;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainAuthoringGUI{
	
	private Stage stage;
	private Scene scene;
	private SceneController sceneController;
	private BorderPane rootPane;
	private ToolBar toolBar;
	
	private MenuButton fileOptions;
	private MenuButton settings;
	private Pane authoringPane;
	private AuthoringController authoringController;
	
	private static final String AUTHORING_CSS = "Authoring.css";
	private static final String BORDERPANE_ID = "borderpane";
	

	public MainAuthoringGUI(Stage currentStage, SceneController currentSceneController) {
		
		stage = currentStage;
		rootPane = new BorderPane();
		rootPane.setId(BORDERPANE_ID);
		scene = new Scene(rootPane, WelcomeScreen.WIDTH, WelcomeScreen.HEIGHT);
		scene.getStylesheets().add(MainAuthoringGUI.class.getResource(AUTHORING_CSS).toExternalForm());
		sceneController = currentSceneController;

	}
	
	public void createAuthoringGUI() {
		toolBar = new Toolbar(sceneController);
		rootPane.setTop(toolBar);
		
		authoringPane = new Pane();
		authoringController = new AuthoringController(stage, authoringPane);
		ViewSideBar sideBar = new ViewSideBar(authoringController);
		authoringController.switchView(AuthoringController.MAP_EDITOR_KEY, sideBar);
		
		rootPane.setCenter(authoringPane);
	}
	
	/**
	 * Gets the scene for initialization in SceneController.
	 * 
	 * @return the authoring gui scene
	 */
	public Scene getScene() {
		return scene;
	}
	
}
