package authoring_actionconditions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControllerTopToolBar {
	
	private TopToolBar topToolBar;
	private ActionConditionVBox actionConditionVBox;
	
	public ControllerTopToolBar(TopToolBar topToolBar,ActionConditionVBox actionConditionVBox) {
		this.topToolBar = topToolBar;
		this.actionConditionVBox = actionConditionVBox;
		addListeners();
	}
	
	private void addListeners() {
		topToolBar.addButtonListener(e -> handleAddingorRemoving(true));
		topToolBar.addRemoveListener(e -> handleAddingorRemoving(false));
	}
	
	private void handleAddingorRemoving(Boolean isAdding) {
		if(isAdding && !(topToolBar.getOptionsValue() == null)) {
			actionConditionVBox.addActionCondition(topToolBar.getOptionsValue());
			topToolBar.addRemoveOption();
		}
		else if(!isAdding && !(topToolBar.getRemoveValue() == null)) {
			int rowToBeRemoved = topToolBar.getRemoveValue();
			actionConditionVBox.removeActionCondition(rowToBeRemoved - 1);
			topToolBar.removeRemoveOption(rowToBeRemoved - 1);
		}
	}
	
}
