package authoring_actionconditions;

public class ControllerConditionActionTabs {
	
	private ConditionTab conditionTab;
	private ActionTab actionTab;
	
	public ControllerConditionActionTabs(ConditionTab conditionTab,ActionTab actionTab) {
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
	
	private void addConditionActionRow(ActionTab actionConditionTab) {
		if(!(actionConditionTab.getActionCondition() == null)) {
			if(actionConditionTab instanceof ConditionTab) {
				((ConditionTab) actionConditionTab).addCondition(actionConditionTab.getActionCondition(),actionTab.getCurrentActions());
			}
			else ((ActionTab) actionConditionTab).addAction(actionConditionTab.getActionCondition());
			actionConditionTab.addRemoveOption();
		}
	}
	
	private void removeConditionActionRow(ActionTab actionConditionTab) {
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
