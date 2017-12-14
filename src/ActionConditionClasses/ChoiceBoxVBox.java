package ActionConditionClasses;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

public class ChoiceBoxVBox extends VBoxList implements ChoiceBoxVBoxI {
	
	private ChoiceBox<Integer> choiceBox;

	public ChoiceBoxVBox(String label,ObservableList<Integer> options) {
		super(label,options);
		choiceBox = new ChoiceBox<Integer>();
		setNewOptions(options);
		getChildren().add(choiceBox);
	}

	@Override
	public Integer getCurrentValue() {
		return choiceBox.getValue();
	}

	@Override
	public void setValue(Integer newValue) {
		choiceBox.setValue(newValue);
	}

	@Override
	public void realizeNewOptions(ObservableList<Integer> newOptions) {
		choiceBox.setItems(newOptions);
	}
	public Integer getSelected() {
		return choiceBox.getValue();
	}

}
