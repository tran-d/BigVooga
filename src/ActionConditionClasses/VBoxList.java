package ActionConditionClasses;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public abstract class VBoxList<T> extends VBox implements VBoxListI<T> {

	private Label topLabel;
	private ObservableList<T> boxOptions;

	public VBoxList(String label, ObservableList<T> options) {
		super();
		this.setAlignment(Pos.CENTER);
		topLabel = new Label(label);
		getChildren().add(topLabel);
		boxOptions = FXCollections.observableArrayList();
		boxOptions.addListener((ListChangeListener<T>) c -> realizeNewOptions(boxOptions));
	}

	@Override
	public void changeLabel(String newLabel) {
		topLabel.setText(newLabel);
	}

	@Override
	public void setNewOptions(ObservableList<T> newOptions) {
		if (newOptions != null)
			boxOptions.setAll(newOptions);
	}

	@Override
	public int getOptionsSize() {
		return boxOptions.size();
	}

	@Override
	public ObservableList<T> getOptions() {
		return boxOptions;
	}

	@Override
	public void addListChangeListener(ListChangeListener<T> listChangeListener) {
		boxOptions.addListener(listChangeListener);
	}

}
