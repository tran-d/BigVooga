package engine.utilities.collisions;

import java.util.Collection;

import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public abstract class BoundingGeometry {
	public abstract Range dotted(Point2D vectorDirection);
	public abstract Point2D checkCollision(BoundingGeometry g);
	public abstract Point2D checkPolygonCollision(BoundingPolygon polygon);
	public abstract Point2D checkPointCollision(BoundingPoint point);
	public abstract BoundingGeometry getScaled(double xFactor, double yFactor);
	public abstract BoundingGeometry getRotated(double rotation);
	public abstract BoundingGeometry getTranslated(double dx, double dy);
	public Point2D checkCollision(BoundingSet s) {
		return negativeOf(s.checkCollision(this));
	}
	
	protected Point2D checkCollisions(Collection<Point2D> normalVectors, BoundingGeometry other) {
		double minOverlap = Integer.MAX_VALUE;
		Point2D direction = null;

		for (Point2D normal : normalVectors) {
			double overlap = other.dotted(normal).getOverlap(dotted(normal));
			if (overlap <= 0)
				return null;
			if (overlap < minOverlap) {
				minOverlap = overlap;
				direction = normal;
			}
		}
		return direction.multiply(minOverlap);
	}
	
	protected Point2D negativeOf(Point2D vector) {
		if(vector == null)
			return null;
		return vector.multiply(-1);
	}
}
