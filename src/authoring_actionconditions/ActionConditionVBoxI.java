package authoring_actionconditions;

import java.util.List;

import javafx.collections.ObservableList;

public interface ActionConditionVBoxI {
	
	public void addConditionAction(String label,ObservableList<Integer> currentActions);
	public void removeConditionAction(int row);
	public void addActionOption();
	public void removeActionOption(Integer action);
	public List<ActionConditionRow> getRows();
	
}
