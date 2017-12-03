package engine.operations.vectorops;

import engine.GameObject;
import engine.Layer;
import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class VectorSum implements VectorOperation {
	private VectorOperation first;
	private VectorOperation second;

	public VectorSum(VectorOperation first, VectorOperation second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public Point2D evaluate(GameObject asking, Layer world) {
		return first.evaluate(asking, world).add(second.evaluate(asking, world));
	}
}
