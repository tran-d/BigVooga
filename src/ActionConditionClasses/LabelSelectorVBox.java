package ActionConditionClasses;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public abstract class LabelSelectorVBox extends VBox implements LabelSelectorVBoxI {
	
	private Label topLabel;
	
	public LabelSelectorVBox(String label) {
		super();
		topLabel = new Label(label);
		getChildren().add(topLabel);
	}
	
	@Override
	public void changeLabel(String newLabel) {
		Label tempLabel = new Label(newLabel);
		topLabel = tempLabel;
	}
	
}
