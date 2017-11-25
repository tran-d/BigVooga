package authoring_actionconditions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToolBar;

public class ControllerTopToolBar {
	
	private TopToolBar topToolBar;
	private ActionConditionVBox actionConditionVBox;
	
	public ControllerTopToolBar(TopToolBar topToolBar,ActionConditionVBox actionConditionVBox) {
		this.topToolBar = topToolBar;
		this.actionConditionVBox = actionConditionVBox;
		addListeners();
	}
	
	private void addListeners() {
		topToolBar.addButtonListener(new addListener(topToolBar,actionConditionVBox,true));
		topToolBar.addRemoveListener(new addListener(topToolBar,actionConditionVBox,false));
	}
	
	private class addListener implements EventHandler<ActionEvent> {
		
		private TopToolBar topToolBar;
		private ActionConditionVBox actionConditionVBox;
		private Boolean isAdding;
		public addListener(TopToolBar topToolBar, ActionConditionVBox actionConditionVBox, Boolean isAdding) {
			this.topToolBar = topToolBar;
			this.actionConditionVBox = actionConditionVBox;
			this.isAdding = isAdding;
		}

		@Override
		public void handle(ActionEvent arg0) {
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
}
