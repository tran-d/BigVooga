package engine.operations.doubleops;

import engine.GameObject;
import engine.Layer;
import engine.operations.vectorops.VectorOperation;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class XOf implements DoubleOperation {

	private VectorOperation vector;

	public XOf(VectorOperation vector) {
		this.vector = vector;
	}
	
	@Override
	public Double evaluate(GameObject asking, Layer world) {
		return vector.evaluate(asking, world).getX();
	}

}
