package authoring_UI.cutscene;

import authoring_UI.dialogue.Dialogue;
import javafx.scene.control.ListCell;

public class CutsceneListCell extends ListCell<String> {

	private Cutscene cutscene;
	
	public CutsceneListCell(Cutscene c) {
		this.setText("a");
		this.cutscene = c;
	}
	
	public Cutscene getCutscene() {
		return cutscene;
	}
}

