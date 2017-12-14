package authoring_UI.cutscene;

import authoring_UI.dialogue.Dialogue;
import javafx.scene.control.ListCell;

public class CutsceneListCell extends ListCell<String> {

	private Cutscene cutscene;
	
	public CutsceneListCell(Cutscene c) {
//		this.setText("Name: " + d.getName());
		this.setText("a");
		this.cutscene = c;
//		this.setOnMouseEntered(e -> showPopup(new ListCell<String>()));
	}
	
	public Cutscene getCutscene() {
		return cutscene;
	}
}

