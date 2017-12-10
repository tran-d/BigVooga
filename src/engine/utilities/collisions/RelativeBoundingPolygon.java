package engine.utilities.collisions;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

/**
 * Uses composition to represent BoundingGeometries of relative size and
 * position that can be scaled/translated to produce a boundingGeometry
 * 
 * @author Ian Eldridge-Allegra
 * 
 */
public class RelativeBoundingPolygon {
	public static final RelativeBoundingPolygon DEFAULT = defaultRectangle();

	private static RelativeBoundingPolygon defaultRectangle() {
		List<Point2D> points = new ArrayList<Point2D>();
		points.add(new Point2D(-.5, -.5));
		points.add(new Point2D(.5, -.5));
		points.add(new Point2D(.5, .5));
		points.add(new Point2D(-.5, .5));
		return new RelativeBoundingPolygon(new BoundingPolygon(points));
	}

	private BoundingPolygon geometry;

	/**
	 * @param geometry
	 *            Geometry given in relative coordinates from -0.5 to .5 in both
	 *            directions
	 */
	public RelativeBoundingPolygon(BoundingPolygon geometry) {
		this.geometry = geometry;
	}

	public BoundingPolygon getBoundingGeometry(double xCenter, double yCenter, double xSize, double ySize,
			double rotation) {
		return (BoundingPolygon) geometry.getScaled(xSize, ySize).getRotated(rotation).getTranslated(xCenter, yCenter);
	}

	public String toString() {
		return geometry.toString();
	}
}