package authoring_actionconditions;

import javafx.collections.ObservableList;

public interface ConditionVBoxI<T> {
	
	public void addCondition(ObservableList<Integer> currentActions);
	public void addActionOption();
	public void removeActionOption(Integer action);
	public void setNewActionOptions(ObservableList<Integer> newActionOptions);

}
