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
	private int currentEditor = 0;

	public HUDManager() {

		dView = new DialogueTabPane();
		editorList = new ArrayList<>();
		hb = new HBox(NODE_SPACING);
		hb.setPrefSize(MapManager.VIEW_WIDTH, MapManager.VIEW_HEIGHT);
		hb.getChildren().addAll(dView, createButtonPanel());

		// test
		// addDefaultDialogueButton();
		// addUserDialogueButton("blah", -1);

	}

	/*************************** PUBLIC METHODS **********************************/

	public HBox getPane() {
		return hb;
	}

	/*************************** PRIVATE METHODS *********************************/

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
