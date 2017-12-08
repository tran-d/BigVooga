package engine.operations.booleanops;

import engine.Condition;
import engine.GameObject;
import engine.Layer;
import engine.utilities.collisions.BoundingPoint;

/**
 * Checks if click is released from a click on an object. Note that the cursor 
 * can be anywhere for the condition to return true as long as the last click
 * that is being released was on the object.
 * 
 * @author aaronpaskin
 *
 */
public class ObjectClickReleased implements BooleanOperation {

	public ObjectClickReleased() {}
	
	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		BooleanOperation screenClickReleased = new ScreenClicked();
		return screenClickReleased.evaluate(asking, world) &&
				asking.getBounds().checkCollision(new BoundingPoint(world.getPlayerManager().getClickX(), world.getPlayerManager().getClickY())) != null;
	}

}
