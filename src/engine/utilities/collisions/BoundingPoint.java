package engine.utilities.collisions;

import javafx.geometry.Point2D;

public class BoundingPoint extends BoundingGeometry {

	private Point2D point;

	public BoundingPoint(Point2D point) {
		this.point = point;
	}
	
	public BoundingPoint(double x, double y) {
		this(new Point2D(x, y));
	}
	
	public Point2D getPoint() {
		return point;
	}
	
	@Override
	public Range dotted(Point2D vectorDirection) {
		double dot = point.dotProduct(vectorDirection);
		return new Range(dot, dot);
	}

	@Override
	public Point2D checkCollision(BoundingGeometry g) {
		return negativeOf(g.checkPointCollision(this));
	}

	@Override
	public Point2D checkPolygonCollision(BoundingPolygon polygon) {
		double minOverlap = Double.POSITIVE_INFINITY;
		Point2D direction = null;
		for(Point2D normal : polygon.generateOutwardNormals()) {
			Range r = polygon.dotted(normal);
			double d = point.dotProduct(normal);
			if(r.contains(d)) {
				minOverlap = r.getMax()-d;
				direction = normal;
			}
		}
		return direction.multiply(minOverlap);
	}

	@Override
	public Point2D checkPointCollision(BoundingPoint point) {
		return equals(point)?new Point2D(0,0):null;
	}

	@Override
	public BoundingGeometry getScaled(double xFactor, double yFactor) {
		return this;
	}

	@Override
	public BoundingGeometry getRotated(double rotation) {
		return this;
	}

	@Override
	public BoundingGeometry getTranslated(double dx, double dy) {
		return new BoundingPoint(point.getX()+dx, point.getY()+dy);
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof BoundingPoint && ((BoundingPoint)o).getPoint().equals(getPoint());
	}
}
