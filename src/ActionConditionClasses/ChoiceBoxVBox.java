package ActionConditionClasses;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

public class ChoiceBoxVBox<T> extends VBoxList<T> implements ChoiceBoxVBoxI<T> {
	
	private ChoiceBox<T> choiceBox;

	public ChoiceBoxVBox(String label,ObservableList<T> options) {
		super(label,options);
		choiceBox = new ChoiceBox<T>();
		setNewOptions(options);
		getChildren().add(choiceBox);
	}

	@Override
	public Object getCurrentValue() {
		return choiceBox.getValue();
	}

	@Override
	public void setValue(Object newValue) {
		choiceBox.setValue((T) newValue);
	}

	@Override
	public void realizeNewOptions(ObservableList<T> newOptions) {
		choiceBox.setItems(newOptions);
	}

}
