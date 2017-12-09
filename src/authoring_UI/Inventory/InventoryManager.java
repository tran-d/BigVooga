package authoring_UI.Inventory;

import java.util.ArrayList;
import java.util.List;

import authoring.AbstractSpriteObject;
import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObjectGridManager;
import authoring_UI.AuthoringMapEnvironment;
import authoring_UI.DraggableGrid;
import authoring_UI.InventorySpritePanels;
import authoring_UI.MapManager;
import authoring_UI.SpriteGridHandler;
import authoring_UI.SpritePanels;
import authoring_UI.dialogue.DialogueEditor;
import authoring_UI.dialogue.DialogueTabPane;
import engine.utilities.data.GameDataHandler;
import gui.welcomescreen.WelcomeScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tools.DisplayLanguage;

public class InventoryManager {

	private static final double NODE_SPACING = 20;
	private static final double BUTTON_WIDTH = 300;
	private static final double BUTTON_HEIGHT = 75;
	private static final String ADD_BUTTON_PROMPT = "New";
	private static final String SAVE_BUTTON_PROMPT = "Save";
	private static final String DEFAULT = "Default";
	private static final String USER = "User";
	private static final String IMPORTED = "Imported";

	private HBox hb;
	private DialogueEditor editor;
	private DialogueTabPane dView;
	private ScrollPane spriteSideGridScrollPane;
	private List<DialogueEditor> editorList;
	private int currentEditor = 0;
	private SpriteObjectGridManager InventoryGridBE;
	private SpriteGridHandler SGH;
	private AuthoringEnvironmentManager myAEM;
	private VBox spriteSideVbox;
	private SpritePanels SPanels;
	private GameDataHandler myGDH;
	private AuthoringMapEnvironment myAME;
	
	public InventoryManager(AuthoringEnvironmentManager AEM) {
		DraggableGrid DG = new DraggableGrid();
		myAEM = AEM;
		SGH = new SpriteGridHandler("InventoryManager", 1, DG);
		
		InventoryGridBE = new InventoryGridManager();
		DG.setAllGrids(InventoryGridBE);
		DG.construct(SGH);
		
		
		SPanels = new InventorySpritePanels(SGH, myAEM);
		SGH.setDisplayPanel(SPanels);
		myAME = new AuthoringMapEnvironment(SPanels, DG);
		

	}
	
	public HBox getPane() {
		return myAME;
	}
	
	

}