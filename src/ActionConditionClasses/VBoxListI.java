package ActionConditionClasses;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

public interface VBoxListI<T> {
	
	public void changeLabel(String newLabel);
	public Object getCurrentValue();
	public void setNewOptions(ObservableList<T> newOptions);
	public int getOptionsSize();
	public void realizeNewOptions(ObservableList<T> newOptions);
	public ObservableList<T> getOptions();
	public void addListChangeListener(ListChangeListener<T> listChangeListener);
	public Label getLabel();

}
