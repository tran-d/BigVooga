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
		conditionToolBar.addButtonListener(new addListener(conditionToolBar,conditionVBox));
		conditionToolBar.addRemoveListener();
		actionToolBar.addButtonListener(new addListener(actionToolBar,actionVBox));
		actionToolBar.addRemoveListener(e);
	}
	
	private class addListener implements EventHandler<ActionEvent> {
		
		private TopToolBar topToolBar;
		private ActionConditionVBox actionConditionVBox;
		public addListener(TopToolBar topToolBar, ActionConditionVBox actionConditionVBox) {
			this.topToolBar = topToolBar;
			this.actionConditionVBox = actionConditionVBox;
		}

		@Override
		public void handle(ActionEvent arg0) {
			actionConditionVBox.addActionCondition(topToolBar.getOptionsValue());
		}
		
	}
}
