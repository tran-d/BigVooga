package engine.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.World;

/**
 * 
 * @author aaronpaskin
 *
 */
public class BooleanTrue extends Condition {

	private String varName;
	
	public BooleanTrue(int priorityNum, String varName) {
		this.priorityNum = priorityNum;
		this.varName = varName;
	}
	
	@Override
	public boolean isTrue(GameObject asking, World world) {
		return asking.getBoolean(varName);
	}
	
}
