package ActionConditionClasses;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

public class ChoiceBoxVBox<T> extends LabelSelectorVBox implements ChoiceBoxVBoxI {
	
	private ChoiceBox<T> choiceBox;
	private ObservableList<T> choiceBoxOptions;

	public ChoiceBoxVBox(String label,List options) {
		super(label);
		choiceBoxOptions = FXCollections.observableList(options);
		choiceBoxOptions.addListener((ListChangeListener<T>) c -> choiceBox.setItems(choiceBoxOptions));
		choiceBox = new ChoiceBox<T>(choiceBoxOptions);
		getChildren().add(choiceBox);
	}

	@Override
	public Object getCurrentValue() {
		return choiceBox.getValue();
	}

	@Override
	public void setNewOptions(List newOptions) {
		choiceBoxOptions.clear();
		for(Object option : newOptions) {
			choiceBoxOptions.add((T) option);
		}
	}

	@Override
	public void setValue(Object newValue) {
		choiceBox.setValue((T) newValue);
	}

	@Override
	public void addOption(Object newOption) {
		choiceBoxOptions.add((T) newOption);
	}

	@Override
	public int getOptionsSize() {
		return choiceBoxOptions.size();
	}


}
