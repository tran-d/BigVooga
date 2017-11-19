package engine.Conditions;

import engine.Condition;
import engine.VariableContainer;

public class StringEquals extends Condition {

	private String varName;
	private String check;
	
	public StringEquals(int priorityNum, String varName, String check) {
		this.priorityNum = priorityNum;
		this.varName = varName;
		this.check = check;
	}
	
	@Override
	public boolean isTrue(VariableContainer asking) {
		return asking.getString(varName).equals(check);
	}

}
