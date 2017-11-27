package authoring_actionconditions;

import java.util.LinkedList;
import java.util.List;

import ActionConditionClasses.ChoiceBoxVBox;

public class RemoveChoiceBoxVBox extends ChoiceBoxVBox<Integer> {
	
	public RemoveChoiceBoxVBox(String label,List<Integer> removalOptions) {
		super(label,removalOptions);
	}
	
	protected void addRow() {
		int newSize = increaseOptionsSize();
		adjustListtoSize(newSize);
	}
	
	protected void removeRow() {
		int newSize = decreaseOptionsSize();
		adjustListtoSize(newSize);
	}
	
	private int increaseOptionsSize() {
		return getOptionsSize() + 1;
	}
	
	private int decreaseOptionsSize() {
		return getOptionsSize() - 1;
	}
	
	private void adjustListtoSize(int newSize) {
		List<Integer> newOptions = new LinkedList<Integer>();
		for(int i = 1; i <= newSize; i++) {
			newOptions.add(i);
		}
		setNewOptions(newOptions);
		setValue(null);
	}
	
}
