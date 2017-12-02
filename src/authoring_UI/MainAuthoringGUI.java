package authoring_UI;

import java.util.ArrayList;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObjectGridManagerI;
import controller.welcomeScreen.SceneController;
import gui.welcomescreen.WelcomeScreen;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainAuthoringGUI{
	
	private Stage stage;
	private Scene scene;
	private SceneController sceneController;
	private BorderPane rootPane;
	private SingleSelectionModel<Tab> mySelectModel;
	private TabPane mapManager;
	private Tab currentTab;
	private Tab addTab;
	private ToolBar toolBar;
	
	private AuthoringEnvironmentManager myAEM;
	private SpriteObjectGridManagerI mySOGM;
	private SpriteManager mySprites;
	private AuthoringMapEnvironment authMap;
	private final String addTabString = "+";
	
	private int myTabCount = 1;
	private MenuButton fileOptions;
	private MenuButton settings;
	
	private static final String TABTAG = "map ";

	public MainAuthoringGUI(Stage currentStage, SceneController currentSceneController) {
		
		stage = currentStage;
		rootPane = new BorderPane();
		scene = new Scene(rootPane, WelcomeScreen.WIDTH, WelcomeScreen.HEIGHT);
		sceneController = currentSceneController;

	}
	
	public void createAuthoringGUI() {
		mapManager = new TabPane();
		mySelectModel = mapManager.getSelectionModel();
		setTab();
		createToolBar();
		rootPane.setTop(toolBar);
		rootPane.setCenter(mapManager);
	}
	
	private void createToolBar() {
		
		createFileOptions();
		createSettings();
		
		toolBar = new ToolBar(
				fileOptions,
				settings
				);
		
	}
	
	private void createFileOptions() {
		
		MenuItem save = new MenuItem("Save");
		//TODO save.setOnAction(e -> ());

		fileOptions = new MenuButton ("File", null, save);
		
	}
	
	private void createSettings() {
		
		MenuItem language = new MenuItem("Language");
		//TODO language.setOnAction(e -> ());

		settings = new MenuButton ("Settings", null, language);
		
	}
	
	private void setTab() {
		mapManager.setSide(Side.TOP);
		addTab = new Tab();
		addTab.setClosable(false);
		addTab.setText(addTabString);
		addTab.setOnSelectionChanged(e -> {
			createTab(myTabCount);
			mySelectModel.select(currentTab);
		});
		mapManager.getTabs().add(addTab);
	}

	private HBox setupScene() {

		authMap = new AuthoringMapEnvironment();
		setupBEAuthClasses();
		setupFEAuthClasses();

		authMap.setPrefWidth(WelcomeScreen.WIDTH);
		authMap.setPrefHeight(WelcomeScreen.HEIGHT);
		
		return authMap;
	}
	
	private void setupBEAuthClasses() {
		myAEM = new AuthoringEnvironmentManager();
		mySOGM = myAEM.getGridManager();
	}
	
	private void setupFEAuthClasses() {
		Menu myMenu = new Menu(myAEM, this, sceneController);
		SpriteGridHandler mySpriteGridHandler = new SpriteGridHandler(myTabCount, myMenu, mySOGM);
		DraggableGrid myGrid = new DraggableGrid(mySpriteGridHandler);
		mySprites = new SpriteManager(mySpriteGridHandler, myAEM, mySOGM);
		mySpriteGridHandler.addKeyPress(scene);
//
//<<<<<<< HEAD
//		authMap.getChildren().addAll(myMenu, myGrid, mySprites);
//		mySpriteGridHandler.addKeyPress(scene);
//=======
		authMap.setMenu(myMenu);
		authMap.setGrid(myGrid);
		authMap.setSpriteManager(mySprites);
//		authMap.getChildren().addAll(myMenu, myGrid, mySprites);
	}
	
	protected SpriteCreator createNewSpriteCreator() {
		SpriteCreator mySpriteCreator = new SpriteCreator(stage, mySprites, myAEM);
		return mySpriteCreator;
	}
	

	private void createTab(int tabCount) {
		currentTab = new Tab();
		String tabName = TABTAG + Integer.toString(tabCount);
		currentTab.setText(tabName);
		currentTab.setContent(setupScene());
		mapManager.getTabs().add(mapManager.getTabs().size() - 1, currentTab);
		myTabCount++;
	}
	
	private ArrayList<AuthoringMapEnvironment> getAllMapEnvironments(){
		ArrayList<AuthoringMapEnvironment> allMaps = new ArrayList<AuthoringMapEnvironment>();
		for (Tab t: mapManager.getTabs()) {
			if (!t.getText().equals(addTabString)){
				AuthoringMapEnvironment AME = (AuthoringMapEnvironment) t.getContent();
				allMaps.add(AME);
			}
		}
		return allMaps;

	
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
