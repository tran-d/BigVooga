package engine.Conditions;

import engine.Condition;
import engine.VariableContainer;
import engine.World;

/**
 * 
 * @author aaronpaskin
 *
 */
public class StringEquals extends Condition {

	private String varName;
	private String check;
	
	public StringEquals(int priorityNum, String varName, String check) {
		this.priorityNum = priorityNum;
		this.varName = varName;
		this.check = check;
	}
	
	@Override
	public boolean isTrue(VariableContainer asking, World world) {
		return asking.getString(varName).equals(check);
	}

}
