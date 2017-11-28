package engine.Conditions;

import engine.Condition;
import engine.VariableContainer;
import engine.World;

/**
 * 
 * @author aaronpaskin
 *
 */
public class KeyHeld extends Condition {

	private int priorityNum;
	private String check;
	
	public KeyHeld(int priorityNum, String check) {
		this.priorityNum = priorityNum;
		this.check = check;
	}
	
	
	/**
	 * Returns true when the key named "check" is down
	 */
	@Override
	public boolean isTrue(VariableContainer asking, World world) {
		return world.getInputManager().getKeysDown().contains(check);
	}
	
}
