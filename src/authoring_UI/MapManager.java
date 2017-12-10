package authoring_UI;

import java.util.ArrayList;
import java.util.List;

import authoring.AuthoringEnvironmentManager;
import authoring.SpritePanels.GameElementSelector;
import authoring.SpritePanels.SpritePanels;
import engine.utilities.data.GameDataHandler;
import gui.welcomescreen.WelcomeScreen;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Side;
import javafx.scene.Scene;
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
	protected String TAB_TAG;
	protected static final String ADD_TAB = "+";
	protected String MANAGERNAME;
	
	protected Stage stage;
	protected Scene scene;
	protected SingleSelectionModel<Tab> mySelectModel;
	protected Tab addTab;
//	private AuthoringMapEnvironment authMap;

	private ViewSideBar sideBar;
	private GameElementSelector mySprites;
	private AuthoringEnvironmentManager myAEM;
	private int myTabCount = 1;
	private Tab currentTab;
	private boolean oldProject;
	private String projectName = "TestProject";
	private GameDataHandler myGDH;
	private int numWorlds = 1;
	private List<DraggableGrid> allWorlds = new ArrayList<DraggableGrid>();
	private Pane mapEditor = new Pane();
	private SpritePanels spritePanels;

	public MapManager(AuthoringEnvironmentManager AEM, Stage currentStage)  {
		setTabTag();
		setManagerName();
		myAEM = AEM;
		myGDH = myAEM.getGameDataHandler();
		stage = currentStage;
		mapEditor.getChildren().add(this);
		mySelectModel = this.getSelectionModel();
		this.setPrefWidth(VIEW_WIDTH);
		this.setPrefHeight(VIEW_HEIGHT);
		this.setLayoutX(ViewSideBar.VIEW_MENU_HIDDEN_WIDTH);
		
		List<DraggableGrid> DGs = getListOfDraggableGrids();
		if (DGs.size()>0){
			for (DraggableGrid w: DGs){
			System.out.println("Grid: " + w);
			setTab();
			createTab(myTabCount, w);
			}
		} else {
			setTab();
		}
		
		
//		setTab();
		// TODO REDO LOGIC ^^^ 
		// calls createTab, which calls setUpScene, which calls set up auth classes, 
		// which creates new Authoring Environment Manager
	}
	
	protected void setManagerName(){
		MANAGERNAME = "MapManager";
	}
	
	protected List<DraggableGrid> getListOfDraggableGrids(){
		return myGDH.loadWorldsFromWorldDirectory();
	}
	
	protected String getManagerName(){
		return MANAGERNAME;
	}
	
	protected SpritePanels makeSpritePanels(SpriteGridHandler mySpriteGridHandler){
		return new SpritePanels(mySpriteGridHandler, myAEM);
	}
	
	protected DraggableGrid makeDraggableGrid(){
		return new DraggableGrid();
	}
	
	
	private void setTab() { //?
		this.setSide(Side.TOP);
		addTab = new Tab();
		addTab.setClosable(false);
		addTab.setText(ADD_TAB);
		addTab.setOnSelectionChanged(e -> {
			createTab(myTabCount, makeDraggableGrid());
			mySelectModel.select(currentTab);
		});
		this.getTabs().add(addTab);
	}

	private HBox setupScene(DraggableGrid w) { 
		return setupFEAuthClasses(w);
//		return authMap;
	}
	
	
	private HBox setupFEAuthClasses(DraggableGrid w) { 
		System.out.println("setUpFE?");
		// TODO if it's old project, want all possible worlds, so many worlds!
//		allWorlds.add(w); // TODO unsure if needed
		SpriteGridHandler mySpriteGridHandler = new SpriteGridHandler(getManagerName(), myTabCount, w); 
		w.construct(mySpriteGridHandler);
		mySpriteGridHandler.addKeyPress(stage.getScene());
		spritePanels = new SpritePanels(mySpriteGridHandler, myAEM);
		mySpriteGridHandler.setDisplayPanel(spritePanels);
		AuthoringMapEnvironment authMap = new AuthoringMapEnvironment(spritePanels, w);
		return authMap;
	}

	private void createTab(int tabCount, DraggableGrid w) { //?
		currentTab = new Tab();
		StringProperty tabMap = new SimpleStringProperty();
		tabMap.bind(Bindings.concat(DisplayLanguage.createStringBinding(TAB_TAG)).concat(" " + Integer.toString(tabCount)));
		
		currentTab.textProperty().bind(tabMap);
//		currentTab.textProperty().set(//TODO: World Name);
		currentTab.setContent(setupScene(w));
		this.getTabs().add(this.getTabs().size() - 1, currentTab);
		myTabCount++;
		System.out.println("tab incremented");	
	}
	
	protected void setTabTag(){
		TAB_TAG = "Map";
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
	
	public List<DraggableGrid> getAllWorlds() {
		return allWorlds;
	}
}
