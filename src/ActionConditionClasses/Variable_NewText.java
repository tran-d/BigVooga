package ActionConditionClasses;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public abstract class Variable_NewText extends HBox implements ChoiceBoxI{
	
	private ChoiceBox<String> stringVariables;
	
	public Variable_NewText() {
		stringVariables = new ChoiceBox<String>();
		TextField textArea = new TextField();
		getChildren().addAll(stringVariables,textArea);
	}
	
	@Override
	public void addListChangeListener(ListChangeListener<Object> c) {
		stringVariables.getItems().addListener(c);
	}
	
	@Override
	public void setOptions(ObservableList<String> list) {
		stringVariables.setItems(list);
	}

}
