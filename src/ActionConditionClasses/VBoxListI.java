package ActionConditionClasses;

import javafx.collections.ObservableList;

public interface VBoxListI<T> {
	
	public void changeLabel(String newLabel);
	public Object getCurrentValue();
	public void setNewOptions(ObservableList<T> newOptions);
	public int getOptionsSize();
	public void realizeNewOptions(ObservableList<T> newOptions);
	public ObservableList<T> getOptions();

}
