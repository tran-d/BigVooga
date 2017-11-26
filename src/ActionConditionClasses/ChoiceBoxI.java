package ActionConditionClasses;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public interface ChoiceBoxI {
	
	public void addListChangeListener(ListChangeListener<Object> c);
	public void setOptions(ObservableList<Object> list);
	
}
