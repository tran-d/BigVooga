package engine.Conditions;

import engine.Condition;
import engine.GameObject;
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
	public boolean isTrue(GameObject asking, World world) {
		//TODO make inputmanager (explicitly or implicitly)
		return world.getInputManager().isPrimaryButtonDown();
	}
	
}
