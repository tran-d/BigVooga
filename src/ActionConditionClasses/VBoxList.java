package ActionConditionClasses;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public abstract class VBoxList extends VBox implements VBoxListI {

	private Label topLabel;
	private ObservableList<Integer> boxOptions;

	public VBoxList(String label, ObservableList<Integer> options) {
		super();
		setAlignment(Pos.CENTER);
		topLabel = new Label(label);
		getChildren().add(topLabel);
		boxOptions = FXCollections.observableArrayList();
		boxOptions.addListener((ListChangeListener<Integer>) c -> realizeNewOptions(boxOptions));
	}

	@Override
	public void changeLabel(String newLabel) {
		topLabel.setText(newLabel);
	}

	@Override
	public void setNewOptions(ObservableList<Integer> newOptions) {
		if (newOptions != null)
			boxOptions.setAll(newOptions);
	}

	@Override
	public int getOptionsSize() {
		return boxOptions.size();
	}

	@Override
	public ObservableList<Integer> getOptions() {
		return boxOptions;
	}

	@Override
	public void addListChangeListener(ListChangeListener<Integer> listChangeListener) {
		boxOptions.addListener(listChangeListener);
	}
	
	@Override
	public Label getLabel() {
		return topLabel;
	}

}
