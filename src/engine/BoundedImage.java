package engine;

import java.util.HashSet;
import java.util.Set;

import engine.utilities.collisions.BoundingGeometry;
import engine.utilities.collisions.RelativeBoundingGeometry;
import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class BoundedImage {

	private Set<RelativeBoundingGeometry> relativeBounds;
	private String fileName;
	private double xCenter;
	private double yCenter;
	private double xSize;
	private double ySize;
	private double rotation;
	
	public BoundedImage(String fileName) {
		this.fileName = fileName;
	}
	
	public Point2D checkCollision(BoundedImage other) {
		Point2D result = null;
		double maxMagnitude = 0;
		for(BoundingGeometry thisGeometry : getGeometry()) {
			for(BoundingGeometry otherGeometry : other.getGeometry()) {
				Point2D collision = thisGeometry.checkCollision(otherGeometry);
				if(collision==null)
					continue;
				double magnitude = collision.magnitude();
				if(magnitude > maxMagnitude) {
					maxMagnitude = magnitude;
					result = collision;
				}
			}
		}
		return result;
	}
	
	private Set<BoundingGeometry> getGeometry() {
		Set<BoundingGeometry> geometry = new HashSet<>();
		for(RelativeBoundingGeometry rg : relativeBounds) {
			geometry.add(rg.getBoundingGeometry(xCenter, yCenter, xSize, ySize, rotation));
		}
		return geometry;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
}
