package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.utilities.collisions.CollisionEvent;
import javafx.geometry.Point2D;

public class Collision implements BooleanOperation {

	private GameObjectOperation first;
	private GameObjectOperation second;

	public Collision(GameObjectOperation first, GameObjectOperation second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		GameObject obj1 = first.evaluate(asking, world);
		GameObject obj2 = second.evaluate(asking, world);
		Point2D intersectionVector = obj1.getBounds().checkCollision(obj2.getBounds());
		if (intersectionVector != null) {
			obj2.setLastCollisionChecked(new CollisionEvent(obj1, intersectionVector.multiply(-1)));
			obj2.setLastCollisionChecked(new CollisionEvent(obj1, intersectionVector));
			return true;
		}
		return false;
	}
}
