package ActionConditionClasses;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public interface ChoiceBoxI<T> {
	
	public void addListChangeListener(ListChangeListener<T> c);
	public void setOptions(ObservableList<T> list);
	public Object getCurrentChoice();
	
}
