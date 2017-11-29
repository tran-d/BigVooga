package authoring_actionconditions;

public class ControllerActionCheckBoxVBox {
	
	private ActionConditionTab conditionTab;
	private ActionConditionTab actionTab;
	
	public ControllerActionCheckBoxVBox(ActionConditionTab conditionTab,ActionConditionTab actionTab) {
		this.conditionTab = conditionTab;
		this.actionTab = actionTab;
		this.actionTab.addTopToolBarListChangeListener(c -> this.conditionTab.setNewActionOptions(this.actionTab.getCurrentActions()));
	}

}
