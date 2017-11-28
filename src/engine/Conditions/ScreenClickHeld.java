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
public class ScreenClickHeld extends Condition {
	
	public ScreenClickHeld(int priorityNum) {
		this.priorityNum = priorityNum;
	}
	
	/**
	 * Returns true if the primary mouse button is down, regardless of whether or not it was down in the previous step
	 */
	@Override
	public boolean isTrue(GameObject asking, World world) {
		return world.getPlayerManager().isPrimaryButtonDown();
	}
	
}
