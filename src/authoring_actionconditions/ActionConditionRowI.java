package authoring_actionconditions;

import javafx.scene.control.Label;

public interface ActionConditionRowI {
	
	public Label getLabel();
	public Label getImplementationSelectorLabel();
	public Integer getImplementationSelectorVBoxValue();
	public void decreaseLabelID();
	public int getRowID();

}
