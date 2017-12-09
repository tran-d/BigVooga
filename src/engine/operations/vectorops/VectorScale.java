package engine.operations.vectorops;

import engine.GameObject;
import engine.Layer;
import engine.operations.doubleops.DoubleOperation;
import javafx.geometry.Point2D;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class VectorScale implements VectorOperation{

	private VectorOperation vector;
	private DoubleOperation scalar;
	public VectorScale(VectorOperation vector, DoubleOperation scalar) {
		// TODO Auto-generated constructor stub
		this.vector = vector;
		this.scalar = scalar;
	}

	@Override
	public Point2D evaluate(GameObject asking, Layer world) {
		// TODO Auto-generated method stub
		return vector.evaluate(asking, world).multiply(scalar.evaluate(asking, world));
	}

}
