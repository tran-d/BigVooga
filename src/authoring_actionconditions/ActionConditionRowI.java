package authoring_actionconditions;

import javafx.scene.control.Label;

public interface ActionConditionRowI {
	
	public Label getLabel();
	public Object getSelectedActions();
	public Label getImplementationSelectorLabel();
	public String getImplementationSelectorVBoxValue();

}
