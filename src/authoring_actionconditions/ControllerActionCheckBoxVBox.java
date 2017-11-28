package authoring_actionconditions;

import ActionConditionClasses.ActionCheckBoxVBox;
import javafx.collections.ObservableList;

public class ControllerActionCheckBoxVBox {
	
	private ActionCheckBoxVBox<Integer> actionCheckBoxVBox;
	private RemoveChoiceBoxVBox removeChoiceBoxVBox;
	
	public ControllerActionCheckBoxVBox(ActionCheckBoxVBox<Integer> actionCheckBoxVBox,RemoveChoiceBoxVBox removeChoiceBoxVBox) {
		this.actionCheckBoxVBox = actionCheckBoxVBox;
		this.removeChoiceBoxVBox = removeChoiceBoxVBox;
		this.removeChoiceBoxVBox.addListChangeListener(c -> updateActionCheckBoxVBox(removeChoiceBoxVBox.getOptions()));
	}
	
	private void updateActionCheckBoxVBox(ObservableList<Integer> newOptions) {
		actionCheckBoxVBox.setNewOptions(newOptions);
	}

}
