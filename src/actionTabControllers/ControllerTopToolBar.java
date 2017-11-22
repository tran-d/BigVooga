package actionTabControllers;

import authoring_UI.ActionConditionVBox;
import authoring_UI.ActionTab;
import authoring_UI.TopToolBar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToolBar;

public class ControllerTopToolBar {
	
	private TopToolBar conditionToolBar;
	private TopToolBar actionToolBar;
	private ActionConditionVBox conditionVBox;
	private ActionConditionVBox actionVBox;
	
	public ControllerTopToolBar(TopToolBar conditionToolBar,TopToolBar actionToolBar,ActionConditionVBox conditionVBox,ActionConditionVBox actionVBox) {
		this.conditionToolBar = conditionToolBar;
		this.actionToolBar = actionToolBar;
		this.conditionVBox = conditionVBox;
		this.actionVBox = actionVBox;
		addListeners();
	}
	
	private void addListeners() {
		conditionToolBar.addButtonListener(new addListener(conditionToolBar,conditionVBox,true));
		conditionToolBar.addRemoveListener(new addListener(conditionToolBar,conditionVBox,false));
		actionToolBar.addButtonListener(new addListener(actionToolBar,actionVBox,true));
		actionToolBar.addRemoveListener(new addListener(actionToolBar,actionVBox,false));
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
			if(isAdding) actionConditionVBox.addActionCondition(topToolBar.getOptionsValue());
			else actionConditionVBox.removeActionCondition(topToolBar.getRemoveValue());
		}
		
	}
}
