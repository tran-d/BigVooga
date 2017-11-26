package authoring_actionconditions;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

public abstract class IntegerChoiceBox extends ChoiceBox<Integer> {
	
	private ObservableList<Integer> currentOptions;
	
	public IntegerChoiceBox() {
		super();
		currentOptions = FXCollections.observableArrayList();
		currentOptions.addListener((ListChangeListener<Integer>) c -> setItems(currentOptions));
	}
	
	protected void removeOption(int option) {
		currentOptions.remove(option);
	}
	
	protected ObservableList<Integer> getCurrentOptions() {
		return currentOptions;
	}

	protected void setCurrentOptions(ObservableList<Integer> newCurrentOptions) {
		currentOptions = newCurrentOptions;
	}
	
}
