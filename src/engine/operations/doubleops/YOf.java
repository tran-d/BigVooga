package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.vectorops.VectorOperation;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class YOf implements DoubleOperation {

	private VectorOperation vector;

	public YOf(VectorOperation vector) {
		this.vector = vector;
	}
	
	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return vector.evaluate(asking, world).getY();
	}

}
