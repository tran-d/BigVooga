package authoring_UI.dialogue;

import java.util.ArrayList;
import java.util.List;

import authoring_UI.MapManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tools.DisplayLanguage;

/**
 * Class that represents the pane containing all dialogue authoring components
 * 
 * @author DavidTran
 *
 */
public class DialogueManager {

	private static final double NODE_SPACING = 20;
	private static final double BUTTON_WIDTH = 300;
	private static final double BUTTON_HEIGHT = 75;
	private static final String ADD_BUTTON_PROMPT = "New";
	private static final String SAVE_BUTTON_PROMPT = "Save";

	private HBox hb;
	private DialogueEditor dEditor;
	private DialogueTabPane dView;
	private List<DialogueEditor> editorList;
	private int currentEditor = 0;
	private DialogueExtractor dExtractor;
	private DialogueListView listView;
	
	private Tab mapDialoguesTab;

	public DialogueManager() {

		dView = new DialogueTabPane();
		editorList = new ArrayList<>();
		dExtractor = new DialogueExtractor();
		hb = new HBox(NODE_SPACING);
		hb.setPrefSize(MapManager.VIEW_WIDTH, MapManager.VIEW_HEIGHT);
		hb.getChildren().addAll(dView, createButtonPanel());

		// test
		// addDefaultDialogueButton();
		// addUserDialogueButton("blah", -1);

	}

	/*************************** PUBLIC METHODS **********************************/

	public void addDialogueListener(Tab dialoguesTab) {
		mapDialoguesTab = dialoguesTab;
	}
	
	public HBox getPane() {
		return hb;
	}
	

	/*************************** PRIVATE METHODS *********************************/
	
	private void updateListView() {
		dExtractor.extract(editorList);
		listView = new DialogueListView(dExtractor.getDialogueList());
		System.out.println(listView);
		
//		hb.getChildren().add(listView);
		mapDialoguesTab.setContent(listView);
	}
	
	private void save() {
		if (dEditor != null && !dEditor.getName().trim().equals("")) {

			// if (editorList.size() > currentEditor) {
			// editorList.remove(currentEditor);
			// editorList.add(currentEditor, editor);
			// }
			// else
			// editorList.add(editor);

			editorList.add(dEditor);
			addUserDialogueButton(dEditor.getName());
			dEditor = null;
		}
		System.out.println("# editors: " + editorList.size());
		
		updateListView();
	}

	private void newEditor() {
		dEditor = new DialogueEditor(name -> save());
		loadEditor(editorList.size());
	}

	private void loadEditor(int index) {
		if (hb.getChildren().size() >= 3)
			hb.getChildren().remove(3 - 1);

		if (editorList.size() <= index) {
			hb.getChildren().add(dEditor.getParent());
		} else {
			hb.getChildren().add(editorList.get(index).getParent());
			dEditor = editorList.get(index);

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
