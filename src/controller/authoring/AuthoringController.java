package controller.authoring;

import java.util.HashMap;
import java.util.Map;

import authoring_UI.MapManager;
import authoring_UI.SpriteCreator;
import authoring_UI.ViewSideBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AuthoringController {
	
	public static final String MAP_EDITOR_KEY = "Map Editor";
	public static final String SPRITE_CREATOR_KEY = "Sprite Creator";
	public static final String CUSTOM_PANEL_KEY = "Custom Panel";
	public static final String CUTSCENES_KEY = "Cutscenes";
	public static final String DIALOGUE_KEY = "Dialogue";
	public static final String HUD_KEY = "HUD";
	public static final String INVENTORY_KEY = "Inventory";
	public static final String MENU_CREATOR_KEY = "Menu Creator";
	
	private Map<String, TabPane> viewMap = new HashMap<String, TabPane>();
	private Pane authoringPane;
	private Stage stage;
	private TabPane view;
	
	public AuthoringController(Stage currentStage, Pane currentAuthoringPane) {
		
		stage = currentStage;
		authoringPane = currentAuthoringPane;
		
		MapManager mapManager = new MapManager(stage);
		viewMap.put(MAP_EDITOR_KEY, mapManager);
		
//		TabPane testPane = new TabPane();
		SpriteCreator sc = new SpriteCreator();
		viewMap.put(SPRITE_CREATOR_KEY, sc.getParent());
		
		//SpriteCreator mySpriteCreator = new SpriteCreator(stage, mySprites, myAEM);
		
	}
	
	/**
	 * Changes and sets the scene.
	 * 
	 * @param key - The key that extracts the correct scene from the map to use
	 */
	public void switchView (String key, ViewSideBar currentSideBar) {
		authoringPane.getChildren().removeAll(view, currentSideBar);
		view = viewMap.get(key);
		authoringPane.getChildren().addAll(view, currentSideBar);
		
	}
	
}
