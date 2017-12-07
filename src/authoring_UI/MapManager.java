package authoring_UI;

import java.util.ArrayList;
import java.util.List;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObjectGridManagerI;
import engine.utilities.data.GameDataHandler;
import gui.welcomescreen.WelcomeScreen;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Side;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tools.DisplayLanguage;

public class MapManager extends TabPane {	
	
	public static final int VIEW_WIDTH = MainAuthoringGUI.AUTHORING_WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH;
	public static final int VIEW_HEIGHT = WelcomeScreen.HEIGHT - 35;
	private static final String TAB_TAG = "Map";
	private static final String ADD_TAB = "+";
	
	private Stage stage;
	private SingleSelectionModel<Tab> mySelectModel;
	private Tab addTab;
	private AuthoringMapEnvironment authMap;
	private ViewSideBar sideBar;
	private GameElementSelector mySprites;
	private AuthoringEnvironmentManager myAEM;
	private SpriteObjectGridManagerI mySOGM;
	private int myTabCount = 1;
	private Tab currentTab;
	private boolean oldProject;
	private String projectName = "TestProject";
	private GameDataHandler myGDH;
	private int numWorlds = 1;

	private Pane mapEditor = new Pane();
	private SpritePanels spritePanels;

	public MapManager(Stage currentStage)  {
		stage = currentStage;
		mapEditor.getChildren().add(this);
		mySelectModel = this.getSelectionModel();
		this.setPrefWidth(VIEW_WIDTH);
		this.setPrefHeight(VIEW_HEIGHT);
		this.setLayoutX(ViewSideBar.VIEW_MENU_HIDDEN_WIDTH);
		if (oldProject) {
			projectName = "OLD PROJECT NAME"; // TODO. where to get new project name from?
		}
		myGDH = new GameDataHandler(projectName); 
		if (oldProject) {
			try {
				for (DraggableGrid myWorld : myGDH.loadWorldsFromDirectoryName(projectName)) {
					setTab(myWorld);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			setTab(); 
		}
		// calls createTab, which calls setUpScene, which calls set up auth classes, 
		// which creates new Authoring Environment Manager
	}
	
	private void setTab(DraggableGrid world) {
		this.setSide(Side.TOP);
		addTab = new Tab();
		addTab.setClosable(false);
		addTab.setText(ADD_TAB);
		addTab.setOnSelectionChanged(e -> {
			createTab(myTabCount, world);
			mySelectModel.select(currentTab);
		});
		this.getTabs().add(addTab);
	}
	
	private void setTab() {
		this.setSide(Side.TOP);
		addTab = new Tab();
		addTab.setClosable(false);
		addTab.setText(ADD_TAB);
		addTab.setOnSelectionChanged(e -> {
			createTab(myTabCount);
			mySelectModel.select(currentTab);
		});
		this.getTabs().add(addTab);
	}

	private HBox setupScene() { // called for every world there is
		authMap = new AuthoringMapEnvironment(); // TODO just 1 fine?
		setupBEAuthClasses();
		setupFEAuthClasses();
		return authMap;
	}
	
	private HBox setupScene(DraggableGrid world) {
		authMap = new AuthoringMapEnvironment();
		setupBEAuthClasses(world);
		setupFEAuthClasses();
		return authMap;
	}
	
	private void setupBEAuthClasses(DraggableGrid world) {
		myAEM = new AuthoringEnvironmentManager(myGDH);
		if (oldProject) {
			myAEM.setOldDraggableGrid(world); //TODO FILL THIS IN
		}
		mySOGM = myAEM.getGridManager();
	}
	
	private void setupBEAuthClasses() {
		myAEM = new AuthoringEnvironmentManager(myGDH);
		mySOGM = myAEM.getGridManager();
	}
	
	private void setupFEAuthClasses() {
		
		DraggableGrid myGrid = myAEM.getDraggableGrid();
		SpriteGridHandler mySpriteGridHandler = new SpriteGridHandler(myTabCount, myGrid);
		myGrid.construct(mySpriteGridHandler);
		mySpriteGridHandler.addKeyPress(stage.getScene());
		spritePanels = new SpritePanels(this, mySpriteGridHandler, myAEM, mySOGM);
		mySpriteGridHandler.setDisplayPanel(spritePanels);
		authMap.setPanels(spritePanels);
		authMap.setGrid(myGrid);
	}
	

	private void createTab(int tabCount) {
		currentTab = new Tab();
		StringProperty tabMap = new SimpleStringProperty();
		tabMap.bind(Bindings.concat(DisplayLanguage.createStringBinding(TAB_TAG)).concat(" " + Integer.toString(tabCount)));
		currentTab.textProperty().bind(tabMap);
		currentTab.setContent(setupScene());
		this.getTabs().add(this.getTabs().size() - 1, currentTab);
		myTabCount++;
	}
	
	private void createTab(int tabCount, DraggableGrid DG) {
		currentTab = new Tab();
		StringProperty tabMap = new SimpleStringProperty();
		tabMap.bind(Bindings.concat(DisplayLanguage.createStringBinding(TAB_TAG)).concat(" " + Integer.toString(tabCount)));
		currentTab.textProperty().bind(tabMap);
		currentTab.setContent(setupScene(DG));
		this.getTabs().add(this.getTabs().size() - 1, currentTab);
		myTabCount++;
	}
	
	private List<AuthoringMapEnvironment> getAllMapEnvironments(){
		List<AuthoringMapEnvironment> allMaps = new ArrayList<AuthoringMapEnvironment>();
		for (Tab t: this.getTabs()) {
			if (!t.getText().equals(ADD_TAB)){
				AuthoringMapEnvironment AME = (AuthoringMapEnvironment) t.getContent();
				allMaps.add(AME);
			}
		}
		return allMaps;
	}
	
	public Pane getPane() {
		return mapEditor;
	}
	
	public Tab getDialoguesTab() {
		return spritePanels.getDialoguesTab();
	}
}
