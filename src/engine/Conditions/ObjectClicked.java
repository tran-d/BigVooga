package engine.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.World;

/**
 * Checks if an object is initially clicked (thus, can not return true
 * two steps in a row).
 * 
 * @author aaronpaskin
 *
 */
public class ObjectClicked extends Condition {
	
	public ObjectClicked(int priorityNum) {
		this.priorityNum = priorityNum;
	}
	
	@Override
	public boolean isTrue(GameObject asking, World world) {
		Condition screenClicked = new ScreenClicked(0);
		Condition objectClickHeld = new ObjectClickHeld(0);
		Condition and = new And(0, screenClicked, objectClickHeld);
		return and.isTrue(asking, world);
	}

}
