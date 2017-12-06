package authoring_actionconditions;

import java.util.List;

public interface ActionConditionVBoxI<T> {
	
	public void removeConditionAction(int row);
	public List<T> getRows();
	public String getSelectorLabel();
	public void addToRows(ActionConditionRow actionConditionRow);
	
}
