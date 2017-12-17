package authoring_UI.dialogue;

import javafx.scene.control.ListCell;

public class DialogueListCell extends ListCell<String> {

	private Dialogue dialogue;
	
	public DialogueListCell(Dialogue d) {
		this.setText("a");
		this.dialogue = d;
	}
	
	public Dialogue getDialogue() {
		return dialogue;
	}
}
