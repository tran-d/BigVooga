package authoring_actionconditions;

public class ControllerConditionActionTabs {
	
	private ActionConditionTab conditionTab;
	private ActionConditionTab actionTab;
	
	public ControllerConditionActionTabs(ActionConditionTab conditionTab,ActionConditionTab actionTab) {
		this.conditionTab = conditionTab;
		this.actionTab = actionTab;
		this.conditionTab.addButtonListener(e -> addConditionRow());
		this.actionTab.addButtonListener(e -> {
			addActionRow(this.actionTab);
			addActionOption();
		});
		this.conditionTab.addRemoveListener(e -> removeConditionActionRow(this.conditionTab));
		this.actionTab.addRemoveListener(e -> {
			removeActionOption();
			removeConditionActionRow(this.actionTab);
		});
	}
	
	private void addConditionRow() {
		if(!(conditionTab.getActionCondition() == null)) {
			conditionTab.addCondition(conditionTab.getActionCondition(),actionTab.getCurrentActions());
			conditionTab.addRemoveOption();
		}
	}
	
	private void addActionRow(ActionConditionTab actionConditionTab) {
		if(!(actionConditionTab.getActionCondition() == null)) {
			actionConditionTab.addAction(actionConditionTab.getActionCondition());
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
