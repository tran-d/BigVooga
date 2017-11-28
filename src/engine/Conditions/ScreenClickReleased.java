package engine.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.World;

/**
 * 
 * NOTE: implement Clicked when implementing this condition.
 * 
 * @author aaronpaskin
 *
 */
public class ScreenClickReleased extends Condition{
	
	public ScreenClickReleased(int priorityNum) {
		this.priorityNum = priorityNum;
	}
	
	
	/**
	 * Returns true if the primary mouse button is not down but was down in the previous step, i.e. when it is released
	 */
	@Override
	public boolean isTrue(GameObject asking, World world) {
		return world.getPlayerManager().isPrevPrimaryButtonDown() && !world.getPlayerManager().isPrimaryButtonDown();
	}
	
}
