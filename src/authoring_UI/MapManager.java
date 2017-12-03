package authoring_UI;

import java.util.ArrayList;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObjectGridManagerI;
import gui.welcomescreen.WelcomeScreen;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MapManager extends TabPane {	
	
	private static final String TABTAG = "map ";
	
	private Stage stage;
	private SingleSelectionModel<Tab> mySelectModel;
	private Tab addTab;
	private AuthoringMapEnvironment authMap;
	private ViewSideBar sideBar;
	private SpriteManager mySprites;
	private AuthoringEnvironmentManager myAEM;
	private SpriteObjectGridManagerI mySOGM;
	private int myTabCount;
	private Tab currentTab;
	private String addTabString;

	public MapManager(Stage currentStage) {
		stage = currentStage;
		mySelectModel = this.getSelectionModel();
		this.setPrefWidth(WelcomeScreen.WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH);
		this.setPrefHeight(WelcomeScreen.HEIGHT);
		this.setLayoutX(ViewSideBar.VIEW_MENU_HIDDEN_WIDTH);
		
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
		
		return authMap;
	}
	
	private void setupBEAuthClasses() {
		
		myAEM = new AuthoringEnvironmentManager();
		mySOGM = myAEM.getGridManager();
	}
	
	private void setupFEAuthClasses() {
		Menu myMenu = new Menu(myAEM, this);
		DraggableGrid myGrid = myAEM.getDraggableGrid();
		
		SpriteGridHandler mySpriteGridHandler = new SpriteGridHandler(myTabCount, myMenu, myGrid);
		myGrid.construct(mySpriteGridHandler);
		mySprites = new SpriteManager(mySpriteGridHandler, myAEM, mySOGM);
		mySpriteGridHandler.addKeyPress(stage.getScene());
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
