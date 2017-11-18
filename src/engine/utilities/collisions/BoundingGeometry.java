package engine.utilities.collisions;

import javafx.geometry.Point2D;
import javafx.util.Pair;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public abstract class BoundingGeometry {
	public abstract Pair<Double, Double> dotted(Point2D vectorDirection);
	
	public CollisionEvent checkCollision(BoundingGeometry g){
		return g.checkCollision(this);
	}
	
	public abstract CollisionEvent checkCollision(BoundingCircle circle);
	
	public abstract CollisionEvent checkCollision(BoundingRectangle rectangle);
}
