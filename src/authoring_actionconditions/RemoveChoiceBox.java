package authoring_actionconditions;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

public class RemoveChoiceBox extends ChoiceBox<Integer> {
	
	private ObservableList<Integer> currentRows;
	
	public RemoveChoiceBox() {
		super();
		currentRows = FXCollections.observableArrayList();
		currentRows.addListener((ListChangeListener<Integer>) c -> setItems(currentRows));
	}
	
	protected void addRow() {
		currentRows.add(currentRows.size() + 1);
	}
	
	protected void removeOption(int row) {
		currentRows.remove(row);
		for(int i = row; i < currentRows.size(); i++) currentRows.set(i, currentRows.get(i) - 1);
		setValue(null);
	}
	
}
