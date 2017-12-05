package authoring_UI.dialogue;

import javafx.scene.control.ListCell;

public class DialogueListCell extends ListCell<String> {

	private Dialogue dialogue;
	
	public DialogueListCell(Dialogue d) {
//		this.setText("Name: " + d.getName());
		this.setText("a");
		this.dialogue = d;
//		this.setOnMouseEntered(e -> showPopup(new ListCell<String>()));
	}
	
	public Dialogue getDialogue() {
		return dialogue;
	}
}
