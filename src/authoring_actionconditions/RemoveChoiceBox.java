package authoring_actionconditions;

import javafx.collections.ObservableList;

public class RemoveChoiceBox extends IntegerChoiceBox {
	
	public RemoveChoiceBox() {
		super();
	}
	
	protected void addRow() {
		ObservableList<Integer> currentRows = getCurrentOptions();
		currentRows.add(currentRows.size() + 1);
		setCurrentOptions(currentRows);
	}
	
	protected void removeOption(int row) {
		ObservableList<Integer> currentRows = getCurrentOptions();
		currentRows.remove(row);
		for(int i = row; i < currentRows.size(); i++) currentRows.set(i, currentRows.get(i) - 1);
		setValue(null);
		setCurrentOptions(currentRows);
	}
	
}
