package authoring_UI.dialogue;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for extracting all dialogue information and converting into Dialogue objects.
 * 
 * @author DavidTran
 *
 */
public class DialogueExtractor {

	private List<Dialogue> dialogueList;

	public DialogueExtractor() {
		dialogueList = new ArrayList<>();

	}

	public void extract(List<DialogueEditor> editorList) {
		dialogueList = new ArrayList<>();

		for (DialogueEditor ed : editorList) {
			dialogueList.add(new Dialogue(ed.getName(), ed.getFontType(), ed.getFontSize(), ed.getFontColor(), ed.getDialogueList()));
		}
	}

	public List<Dialogue> getDialogueList() {
		return dialogueList;
	}
}
