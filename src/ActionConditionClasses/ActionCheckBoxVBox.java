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
		checkBoxes.addListener((ListChangeListener<CheckBox>) c -> iterateThroughChanges(c));
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
//		System.out.println("NEWCHECKBOXES: " + newCheckBoxes);
		checkBoxes.setAll(newCheckBoxes);
	}

	private void iterateThroughChanges(Change<? extends CheckBox> c) {
		getChildren().clear();
		System.out.println(c);
		while (c.next()) {
			c.getAddedSubList().forEach(checkBox -> addOrRemoveFromVBox(c.wasAdded(), c.wasRemoved(), checkBox));
		}
	}

	private void addOrRemoveFromVBox(Boolean wasAdded, Boolean wasRemoved, CheckBox checkBox) {
		if (wasAdded)
			getChildren().add(checkBox);
		else if (wasRemoved)
			getChildren().remove(checkBox);
		System.out.println(checkBox);
		
	}

}
