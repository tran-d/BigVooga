package engine.utilities.collisions;

import javafx.geometry.Point2D;
import javafx.util.Pair;

public class BoundingCircle extends BoundingGeometry{

	private Point2D center;
	private double radius;

	public BoundingCircle(Point2D center, double radius) {
		this.center = center;
		this.radius = radius;
	}
	
	@Override
	public Pair<Double, Double> dotted(Point2D vectorDirection) {
		double dp = center.dotProduct(vectorDirection);
		return new Pair<>(dp-radius, dp+radius);
	}

	@Override
	public CollisionEvent checkCollision(BoundingCircle circle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CollisionEvent checkCollision(BoundingRectangle rectangle) {
		// TODO Auto-generated method stub
		return null;
	}

}
