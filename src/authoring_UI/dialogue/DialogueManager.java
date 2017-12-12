package authoring_UI.dialogue;

import java.util.ArrayList;
import java.util.List;

import authoring_UI.MapManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
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
	private static final String DELETE_BUTTON_PROMPT = "Delete";

	private HBox hb;
	private DialogueEditor currentEditor;
	private DialogueTabPane dView;
	private List<DialogueEditor> editorList;
	private int currentEditorIndex = 0;
	private DialogueExtractor dExtractor;
	private DialogueListView listView;

	private Tab mapDialoguesTab;

	public DialogueManager() {

		dView = new DialogueTabPane();
		editorList = new ArrayList<>();
		dExtractor = new DialogueExtractor();
		hb = new HBox(NODE_SPACING);
		hb.getChildren().addAll(dView, createSeparator(), createButtonPanel());

		// test
		// addDefaultDialogueButton();
		// addUserDialogueButton("blah", -1);

	}

	private Separator createSeparator() {
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		return separator;
	}

	private Separator createShortSeparator() {
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		separator.setPrefHeight(300);
		separator.setMaxHeight(300);
		separator.setMinHeight(300);
		return separator;
	}

	/*************************** PUBLIC METHODS **********************************/

	public void addDialogueListener(Tab dialoguesTab) {
		mapDialoguesTab = dialoguesTab;
		updateListView();
	}

	public HBox getPane() {
		return hb;
	}

	/*************************** PRIVATE METHODS *********************************/

	private void updateListView() {
		dExtractor.extract(editorList);
		listView = new DialogueListView(dExtractor.getDialogueList());
		System.out.println(listView);

		mapDialoguesTab.setContent(listView);
	}

	private void save() {
		if (currentEditor != null && !currentEditor.getName().trim().equals("")) {

			// if (editorList.size() > currentEditor) {
			// editorList.remove(currentEditor);
			// editorList.add(currentEditor, editor);
			// }
			// else
			// editorList.add(editor);

			if (editorList.contains(currentEditor)) {
				editorList.remove(currentEditor);
			}
			editorList.add(currentEditorIndex, currentEditor);

			// editorList.add(currentEditor);
			addUserDialogueButton(currentEditor.getName());
			// currentEditor = null;
		}
		System.out.println("# editors: " + editorList.size());

		updateListView();
	}

	private void newEditor() {
		currentEditor = new DialogueEditor(e -> save());
		currentEditorIndex = editorList.size();

		loadEditor(currentEditorIndex);
	}

	private void loadEditor(int index) {

		System.out.println("Load index: " + index);
		
		if (hb.getChildren().size() >= 4) {
			hb.getChildren().remove(5 - 1);
			hb.getChildren().remove(4 - 1);

		}

		if (editorList.size() <= index) {
			hb.getChildren().addAll(createShortSeparator(), currentEditor.getParent());
		} else {
			hb.getChildren().addAll(createShortSeparator(), editorList.get(index).getParent());
			currentEditor = editorList.get(index);

		}

		currentEditorIndex = index;
	}

	private VBox createButtonPanel() {
		VBox vb = new VBox(NODE_SPACING);
		vb.getChildren().addAll(createButton(ADD_BUTTON_PROMPT, e -> newEditor()),
				createButton(SAVE_BUTTON_PROMPT, e -> save()), createButton(DELETE_BUTTON_PROMPT, e -> delete()));
		return vb;
	}

	private void prev() {
		if (currentEditorIndex > 0) {
			currentEditorIndex -= 1;
			hb.getChildren().remove(4);
			hb.getChildren().add(editorList.get(currentEditorIndex).getParent());
		}
	}

	private void next() {
		if (currentEditorIndex < editorList.size() - 1) {
			System.out.println("Editor List Size Called in Next: " + editorList.size());
			System.out.println("Current Editor Index Called in Next: " + currentEditorIndex);
			currentEditorIndex += 1;
			hb.getChildren().remove(4);
			hb.getChildren().add(editorList.get(currentEditorIndex).getParent());
		}
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
		System.out.println("Click Editor Index: " + currentEditorIndex);
		Button btn = new Button(name);
		btn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		dView.addUserDialogueButton(currentEditorIndex, btn);
		btn.setOnAction(e -> loadEditor(dView.getButtonIndex(btn)));
	}

	private void delete() {

		for(int i = 0; i < editorList.size(); i++) {
			System.out.println("Element of Editor List Before Delete: " + editorList.get(i));
		}


		if (!editorList.isEmpty()) {
			
			removeUserDialogueButton();
			
			if (editorList.size() > 1) {
				System.out.println("Editor List Before Delete: " + editorList.size());
				System.out.println("Editor Index Before Delete: " + currentEditorIndex);

				if (currentEditorIndex == editorList.size() - 1) {
					prev();
					editorList.remove(currentEditorIndex + 1);

				} else {
					next();
					editorList.remove(currentEditorIndex - 1);
					currentEditorIndex -= 1;
				}
				
				currentEditor = editorList.get(currentEditorIndex);
			}

			else {
				hb.getChildren().remove(4);
				hb.getChildren().remove(3);
				editorList.remove(currentEditorIndex);
				currentEditorIndex -= 1;
			}
		}

		System.out.println("Editor List Size After Delete: " + editorList.size());
		System.out.println("Editor Index After Delete: " + currentEditorIndex);

		for(int i = 0; i < editorList.size(); i++) {
			System.out.println("Element of Editor List After Delete: " + editorList.get(i));
		}

	}

	private void removeUserDialogueButton() {
		int id = currentEditorIndex;
		dView.removeUserDialogueButton(id);
	}

}
