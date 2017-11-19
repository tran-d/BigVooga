package engine;

import java.util.HashSet;
import java.util.Set;

import engine.utilities.collisions.BoundingGeometry;
import engine.utilities.collisions.RelativeBoundingGeometry;

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
	
	public Set<BoundingGeometry> getGeometry() {
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
