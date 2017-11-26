package engine.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.World;

/**
 * 
 * @author aaronpaskin
 *
 */
public class DoubleLessThan extends Condition {

	private String varName;
	private double check;
	
	public DoubleLessThan(int priorityNum, String varName, double check) {
		this.priorityNum = priorityNum;
		this.varName = varName;
		this.check = check;
	}
	
	@Override
	public boolean isTrue(GameObject asking, World world) {
		return asking.getDouble(varName) < check;
	}
	
}
