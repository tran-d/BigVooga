package authoring_actionconditions;

public class ControllerConditionActionTabs {
	
	private ConditionTab<ConditionRow> conditionTab;
	private ActionTab<ActionRow> actionTab;
	
	public ControllerConditionActionTabs(ConditionTab<ConditionRow> conditionTab,ActionTab<ActionRow> actionTab) {
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
		System.out.println("Action/condition " + actionConditionTab.getActionCondition());
		if(!(actionConditionTab.getActionCondition() == null)) {
			if(actionConditionTab instanceof ConditionTab<?>) {
				((ConditionTab<ConditionRow>) actionConditionTab).addCondition(actionConditionTab.getActionCondition(),actionTab.getCurrentActions());
			}
			else ((ActionTab<ActionRow>) actionConditionTab).addAction(actionConditionTab.getActionCondition());
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
