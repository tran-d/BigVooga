package authoring_actionconditions;

import javafx.collections.ObservableList;

public interface ConditionTabI {
	
	public void addActionOption();
	public void removeActionOption(Integer action);
	public void setNewActionOptions(ObservableList<Integer> newActionOptions);
	public void addCondition(String label, ObservableList<Integer> currentActions);

}
