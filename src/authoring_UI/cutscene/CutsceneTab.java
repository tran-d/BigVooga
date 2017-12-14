package authoring_UI.cutscene;

import authoring_UI.displayable.DisplayableTab;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class CutsceneTab extends DisplayableTab {

	private static final double DIALOGUE_SPACING = 25;
	private static final double PADDING = 25;

	private VBox cutsceneLister;
	private ScrollPane sp;

	public CutsceneTab(String name) {
		super(name);
		this.setContent(sp);
	}

	@Override
	protected VBox makeVBox(double width, double height, double spacing) {
		return makeVBox(width, height, spacing);
	}

	@Override
	protected void addDisplayable(int index, Button btn) {
		if (cutsceneLister.getChildren().size() > index) {
			cutsceneLister.getChildren().remove(index);
			cutsceneLister.getChildren().add(index, btn);
		} else
			cutsceneLister.getChildren().add(btn);
		// System.out.println(dialogueLister.getChildren().size() + " " + index);

	}

	@Override
	protected void deleteDisplayable(int index) {
		cutsceneLister.getChildren().remove(index);

	}
	
	@Override
	protected int getButtonIndex(Button btn) {
		return cutsceneLister.getChildren().indexOf(btn);
	}

}
