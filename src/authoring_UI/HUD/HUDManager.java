package authoring_UI.HUD;

import java.util.ArrayList;
import java.util.List;

import authoring.AbstractSpriteObject;
import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObjectGridManager;
import authoring_UI.DraggableGrid;
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

public class HUDManager {

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
	private SpriteObjectGridManager HUDGridBE;
	private SpriteGridHandler SGH;
	private AuthoringEnvironmentManager myAEM;
	private VBox spriteSideVbox;
	private SpritePanels SPanels;
	private GameDataHandler myGDH;

	public HUDManager(AuthoringEnvironmentManager AEM) {
//		myGDH = GDH;
		DraggableGrid DG = new DraggableGrid();
		myAEM = AEM;
		SGH = new SpriteGridHandler(2001, DG);
		
		HUDGridBE = new HUDGridManager();
		DG.setAllGrids(HUDGridBE);
		DG.construct(SGH);
		
//		this.SGH = SGH;
		
		SPanels = new SpritePanels(SGH, myAEM);
		SGH.setDisplayPanel(SPanels);
		spriteSideGridScrollPane = new ScrollPane();
		spriteSideVbox = new VBox();
		editorList = new ArrayList<>();
		hb = new HBox(NODE_SPACING);
		hb.setPrefSize(MapManager.VIEW_WIDTH, MapManager.VIEW_HEIGHT);
		
		
		spriteSideVbox.getChildren().add(loadSpritesIn());
		
		hb.getChildren().addAll(SPanels, HUDGridBE.getMapLayer());

		// test
		// addDefaultDialogueButton();
		// addUserDialogueButton("blah", -1);

	}
	
	public HBox getPane() {
		return hb;
	}
	
	

	/*************************** PRIVATE METHODS *********************************/

	
	private TabPane loadSpritesIn(){
		TabPane tp = new TabPane();
		tp.setSide(Side.TOP);
		Tab defaultSpriteTab = createSubTab(DEFAULT, myAEM.getDefaultSpriteController().getAllSprites());
		Tab userSpriteTab = createSubTab(USER, myAEM.getCustomSpriteController().getAllSprites());
		Tab importedSpriteTab = createSubTab(IMPORTED, new ArrayList<AbstractSpriteObject>());
		tp.getTabs().addAll(defaultSpriteTab, userSpriteTab, importedSpriteTab);
		return tp;
	}
	
	private Tab createSubTab(String tabName, List<AbstractSpriteObject> sprites) {
		Tab subTab = new Tab();
		subTab.textProperty().bind(DisplayLanguage.createStringBinding(tabName));
//		defaultSpriteTab.setContent(mySprites);
		subTab.setContent(makeGrid(sprites));
		subTab.setClosable(false);
		return subTab;
	}
	
	private ScrollPane makeGrid(List<AbstractSpriteObject> sprites) {
		GridPane gp = new GridPane();
		int totalRows = (int) Math.ceil(sprites.size()/10);
		int DEFAULT_MIN_ROWS = 15;
		totalRows = (totalRows<DEFAULT_MIN_ROWS) ? DEFAULT_MIN_ROWS : totalRows;
		int counter =0;
		for (int i = 0; i < totalRows; i++) {
			for (int j = 0; j < 10; j++) {				
				StackPane sp = new StackPane();
				sp.setPrefHeight(50);
				sp.setPrefWidth(50);
				sp.setBackground(
						new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
				BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED,
						CornerRadii.EMPTY, BorderWidths.DEFAULT);
				sp.setBorder(new Border(border));
				if (counter<sprites.size()) {
					AbstractSpriteObject toPopulate = sprites.get(counter);
					System.out.println("Adding " + toPopulate);
					this.SGH.addSpriteDrag(toPopulate);
					this.SGH.addSpriteMouseClick(toPopulate);
			
					sp.getChildren().add(toPopulate);
			
				counter++;
				
				gp.add(sp, j, i);
			}
		}
		}
		ScrollPane SP = new ScrollPane(gp);
		//sp.getStylesheets().add(this.getClass().getResource("gui.welcomescreen/" + MenuOptionsTemplate.SCROLLPANE_CSS).toExternalForm());
		return SP;
	}
	
	
	
	private void save() {
		if (editor != null && !editor.getName().trim().equals("")) {

			// if (editorList.size() > currentEditor) {
			// editorList.remove(currentEditor);
			// editorList.add(currentEditor, editor);
			// }
			// else
			// editorList.add(editor);

			editorList.add(editor);
			addUserDialogueButton(editor.getName());
			editor = null;
		}
		System.out.println("# editors: " + editorList.size());
	}

	private void newEditor() {
		editor = new DialogueEditor(name -> save());
		loadEditor(editorList.size());
	}

	private void loadEditor(int index) {
		if (hb.getChildren().size() == 3)
			hb.getChildren().remove(3 - 1);

		if (editorList.size() <= index) {
			hb.getChildren().add(editor.getParent());
		} else {
			hb.getChildren().add(editorList.get(index).getParent());
			editor = editorList.get(index);

		}

		currentEditor = index;
	}

	private VBox createButtonPanel() {
		VBox vb = new VBox(NODE_SPACING);
		vb.getChildren().addAll(createButton(ADD_BUTTON_PROMPT, e -> newEditor()),
				createButton(SAVE_BUTTON_PROMPT, e -> save()));
		return vb;
	}

	private Button createButton(String name, EventHandler<ActionEvent> handler) {
		Button btn = new Button();
		btn.textProperty().bind(DisplayLanguage.createStringBinding(name));
		btn.setOnAction(handler);
		return btn;
	}

	private void addDefaultDialogueButton() {
		Button btn = new Button("Default Dialogue #1");
		btn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		// btn.setOnAction(e -> loadEditor(currentEditor));
		// change number
		dView.addDefaultDialogueButton(0, btn);

	}

	private void addUserDialogueButton(String name) {
		int id = currentEditor;
		Button btn = new Button(name);
		btn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		btn.setOnAction(e -> loadEditor(id));
		dView.addUserDialogueButton(id, btn);
	}
	
	
	
}
