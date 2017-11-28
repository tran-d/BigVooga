package ActionConditionClasses;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

public abstract class VBoxList<T> extends VBox implements VBoxListI<T> {
	
	private ObservableList<T> boxOptions;
	
	public VBoxList(List<T> options) {
		super();
		boxOptions = FXCollections.observableArrayList();
		boxOptions.addListener((ListChangeListener<T>) c -> realizeNewOptions(boxOptions));
	}
	
	@Override
	public void setNewOptions(List<T> newOptions) {
		boxOptions.setAll(FXCollections.observableList(newOptions));
	}
	
	@Override
	public int getOptionsSize() {
		return boxOptions.size();
	}

}
