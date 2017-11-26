package ActionConditionClasses;

import authoring_actionconditions.IntegerChoiceBox;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Collision extends IntegerChoiceBox implements CollisionI {
	
	public Collision() {
		super();
	}

	@Override
	public void addListChangeListener(ListChangeListener<Integer> c) {
		ObservableList<Integer> currentRows = getCurrentOptions();
		currentRows.addListener(c);
		setCurrentOptions(currentRows);
	}
	
}
