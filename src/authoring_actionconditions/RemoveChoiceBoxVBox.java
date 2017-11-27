package authoring_actionconditions;

import java.util.LinkedList;
import java.util.List;

import ActionConditionClasses.ChoiceBoxVBox;

public class RemoveChoiceBoxVBox extends ChoiceBoxVBox<Integer> {
	
	public RemoveChoiceBoxVBox(String label,List<Integer> removalOptions) {
		super(label,removalOptions);
	}
	
	protected void addRow() {
		addOption(getOptionsSize() + 1);
	}
	
	protected void removeRow() {
		System.out.println("Old size is " + getOptionsSize());
		int newSize = getOptionsSize() - 1;
		System.out.println("New size is " + newSize);
		List<Integer> newOptions = new LinkedList<Integer>();
		for(int i = 1; i <= newSize; i++) {
			newOptions.add(i);
		}
		setNewOptions(newOptions);
		setValue(null);
	}
	
}
