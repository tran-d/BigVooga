package authoring_UI.dialogue;

import java.util.ArrayList;
import java.util.List;

import gui.welcomescreen.WelcomeScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import tools.DisplayLanguage;

/**
 * Class that represents the pane containing all dialogue authoring components
 * 
 * @author DavidTran
 *
 */
public class DialogueManager {

	private static final double NODE_SPACING = 25;
	private static final double BUTTON_WIDTH = 300;
	private static final double BUTTON_HEIGHT = 75;
	private static final String ADD_BUTTON_PROMPT = "New";
	private static final double WIDTH = WelcomeScreen.WIDTH * 2;

	private HBox hb;
	private DialogueTabPane dView;
	private List<DialogueEditor> editorList;
	private int currentEditor = 0;

	public DialogueManager() {

		dView = new DialogueTabPane();
		editorList = new ArrayList<>();
		hb = new HBox(NODE_SPACING);
		hb.setPrefSize(WIDTH, WelcomeScreen.HEIGHT);
		hb.getChildren().addAll(dView, createAddButton(ADD_BUTTON_PROMPT, e -> newEditor()));

		// test
		// addDefaultDialogueButton();
		// addUserDialogueButton("blah", -1);
	}

	/*************************** PUBLIC METHODS **********************************/

	public HBox getParent() {
		return hb;
	}

	/*************************** PRIVATE METHODS *********************************/

	private void newEditor() {
		editorList.add(new DialogueEditor(name -> addUserDialogueButton(name)));
		loadEditor(editorList.size() - 1);
	}

	private void loadEditor(int index) {
//		System.out.println(hb.getChildren().size());
		if (hb.getChildren().size() == 3)
			hb.getChildren().remove(3 - 1);
		
		hb.getChildren().add(editorList.get(index).getParent());
		currentEditor = index;
//		System.out.println("curr editor " + currentEditor);
	}

	private Button createAddButton(String name, EventHandler<ActionEvent> handler) {
		Button btn = new Button();
		btn.textProperty().bind(DisplayLanguage.createStringBinding(name));
		btn.setOnAction(handler);
		return btn;
	}

	private void addDefaultDialogueButton() {
		Button btn = new Button("Default Dialogue #1");
		btn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		// btn.setOnAction(e -> loadEditor(currentEditor));
		//change number
		dView.addDefaultDialogueButton(0, btn);

	}

	private void addUserDialogueButton(String name) {
		int id = currentEditor;
		Button btn = new Button(name);
		btn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		btn.setOnAction(e -> loadEditor(id));
		dView.addUserDialogueButton(currentEditor, btn);
	}
}
