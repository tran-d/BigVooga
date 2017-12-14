package authoring_UI.dialogue;

import authoring_UI.displayable.DisplayableTab;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * Class that represents a tab listing dialogues to edit
 * 
 * @author DavidTran
 *
 */
public class DialogueTab extends DisplayableTab {

	private static final double DIALOGUE_SPACING = 25;
	private static final double PADDING = 25;

	private VBox dialogueLister;
	private ScrollPane sp;

	public DialogueTab(String name) {
		super(name);
		this.setContent(sp);
	}

	@Override
	protected VBox makeVBox(double width, double height, double spacing) {
		return makeVBox(width, height, spacing);
	}

	@Override
	protected void addDisplayable(int index, Button btn) {
		if (dialogueLister.getChildren().size() > index) {
			dialogueLister.getChildren().remove(index);
			dialogueLister.getChildren().add(index, btn);
		} else
			dialogueLister.getChildren().add(btn);
		// System.out.println(dialogueLister.getChildren().size() + " " + index);

	}

	@Override
	protected void deleteDisplayable(int index) {
		dialogueLister.getChildren().remove(index);

	}
	
	@Override
	protected int getButtonIndex(Button btn) {
		return dialogueLister.getChildren().indexOf(btn);
	}

}
