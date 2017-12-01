package authoring_actionconditions;

import javafx.collections.ObservableList;

public interface ActionConditionVBoxI {
	
	public void addCondition(String label,ObservableList<Integer> currentActions);
	public void removeActionCondition(int row);
	public void addActionOption();
	public void removeActionOption(Integer action);
	
}
