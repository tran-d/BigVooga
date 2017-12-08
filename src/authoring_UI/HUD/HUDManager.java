package authoring_UI.HUD;

import java.util.ArrayList;
import java.util.List;

import authoring_UI.MapManager;
import authoring_UI.dialogue.DialogueEditor;
import authoring_UI.dialogue.DialogueTabPane;
import gui.welcomescreen.WelcomeScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tools.DisplayLanguage;

public class HUDManager {

	private static final double NODE_SPACING = 20;
	private static final double BUTTON_WIDTH = 300;
	private static final double BUTTON_HEIGHT = 75;
	private static final String ADD_BUTTON_PROMPT = "New";
	private static final String SAVE_BUTTON_PROMPT = "Save";

	private HBox hb;
	private DialogueEditor editor;
	private DialogueTabPane dView;
	private List<DialogueEditor> editorList;
	private TabPane hudDisplay;
	private ScrollPane scrollPane;
	private HBox hudBox;

	public HUDManager() {
		
		hudBox = new HBox();
		hudDisplay = new TabPane();
		scrollPane = new ScrollPane();
		
		
		
	}
	
	public HBox getPane() {
		return hudBox;
	}
	
	
	
}
