package engine;

import java.util.ArrayList;
import java.util.List;

import engine.utilities.collisions.BoundingGeometry;
import engine.utilities.collisions.RelativeBoundingGeometry;
import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class BoundedImage {

	private List<RelativeBoundingGeometry> relativeBounds;
	private String fileName;
	private double xCenter;
	private double yCenter;
	private double xSize;
	private double ySize;
	private double rotation;
	
	public BoundedImage(String fileName) {
		this.fileName = fileName;
		relativeBounds = new ArrayList<>();
		relativeBounds.add(RelativeBoundingGeometry.DEFAULT);
	}
	
	public BoundedImage(String fileName, List<RelativeBoundingGeometry> bounds) {
		this.fileName = fileName;
		relativeBounds = bounds;
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
	
	private List<BoundingGeometry> getGeometry() {
		List<BoundingGeometry> geometry = new ArrayList<>(relativeBounds.size());
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
	
	public double getXCenter() {
		return xCenter;
	}
	
	public double getYCenter() {
		return yCenter;
	}
	
	public double getXSize() {
		return xSize;
	}
	
	public double getYSize() {
		return ySize;
	}
	
}
