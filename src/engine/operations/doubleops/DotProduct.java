package engine.operations.doubleops;

import engine.GameObject;
import engine.Layer;
import engine.operations.vectorops.VectorOperation;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class DotProduct implements DoubleOperation {

	private VectorOperation firstVector;
	private VectorOperation secondVector;

	public DotProduct(VectorOperation firstVector, VectorOperation secondVector) {
		this.firstVector = firstVector;
		this.secondVector = secondVector;
	}
	
	@Override
	public Double evaluate(GameObject asking, Layer world) {
		return firstVector.evaluate(asking, world).dotProduct(secondVector.evaluate(asking, world));
	}

}
