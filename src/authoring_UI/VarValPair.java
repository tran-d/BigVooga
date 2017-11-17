package authoring_UI;

import java.util.List;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;

public class VarValPair extends HBox implements VarValPairI{
	
	private ChoiceBox<String> variables;
	private List<Control> values;
	
	public VarValPair(ObservableList<String> variableOptions, String defaultVariable){
		variables = newChoiceBox(variableOptions,defaultVariable);
	}

	@Override
	public ChoiceBox newChoiceBox(ObservableList<String> values, String defaultVariable) {
		ChoiceBox<String> cb = new ChoiceBox(values);
		cb.setValue(defaultVariable);
		addToControlandHBox(cb);
		return cb;
	}

	@Override
	public Spinner newSpinner(double min, double max, double initialValue, double amountToStepBy) {
		Spinner spinner = new Spinner(min,max,initialValue,amountToStepBy);
		addToControlandHBox(spinner);
		return spinner;
	}

	@Override
	public void remove(Control control) {
		values.remove(control);
		getChildren().remove(control);
	}
	
	private void addToControlandHBox(Control control) {
		values.add(control);
		getChildren().add(control);
	}
	
}
