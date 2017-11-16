package engine;

import java.util.Set;

import engine.utilities.collisions.BoundingGeometry;
import engine.utilities.collisions.RelativeBoundingGeometry;

public class BoundedImage {

	private Set<RelativeBoundingGeometry> relativeBounds;
	private String pathName;
	
	public BoundedImage(String pathName) {
		this.pathName = pathName;
	}
}
