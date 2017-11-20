package engine.Conditions;

import engine.Condition;
import engine.VariableContainer;
import engine.World;

public class DoubleEquals extends Condition {

	private String varName;
	private double check;
	
	public DoubleEquals(int priorityNum, String varName, double check) {
		this.priorityNum = priorityNum;
		this.varName = varName;
		this.check = check;
	}
	
	@Override
	public boolean isTrue(VariableContainer asking, World world) {
		return asking.getDouble(varName) == check;
	}
	
}
