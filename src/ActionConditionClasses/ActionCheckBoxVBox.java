package ActionConditionClasses;

import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

public class ActionCheckBoxVBox<T> extends VBoxList<T> implements ActionCheckBoxVBoxI {

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
		newOptions.forEach(newOption -> newCheckBoxes.add(new CheckBox(newOption.toString())));
		checkBoxes.setAll(newCheckBoxes);
	}

	private void addOrRemoveCheckBoxes(Change<? extends CheckBox> c) {
		while(c.next()) {
			for(CheckBox checkBox : c.getRemoved()) getChildren().remove(checkBox);
			for(CheckBox checkBox : c.getAddedSubList()) getChildren().add(checkBox);
		}
	}

	@Override
	public void addAction() {
		checkBoxes.add(new CheckBox(Integer.toString(checkBoxes.size() + 1)));
	}

	@Override
	public void removeAction(Integer action) {
		checkBoxes.remove((int) action);
		for(int i = action; i < checkBoxes.size(); i++) {
			CheckBox iCheckBox = checkBoxes.get(i);
			int currentLabel = Integer.parseInt(iCheckBox.getText());
			iCheckBox.setText(Integer.toString(currentLabel - 1));
		}
	}

}
