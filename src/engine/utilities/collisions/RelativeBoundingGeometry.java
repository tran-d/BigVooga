package engine.utilities.collisions;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class RelativeBoundingGeometry{
	private BoundingGeometry geometry;

	public RelativeBoundingGeometry(BoundingGeometry geometry) {
		this.geometry = geometry;
	}

	public BoundingGeometry getBoundingGeometry(double xCenter, double yCenter, double xSize, double ySize,
			double rotation) {
		return geometry.getScaled(xSize, ySize).getRotated(rotation).getTranslated(xCenter, yCenter);
	}
	
	
}
