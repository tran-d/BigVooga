package authoring_UI;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

public class RemoveChoiceBox extends ChoiceBox<Integer> {
	
	ObservableList<Integer> currentRows;
	
	public RemoveChoiceBox() {
		super();
		currentRows = FXCollections.observableArrayList();
		currentRows.addListener((ListChangeListener<Integer>) c -> {
			System.out.println("Hi");
			setItems(currentRows);
		});
	}
	
	protected void addRow() {
		if(currentRows.isEmpty()) {
			currentRows.add(1);
		}
		else {
			currentRows.add(currentRows.size() + 1);
		}
		System.out.println("Rows should have been added to...");
	}
	
	protected void removeRow(int row) {
		currentRows.remove(row);
		for(int i = row; i < currentRows.size(); i++) currentRows.set(i, currentRows.get(i) - 1);
	}
	
}
