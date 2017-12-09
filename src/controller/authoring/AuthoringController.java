package controller.authoring;

import java.util.HashMap;
import java.util.Map;

import authoring.AuthoringEnvironmentManager;
import authoring_UI.DraggableGrid;
import authoring_UI.MapManager;
import authoring_UI.SpriteCreator;
import authoring_UI.ViewSideBar;
import authoring_UI.HUD.HUDManager;
import authoring_UI.Inventory.InventoryManager;
import authoring_UI.dialogue.DialogueManager;
import engine.utilities.data.GameDataHandler;
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
	
	private Map<String, Pane> viewMap = new HashMap<String, Pane>();
	private Pane authoringPane;
	private Stage stage;
	private Pane view;
	private MapManager mapManager;
	
	public AuthoringController(Stage currentStage, Pane currentAuthoringPane, GameDataHandler GDH) {
		stage = currentStage;
		authoringPane = currentAuthoringPane;
		
		AuthoringEnvironmentManager AEM = new AuthoringEnvironmentManager(GDH, stage);
		mapManager = new MapManager(AEM, stage);
		viewMap.put(MAP_EDITOR_KEY, mapManager.getPane());
		
		SpriteCreator sc = new SpriteCreator(AEM);
		viewMap.put(SPRITE_CREATOR_KEY, sc.getPane());
		
//		DialogueManager dm = new DialogueManager();
//		dm.addDialogueListener(mapManager.getDialoguesTab());
//		viewMap.put(DIALOGUE_KEY, dm.getPane());
		
//		HUDManager hudManager = new HUDManager(AEM);
//		viewMap.put(HUD_KEY, hudManager.getPane());
		
		InventoryManager inventoryManager = new InventoryManager(AEM);
		viewMap.put(INVENTORY_KEY, inventoryManager.getPane());
	}
	
	/**
	 * Changes and sets the authoring view.
	 * @param key - The key that extracts the correct view from the viewmap to use
	 */
	public void switchView (String key, ViewSideBar currentSideBar) {
		authoringPane.getChildren().removeAll(view, currentSideBar);
		view = viewMap.get(key);
		authoringPane.getChildren().addAll(view, currentSideBar);
	}

//	public void saveWorlds() {
//		GameDataHandler existingGDH = mapManager.getGDH();
//		for (DraggableGrid DG : mapManager.getAllWorlds()) {
//			try {
//				existingGDH.saveWorld(DG);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
}