package authoring_actionconditions;

import javafx.collections.ObservableList;

public interface ConditionVBoxI<T> {
	
	public void addCondition(String label,ObservableList<Integer> currentActions);
	public void addActionOption();
	public void removeActionOption(Integer action);
	public void setNewActionOptions(ObservableList<Integer> newActionOptions);

}
