package engine;

import java.util.Set;

import engine.utilities.collisions.RelativeBoundingGeometry;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class BoundedImage {

	private Set<RelativeBoundingGeometry> relativeBounds;
	private String fileName;
	
	public BoundedImage(String fileName) {
		this.fileName = fileName;
	}
}
