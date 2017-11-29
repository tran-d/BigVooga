package ActionConditionClasses;

import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

public class ActionCheckBoxVBox<T> extends VBoxList<T> {

	private ObservableList<CheckBox> checkBoxes;

	public ActionCheckBoxVBox(String label, ObservableList<T> options) {
		super(label, options);
		checkBoxes = FXCollections.observableList(new LinkedList<CheckBox>());
		checkBoxes.addListener((ListChangeListener<CheckBox>) c -> addOrRemoveCheckBoxes(c));
		setNewOptions(options);
	}

	@Override
	public Object getCurrentValue() {
		List<T> checkedBoxValues = new LinkedList<T>();
		for (CheckBox checkBox : checkBoxes) {
			if (checkBox.isSelected())
				checkedBoxValues.add((T) checkBox.getText());
		}
		return checkedBoxValues;
	}

	@Override
	public void realizeNewOptions(ObservableList<T> newOptions) {
		ObservableList<CheckBox> newCheckBoxes = FXCollections.observableArrayList();
		System.out.println("NEW ROW");
		newOptions.forEach(newOption -> {
			newCheckBoxes.add(new CheckBox(newOption.toString()));
			System.out.println(newOption.toString());
		});
		checkBoxes.setAll(newCheckBoxes);
	}

	private void addOrRemoveCheckBoxes(Change<? extends CheckBox> c) {
		getChildren().clear();
		addLabel();
		getChildren().addAll(checkBoxes);
	}

}
