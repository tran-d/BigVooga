package authoring_actionconditions;

import javafx.collections.ObservableList;

/**
 * Class representing a condition row for sprites.
 * 
 * @author DavidTran
 *
 */
public class ConditionRow extends ActionConditionRow {

	public ConditionRow(int ID, String label, String selectorLabel, boolean isConditionRow,
			ObservableList<Integer> newActionOptions, ActionConditionVBox ACVBox) {
		
		super(ID, label, selectorLabel, newActionOptions, ACVBox);
		
		addActionCheckBox();
	}

}
