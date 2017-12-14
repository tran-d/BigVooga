package controller.authoring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteCreatorSpriteManager;
import authoring_UI.DraggableGrid;
import authoring_UI.MapManager;
import authoring_UI.SpriteCreator;
import authoring_UI.SpriteCreatorGridHandler;
import authoring_UI.SpriteCreatorImageGrid;
import authoring_UI.SpriteCreatorManager;
import authoring_UI.ViewSideBar;
import authoring_UI.HUD.HUDManager;
import authoring_UI.Inventory.InventoryManager;
import authoring_UI.Menu.MenuManager;
import authoring_UI.SpriteCreatorTab.SpriteCreatorManagerSlack;
import authoring_UI.dialogue.DialogueManager;
import engine.utilities.data.GameDataHandler;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AuthoringController {
	public static final String MAP_EDITOR_KEY = "Map Editor";
	public static final String SPRITE_CREATOR_KEY = "Sprite Creator";
	public static final String INVENTORY_CREATOR_KEY = "Inventory Creator";
	public static final String CUSTOM_PANEL_KEY = "Custom Panel";
	public static final String CUTSCENES_KEY = "Cutscenes";
	public static final String DIALOGUE_KEY = "Dialogue";
	public static final String HUD_KEY = "HUD";
	public static final String INVENTORY_KEY = "Inventory";
	public static final String MENU_CREATOR_KEY = "Menu Creator";

	private ObjectProperty<MapManager> activeManagerProperty;
	private Map<String, MapManager> viewMapKeysToManager = new HashMap<String, MapManager>();
	private Map<String, Pane> viewMap = new HashMap<String, Pane>();
	private Pane authoringPane;
	private Scene scene;
	private Pane view;
	private MapManager mapManager;
	private SpriteCreatorManagerSlack mySCM;
	private SpriteCreatorManagerSlack myInventorySCM;

	public AuthoringController(Scene currentScene, Stage currentStage, Pane currentAuthoringPane, GameDataHandler GDH) {
		scene = currentScene;
		authoringPane = currentAuthoringPane;
		activeManagerProperty = new SimpleObjectProperty<MapManager>();
		activeManagerProperty.addListener((change, previousManager, newManager) -> {
			;
			if (previousManager != null) {
				previousManager.gridIsNotShowing();
			}
			if (newManager != null) {

				newManager.gridIsShowing();
			}
		});

		AuthoringEnvironmentManager AEM = new AuthoringEnvironmentManager(GDH);
		mapManager = new MapManager(AEM, scene);
		viewMap.put(MAP_EDITOR_KEY, mapManager.getPane());
		viewMapKeysToManager.put(MAP_EDITOR_KEY, mapManager);

//		SpriteCreatorImageGrid imageGrid = new SpriteCreatorImageGrid();
//		mySM = new SpriteCreatorSpriteManager();
//		SpriteCreatorGridHandler mySCGridHandler = new SpriteCreatorGridHandler(mySM, imageGrid);
		
//		mySCM = new SpriteCreatorManager(AEM, imageGrid, mySCGridHandler, mySM);
//		SpriteCreator sc = new SpriteCreator(AEM, mySCM, imageGrid, mySM, mySCGridHandler);
		
		mySCM = new SpriteCreatorManagerSlack(AEM, scene, "SpriteObject");
		viewMap.put(SPRITE_CREATOR_KEY, mySCM.getPane());
		
		myInventorySCM = new SpriteCreatorManagerSlack(AEM, scene, "InventoryObject");
		viewMap.put(INVENTORY_CREATOR_KEY, myInventorySCM.getPane());

		DialogueManager dm = new DialogueManager();
		dm.addDialogueListener(mapManager.getDialoguesTab());
		viewMap.put(DIALOGUE_KEY, dm.getPane());

		HUDManager hudManager = new HUDManager(AEM, scene);
		viewMap.put(HUD_KEY, hudManager.getPane());
		viewMapKeysToManager.put(HUD_KEY, hudManager);

		MenuManager menuManager = new MenuManager(AEM, scene);
		viewMap.put(MENU_CREATOR_KEY, menuManager.getPane());
		viewMapKeysToManager.put(MENU_CREATOR_KEY, menuManager);

		InventoryManager inventoryManager = new InventoryManager(AEM, scene);
		viewMap.put(INVENTORY_KEY, inventoryManager.getPane());
		viewMapKeysToManager.put(INVENTORY_KEY, inventoryManager);
	}

	/**
	 * Changes and sets the authoring view.
	 * 
	 * @param key
	 *            - The key that extracts the correct view from the viewmap to use
	 */
	public void switchView(String key, ViewSideBar currentSideBar) {
		authoringPane.getChildren().removeAll(view, currentSideBar);
		view = viewMap.get(key);
		if (this.viewMapKeysToManager.containsKey(key)) {
			;
			this.activeManagerProperty.set(viewMapKeysToManager.get(key));
		} else {
			this.activeManagerProperty.set(null);
		}
		authoringPane.getChildren().addAll(view, currentSideBar);
	}

	public List<DraggableGrid> getExistingWorlds() {
		return mapManager.getAllWorlds();
	}
	
	public void importGrids(List<DraggableGrid> importedWorlds) {
		mapManager.addImportedWorlds(importedWorlds);
	}
}