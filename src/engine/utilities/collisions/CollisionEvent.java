package engine.utilities.collisions;

import engine.GameObject;
import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class CollisionEvent {

	private GameObject other;
	private Point2D overlap;

	public CollisionEvent(GameObject other, Point2D overlap) {
		this.other = other;
		this.overlap = overlap;
	}
	
	public GameObject getGameObject() {
		return other;
	}
	
	public Point2D getOverlapVector() {
		return overlap;
	}
}
