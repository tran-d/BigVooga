package engine.Conditions;

import engine.Condition;
import engine.VariableContainer;

public class BeginStep extends Condition {

	public BeginStep() {
		priorityNum = 0;
	}
	
	@Override
	public boolean isTrue(VariableContainer asking) {
		return true;
	}

}
