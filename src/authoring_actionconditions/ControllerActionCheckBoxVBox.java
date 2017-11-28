package authoring_actionconditions;

import ActionConditionClasses.ActionCheckBoxVBox;
import javafx.collections.ObservableList;

public class ControllerActionCheckBoxVBox {
	
	private ActionConditionTab conditionTab;
	private ActionConditionTab actionTab;
	
	public ControllerActionCheckBoxVBox(ActionConditionTab conditionTab,ActionConditionTab actionTab) {
		this.conditionTab = conditionTab;
		this.actionTab = actionTab;
		this.removeChoiceBoxVBox.addListChangeListener(c -> updateActionCheckBoxVBox(removeChoiceBoxVBox.getOptions()));
	}
	
	private void updateActionCheckBoxVBox(ObservableList<Integer> newOptions) {
		actionCheckBoxVBox.setNewOptions(newOptions);
	}

}
