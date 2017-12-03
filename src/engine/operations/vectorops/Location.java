package engine.operations.vectorops;

import engine.GameObject;
import engine.Layer;
import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class Location implements VectorOperation {
	
	@Override
	public Point2D evaluate(GameObject asking, Layer world) {
		return new Point2D(asking.getX(), asking.getY());
	}
	
}
