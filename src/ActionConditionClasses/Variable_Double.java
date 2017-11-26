package ActionConditionClasses;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public abstract class Variable_Double extends HBox implements ChoiceBoxI<String> {
	
	private ChoiceBox<String> stringVariables;
	private TextField numberSelection;
	
	public Variable_Double() {
		stringVariables = new ChoiceBox<String>();
		numberSelection = new TextField();
	}
	
	@Override
	public void addListChangeListener(ListChangeListener<String> c) {
		stringVariables.getItems().addListener(c);
	}
	
	@Override
	public void setOptions(ObservableList<String> list) {
		stringVariables.setItems(list);
	}
	
	@Override
	public Object getCurrentChoice() {
		return stringVariables.getValue();
	}

}
