package authoring_actionconditions;

import javafx.collections.ObservableList;

public interface ActionConditionVBoxI {
	
	public void addActionCondition(String label,ObservableList<Integer> currentActions);
	public void removeActionCondition(int row);
	
}
