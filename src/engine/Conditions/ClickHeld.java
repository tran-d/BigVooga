package engine.Conditions;

import engine.Condition;
import engine.VariableContainer;
import engine.World;

/**
 * 
 * 
 * @author aaronpaskin
 *
 */
public class ClickHeld extends Condition {
	
	public ClickHeld(int priorityNum) {
		this.priorityNum = priorityNum;
	}
	
	/**
	 * Returns true if the primary mouse button is down, regardless of whether or not it was down in the previous step
	 */
	@Override
	public boolean isTrue(VariableContainer asking, World world) {
		return world.getInputManager().isPrimaryButtonDown();
	}
	
}
