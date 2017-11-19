package engine.utilities.collisions;

import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public abstract class BoundingGeometry {
	public abstract Range dotted(Point2D vectorDirection);
	
	
	/**
	 * @param g the other geometry
	 * @return null if no overlap, otherwise gives the motion required on this object to separate them
	 */
	public abstract Point2D checkCollision(BoundingGeometry g);
	
	/**
	 * @param polygon the other geometry
	 * @return null if no overlap, otherwise gives the motion required on this object to separate them
	 */
	public abstract Point2D checkPolygonCollision(BoundingPolygon polygon);

	public abstract BoundingGeometry getScaled(double xFactor, double yFactor);

	public abstract BoundingGeometry getRotated(double rotation);

	public abstract BoundingGeometry getTranslated(double xCenter, double yCenter);
}
