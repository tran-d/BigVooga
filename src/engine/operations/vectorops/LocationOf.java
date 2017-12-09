package engine.operations.vectorops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.gameobjectops.GameObjectOperation;
import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class LocationOf implements VectorOperation {
	private GameObjectOperation gameObject;

	public LocationOf(GameObjectOperation gameObject) {
		this.gameObject = gameObject;
	}

	@Override
	public Point2D evaluate(GameObject asking, GameObjectEnvironment world) {
		GameObject obj = gameObject.evaluate(asking, world);
		return new Point2D(obj.getX(), obj.getY());
	}
	
}
