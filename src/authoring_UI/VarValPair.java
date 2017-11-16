package authoring_UI;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;

public class VarValPair extends HBox implements VarValPairI{
	
	private ChoiceBox<String> variables;
	private ChoiceBox<String> values;
	
	public VarValPair(ObservableList<String> variableOptions, String defaultVariable, ObservableList<String> valueOptions, String defaultOption){
		variables = new ChoiceBox(variableOptions);
		values = new ChoiceBox(valueOptions);
		variables.setValue(defaultVariable);
		variables.getSelectionModel().selectedInd
		values.setValue(defaultOption);
	}

	@Override
	public String passVariable() {
		return variables.getValue();
	}

	@Override
	public String passValue() {
		return values.getValue();
	}
}
