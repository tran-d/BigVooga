package ActionConditionClasses;

import java.util.List;

import javafx.collections.ObservableList;

public interface VBoxListI<T> {
	
	public Object getCurrentValue();
	public void setNewOptions(List<T> newOptions);
	public int getOptionsSize();
	public void realizeNewOptions(ObservableList<T> newOptions);

}
