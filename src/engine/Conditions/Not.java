package engine.Conditions;

import engine.Condition;
import engine.VariableContainer;

public class Not extends Condition {

	private Condition condition;
	
	public Not(int priorityNum, Condition condition) {
		this.priorityNum = priorityNum;
		this.condition = condition;
	}
	
	@Override
	public boolean isTrue(VariableContainer asking) {
		return !condition.isTrue(asking);
	}

}
