package engine.Conditions;

import engine.Condition;
import engine.VariableContainer;
import engine.World;

public class DoubleLessThan extends Condition {

	private String varName;
	private double check;
	
	public DoubleLessThan(int priorityNum, String varName, double check) {
		this.priorityNum = priorityNum;
		this.varName = varName;
		this.check = check;
	}
	
	@Override
	public boolean isTrue(VariableContainer asking, World world) {
		return asking.getDouble(varName) < check;
	}
	
}
