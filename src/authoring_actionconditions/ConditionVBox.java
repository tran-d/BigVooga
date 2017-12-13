package authoring_actionconditions;

import java.util.List;

import javafx.collections.ObservableList;

public class ConditionVBox<T> extends ActionConditionVBox<T> implements ConditionVBoxI<T>{

	public ConditionVBox() {
		super();
	}
	
	public ConditionVBox(List<T> rows) {
		super(rows);
	}

	@Override
	public void addCondition(ObservableList<Integer> currentActions) {
		ConditionRow conditionRow = new ConditionRow(getRows().size() + 1,currentActions, (ConditionVBox<ConditionRow>) this);
		addToRows(conditionRow);
		BuildConditionView bcd = new BuildConditionView(this, conditionRow);
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
