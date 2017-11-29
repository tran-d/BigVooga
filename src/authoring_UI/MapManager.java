package authoring_UI;

import java.util.ArrayList;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObjectGridManagerI;
import default_pkg.SceneController;
import gui.welcomescreen.WelcomeScreen;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MapManager extends TabPane {
	
	private Stage stage;
	private Scene scene;
	private SceneController sceneController;
	private SingleSelectionModel<Tab> mySelectModel;
	private Tab currentTab;
	private Tab addTab;
	private AuthoringEnvironmentManager myAEM;
	private SpriteObjectGridManagerI mySOGM;
	private SpriteManager mySprites;
	private AuthoringMapEnvironment authMap;
	private final String addTabString = "+";
	
	private int myTabCount = 1;
	private static final String TABTAG = "map ";
	

	public MapManager(Stage currentStage, SceneController currentSceneController) {
		
		stage = currentStage;
		sceneController = currentSceneController;
		scene = new Scene(this, WelcomeScreen.WIDTH, WelcomeScreen.HEIGHT);
		
		mySelectModel = this.getSelectionModel();
		setTab();	
	}
	
	private void setTab() {
		this.setSide(Side.TOP);
		addTab = new Tab();
		addTab.setClosable(false);
		addTab.setText(addTabString);
		addTab.setOnSelectionChanged(e -> {
			createTab(myTabCount);
			mySelectModel.select(currentTab);
		});
		this.getTabs().add(addTab);
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
		Menu myMenu = new Menu(myAEM, this);
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
		this.getTabs().add(this.getTabs().size() - 1, currentTab);
		myTabCount++;
	}
	
	private ArrayList<AuthoringMapEnvironment> getAllMapEnvironments(){
		ArrayList<AuthoringMapEnvironment> allMaps = new ArrayList<AuthoringMapEnvironment>();
		for (Tab t: this.getTabs()) {
			if (!t.getText().equals(addTabString)){
				AuthoringMapEnvironment AME = (AuthoringMapEnvironment) t.getContent();
				allMaps.add(AME);
			}
		}
		return allMaps;
	}
	
}
