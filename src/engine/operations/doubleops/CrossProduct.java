package engine.operations.doubleops;

import engine.GameObject;
import engine.Layer;
import engine.operations.vectorops.VectorOperation;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class CrossProduct implements DoubleOperation {

	private VectorOperation firstVector;
	private VectorOperation secondVector;

	public CrossProduct(VectorOperation firstVector, VectorOperation secondVector) {
		this.firstVector = firstVector;
		this.secondVector = secondVector;
	}

	@Override
	public Double evaluate(GameObject asking, Layer world) {
		return firstVector.evaluate(asking, world).crossProduct(secondVector.evaluate(asking, world)).getZ();
	}
}
