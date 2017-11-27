package ActionConditionClasses;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ChoiceBoxVBox<T> extends VBox implements ChoiceBoxVBoxI {
	
	private Label topLabel;
	private ChoiceBox<T> choiceBox;
	private ObservableList<T> choiceBoxOptions;

	public ChoiceBoxVBox(String label,List options) {
		super();
		topLabel = new Label(label);
		choiceBoxOptions = FXCollections.observableList(options);
		choiceBoxOptions.addListener((ListChangeListener<T>) c -> choiceBox.setItems(choiceBoxOptions));
		choiceBox = new ChoiceBox<T>(choiceBoxOptions);
		getChildren().addAll(topLabel,choiceBox);
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
	public int getOptionsSize() {
		return choiceBoxOptions.size();
	}

	@Override
	public void changeLabel(String newLabel) {
		Label tempLabel = new Label(newLabel);
		topLabel = tempLabel;
	}

}
