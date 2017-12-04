package authoring_UI.dialogue;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tools.DisplayLanguage;

public class DialogueTextAreaView extends VBox {

	private static final double VBOX_SPACING = 25;
	private static final double DIALOG_PROMPT_WIDTH = 150;
	private static final double DIALOG_PROMPT_HEIGHT = 150;
	private static final String NEXT_BUTTON_PROMPT = "Next";
	private static final String PREV_BUTTON_PROMPT = "Previous";

	private List<TextArea> dialogueList;
	private Button nextButton;
	private Button prevButton;
	private int currentPanelIndex = 0;
	private Label currentPanel;
	private HBox dialoguePreview;

	public DialogueTextAreaView() {
		dialogueList = new ArrayList<>();
		this.setSpacing(15);
		dialoguePreview = new HBox();

		this.getChildren().addAll(dialoguePreview, makeToolPanel());
	}

	/************************ PUBLIC METHODS ***************************/

	public void removePanel() {

		if (!dialogueList.isEmpty()) {

			if (currentPanelIndex == 0) {
				next();
				if (currentPanelIndex == 0) {
					dialoguePreview.getChildren().clear();
					dialogueList.remove(currentPanelIndex);
				} else
					dialogueList.remove(--currentPanelIndex);
			} else if (currentPanelIndex == dialogueList.size() - 1) {
				prev();
				dialogueList.remove(currentPanelIndex + 1);
			} else {
				next();
				dialogueList.remove(--currentPanelIndex);
			}
		}

		// if (!dialogueList.isEmpty()) {
		//
		// dialoguePreview.getChildren().clear();
		//
		// if (currentPanelIndex == 0) {
		// next();
		// if (currentPanelIndex > 0)
		// dialogueList.remove(--currentPanelIndex);
		// }
		// if (currentPanelIndex == dialogueList.size() - 1) {
		// removeLast();
		// }
		//
		// System.out.println("removed panel");
		// }
	}

	public void addPanel() {
		TextArea ta = new TextArea();
		ta.setWrapText(true);
		dialogueList.add(ta);

		setCurrentPanel(dialogueList.size() - 1);
	}

	/************************ PRIVATE METHODS ***************************/

	private void setCurrentPanel(int index) {
		dialoguePreview.getChildren().clear();
		dialoguePreview.getChildren().add(dialogueList.get(index));
		currentPanelIndex = index;
	}

	private void prev() {
		if (currentPanelIndex > 0) {
			dialoguePreview.getChildren().clear();
			dialoguePreview.getChildren().add(dialogueList.get(--currentPanelIndex));
		}
	}

	private void next() {
		if (currentPanelIndex < dialogueList.size() - 1) {
			dialoguePreview.getChildren().clear();
			dialoguePreview.getChildren().add(dialogueList.get(++currentPanelIndex));
		}
	}

	private void removeLast() {
		dialoguePreview.getChildren().clear();
		dialogueList.remove(dialogueList.size() - 1);
		dialoguePreview.getChildren().add(dialogueList.get(--currentPanelIndex));
		System.out.println("prev panel");
	}

	private HBox makeToolPanel() {
		HBox hb = new HBox(50);
		currentPanel = new Label();
		currentPanel.textProperty().bind(new SimpleIntegerProperty(currentPanelIndex).asString());
		hb.getChildren().addAll(makeButtonPanel(), currentPanel);
		return hb;
	}

	private HBox makeButtonPanel() {
		HBox hb = new HBox(15);
		nextButton = makeButton(NEXT_BUTTON_PROMPT, e -> next());
		prevButton = makeButton(PREV_BUTTON_PROMPT, e -> prev());
		hb.getChildren().addAll(prevButton, nextButton);
		return hb;
	}

	private Button makeButton(String name, EventHandler<ActionEvent> handler) {
		Button btn = new Button();
		btn.textProperty().bind(DisplayLanguage.createStringBinding(name));
		btn.setOnAction(handler);
		return btn;
	}

}
