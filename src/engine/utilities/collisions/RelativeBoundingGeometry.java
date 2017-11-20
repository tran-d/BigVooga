package engine.utilities.collisions;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class RelativeBoundingGeometry{
	public static final RelativeBoundingGeometry DEFAULT = defaultRectangle();
	private static RelativeBoundingGeometry defaultRectangle() {
		List<Point2D> points = new ArrayList<Point2D>();
		points.add(new Point2D(-.5, -.5));
		points.add(new Point2D(.5, -.5));
		points.add(new Point2D(.5, .5));
		points.add(new Point2D(-.5, .5));
		return new RelativeBoundingGeometry(new BoundingPolygon(points));
	}	
	
	private BoundingGeometry geometry;

	public RelativeBoundingGeometry(BoundingGeometry geometry) {
		this.geometry = geometry;
	}

	public BoundingGeometry getBoundingGeometry(double xCenter, double yCenter, double xSize, double ySize,
			double rotation) {
		return geometry.getScaled(xSize, ySize).getRotated(rotation).getTranslated(xCenter, yCenter);
	}
	
	public String toString() {
		return geometry.toString();
	}
}
