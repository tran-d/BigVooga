package authoring_UI;

import java.util.Observable;
import java.util.Observer;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObjectGridManagerI;
import default_pkg.SceneController;
import gui.welcomescreen.WelcomeScreen;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MapManager extends TabPane implements Observer {
	
	private Stage stage;
	private Scene scene;
	private SceneController sceneController;
	
	private SingleSelectionModel<Tab> mySelectModel;
	private Tab currentTab;
	private Tab addTab;
	private AuthoringEnvironmentManager myAEM;
	private SpriteObjectGridManagerI mySOGM;
	private SpriteManager mySprites;
	
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
		addTab.setText("+");
		addTab.setOnSelectionChanged(e -> {
			createTab(myTabCount);
			mySelectModel.select(currentTab);
		});
		this.getTabs().add(addTab);
	}

	private HBox setupScene() {
		myAEM = new AuthoringEnvironmentManager();
		Menu myMenu = new Menu(myAEM,this);
		mySOGM = myAEM.getGridManager();
		DraggableGrid myGrid = new DraggableGrid(myTabCount, myMenu, mySOGM);
		mySprites = new SpriteManager(myGrid, myAEM, mySOGM);
		HBox authMap = new HBox(myMenu, myGrid, mySprites);
		authMap.setPrefWidth(WelcomeScreen.WIDTH);
		authMap.setPrefHeight(WelcomeScreen.HEIGHT);
		
		return authMap;
	}

	private void createTab(int tabCount) {
		currentTab = new Tab();
		String tabName = TABTAG + Integer.toString(tabCount);
		currentTab.setText(tabName);
		currentTab.setContent(setupScene());
		this.getTabs().add(this.getTabs().size() - 1, currentTab);
		myTabCount++;
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("notified observer");
		mySprites.getUserSpriteParam((String) arg);
	}
}
