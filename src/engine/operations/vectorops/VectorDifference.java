package engine.operations.vectorops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class VectorDifference implements VectorOperation {
	private VectorOperation first;
	private VectorOperation second;

	public VectorDifference(VectorOperation vector, VectorOperation toSubtract) {
		this.first = vector;
		this.second = toSubtract;
	}

	@Override
	public Point2D evaluate(GameObject asking, GameObjectEnvironment world) {
		return first.evaluate(asking, world).subtract(second.evaluate(asking, world));
	}
}
