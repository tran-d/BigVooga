package authoring_actionconditions;

public class ControllerConditionActionTabs {
	
	private ActionConditionTab conditionTab;
	private ActionConditionTab actionTab;
	
	public ControllerConditionActionTabs(ActionConditionTab conditionTab,ActionConditionTab actionTab) {
		this.conditionTab = conditionTab;
		this.actionTab = actionTab;
		this.conditionTab.addButtonListener(e -> addConditionActionRow(this.conditionTab));
		this.actionTab.addButtonListener(e -> {
			addConditionActionRow(this.actionTab);
			addActionOption();
		});
		this.conditionTab.addRemoveListener(e -> removeConditionActionRow(this.conditionTab));
		this.actionTab.addRemoveListener(e -> {
			removeActionOption();
			removeConditionActionRow(this.actionTab);
		});
	}
	
	private void addConditionActionRow(ActionConditionTab actionConditionTab) {
		if(!(actionConditionTab.getActionCondition() == null)) {
			actionConditionTab.addConditionAction(actionConditionTab.getActionCondition(),actionTab.getCurrentActions());
			actionConditionTab.addRemoveOption();
		}
	}
	
	private void removeConditionActionRow(ActionConditionTab actionConditionTab) {
		if(!(actionConditionTab.getRemoveValue() == null)) {
			int rowToBeRemoved = actionConditionTab.getRemoveValue();
			actionConditionTab.removeActionCondtion(rowToBeRemoved - 1);
			actionConditionTab.removeRowOption(rowToBeRemoved - 1);
		}
	}
	
	private void addActionOption() {
		conditionTab.addActionOption();
	}
	
	private void removeActionOption() {
		if(!(actionTab.getRemoveValue() == null)) {
			conditionTab.removeActionOption(actionTab.getRemoveValue() - 1);
		}
	}

}
