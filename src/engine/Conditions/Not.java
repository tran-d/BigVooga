package engine.Conditions;

import engine.Condition;
import engine.VariableContainer;
import engine.World;

public class Not extends Condition {

	private Condition condition;
	
	public Not(int priorityNum, Condition condition) {
		this.priorityNum = priorityNum;
		this.condition = condition;
	}
	
	@Override
	public boolean isTrue(VariableContainer asking, World world) {
		return !condition.isTrue(asking, world);
	}

}
