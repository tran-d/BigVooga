package authoring_actionconditions;

import javafx.scene.control.Label;

public interface ActionConditionRowI {
	
	public Label getLabel();
	public Label getImplementationSelectorLabel();
	public String getImplementationSelectorVBoxValue();
	public void decreaseLabelID();
	public int getRowID();

}
