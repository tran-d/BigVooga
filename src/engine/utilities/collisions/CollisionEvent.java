package engine.utilities.collisions;

import engine.GameObject;
import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class CollisionEvent {

	private GameObject other;

	public GameObject getGameObject() {
		return other;
	}
	
	public Point2D getOverlapVector() {
		return null;
	}
}
