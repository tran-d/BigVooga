package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;
import engine.utilities.collisions.BoundingPoint;

/**
 * Checks if a click is being held on an object.
 * 
 * @author aaronpaskin
 *
 */
public class ObjectClickHeld implements BooleanOperation {

	public ObjectClickHeld() {}

	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		BooleanOperation screenClickHeld = new ScreenClickHeld();
		return screenClickHeld.evaluate(asking, world) && 
				asking.getBounds().checkCollision(new BoundingPoint(world.getPlayerManager().getClickX(), world.getPlayerManager().getClickY())) != null;
	}
}
