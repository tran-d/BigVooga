package authoring_UI;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;

public interface VarValPairI {

	public String passVariable();
	public ChoiceBox newChoiceBox(ObservableList<String> values, String defaultVariable);
	public Spinner newSpinner(double min, double max, double initialValue, double amountToStepBy);
	
}
