package authoring_actionconditions;

import java.util.List;

import javafx.collections.ObservableList;

public class ConditionVBox<T> extends ActionConditionVBox<T> implements ConditionVBoxI<T>{

	public ConditionVBox(String selectorString) {
		super(selectorString);
	}
	
	public ConditionVBox(String selectorString,List<T> rows) {
		super(selectorString,rows);
	}

	@Override
	public void addCondition(String label, ObservableList<Integer> currentActions) {
		ConditionRow conditionRow = new ConditionRow(getRows().size() + 1, label, getSelectorLabel(),null,currentActions, this);
		addToRows(conditionRow);
		getChildren().add(conditionRow);
	}
	
	@Override
	public void setNewActionOptions(ObservableList<Integer> newActionOptions) {
		getRows().forEach(row -> ((ConditionRow) row).setNewActionCheckBoxVBoxOptions(newActionOptions));
	}
	
	@Override
	public void addActionOption() {
		getRows().forEach(row -> ((ConditionRow) row).addAction());
	}
	
	@Override
	public void removeActionOption(Integer action) {
		getRows().forEach(row -> ((ConditionRow) row).removeAction(action));
	}

}
