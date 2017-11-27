package engine.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.World;

/**
 * Checks if click is released from a click on an object. Note that the cursor 
 * can be anywhere for the condition to return true as long as the last click
 * that is being released was on the object.
 * 
 * @author aaronpaskin
 *
 */
public class ObjectClickReleased extends Condition {

	public ObjectClickReleased(int priorityNum) {
		this.priorityNum = priorityNum;
	}
	
	@Override
	public boolean isTrue(GameObject asking, World world) {
		Condition screenClickReleased = new ScreenClicked(0);
		return screenClickReleased.isTrue(asking, world) && 
				world.getPlayerManager().getClickX() > asking.getImage().getXCenter() - 0.5 * asking.getImage().getXSize() &&
				world.getPlayerManager().getClickX() < asking.getImage().getXCenter() + 0.5 * asking.getImage().getXSize() &&
				world.getPlayerManager().getClickY() > asking.getImage().getYCenter() - 0.5 * asking.getImage().getYSize() &&
				world.getPlayerManager().getClickY() < asking.getImage().getYCenter() + 0.5 * asking.getImage().getYSize();
	}

}
