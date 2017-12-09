package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.utilities.collisions.BoundingPoint;

/**
 * Checks if a click is being held on an object.
 * 
 * @author aaronpaskin
 *
 */
public class ObjectClickHeld implements BooleanOperation {

	private GameObjectOperation object;

	public ObjectClickHeld(GameObjectOperation object) {
		this.object = object;
	}

	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		BooleanOperation screenClickHeld = new ScreenClickHeld();
		return screenClickHeld.evaluate(asking, world) && object.evaluate(asking, world).getBounds()
				.checkCollision(new BoundingPoint(world.getPlayerManager().getMouseXY().getX(),
						world.getPlayerManager().getMouseXY().getY())) != null;
	}
}
