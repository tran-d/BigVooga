package engine.utilities.collisions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class BoundingPolygon extends BoundingGeometry {

	private List<Point2D> vertices;

	public BoundingPolygon(List<Point2D> vertices) {
		this.vertices = vertices;
	}

	@Override
	public Range dotted(Point2D vectorDirection) {
		double max = Double.NEGATIVE_INFINITY;
		double min = Double.POSITIVE_INFINITY;
		for (Point2D vertex : vertices) {
			double dot = vertex.dotProduct(vectorDirection);
			min = Math.min(min, dot);
			max = Math.max(max, dot);
		}
		
		return new Range(min, max);
	}
	
	@Override
	public Point2D checkCollision(BoundingGeometry geometry) {
		Point2D result = geometry.checkPolygonCollision(this);
		if(result == null)
			return null;
		return result.multiply(-1);
	}
	
	@Override
	public BoundingGeometry getScaled(double xFactor, double yFactor) {
		return getTransformed(p -> new Point2D(p.getX() * xFactor, p.getY() * yFactor));
	}

	@Override
	public BoundingGeometry getRotated(double rotation) {
		return getTransformed(p -> rotateByAngle(p, rotation));
	}

	@Override
	public BoundingGeometry getTranslated(double dx, double dy) {
		return getTransformed(p->new Point2D(p.getX()+dx, p.getY()+dy));
	}

	private BoundingGeometry getTransformed(Function<Point2D, Point2D> function) {
		List<Point2D> newVertices = new ArrayList<>();
		for (Point2D vertex : vertices) {
			newVertices.add(function.apply(vertex));
		}
		return new BoundingPolygon(newVertices);
	}

	@Override
	public Point2D checkPolygonCollision(BoundingPolygon polygon) {
		double minOverlap = Integer.MAX_VALUE;
		Point2D direction = null;
		List<Point2D> normals = generateOutwardNormals();
		normals.addAll(polygon.generateInwardNormals());
		System.out.println(normals);
		for(Point2D normal : normals){
			Range otherProjection = polygon.dotted(normal);
			Range thisProjection = dotted(normal);
			double overlap = otherProjection.getOverlap(thisProjection);
			if(overlap <= 0)
				return null;
			if(overlap < minOverlap) {
				minOverlap = overlap;
				direction = normal;
			}
		}
		return direction.multiply(minOverlap);
	}
	
	protected List<Point2D> generateOutwardNormals(){
		return generateNormals(true);
	}
	
	protected List<Point2D> generateInwardNormals(){
		return generateNormals(false);
	}
	
	private List<Point2D> generateNormals(boolean outward){
		List<Point2D> normals = new ArrayList<>();
		for(int i = 0; i < vertices.size(); i++) {
			Point2D side = vertices.get((i+1)%vertices.size()).subtract(vertices.get(i));
			Point2D normalToSide = rotateByAngle(side, outward?-90:90).normalize();
			normals.add(normalToSide);
		}
		return normals;
	}

	private static Point2D rotateByAngle(Point2D vector, double angle) {
		angle = Math.toRadians(angle);
		return new Point2D(vector.getX() * Math.cos(angle) - vector.getY() * Math.sin(angle),
				vector.getX() * Math.sin(angle) + vector.getY() * Math.cos(angle));
	}
	
	public String toString() {
		return vertices.toString();
	}
	
	public List<Point2D> getVertices(){
		return vertices;
	}
}
